package com.deskshop.serv.impl;

import com.deskshop.common.link.ClientInterface;
import com.deskshop.common.link.ServerInterface;
import com.deskshop.common.metier.*;
import com.deskshop.serv.manager.*;
import com.deskshop.common.metier.Article;
import com.deskshop.common.metier.Compte;
import com.deskshop.common.metier.Magasin;
import com.deskshop.common.metier.Person;
import com.deskshop.serv.manager.ArticleManager;
import com.deskshop.serv.manager.CompteManager;
import com.deskshop.serv.manager.MagasinManager;
import com.deskshop.serv.manager.PersonManager;
import com.deskshop.utils.MailUtil;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

public class ServerImpl extends Observable implements ServerInterface {
    private ArticleManager        articleManager        = new ArticleManager();
    private CommandeManager       commandeManager       = new CommandeManager();
    private CompteManager         compteManager         = new CompteManager();
    private DetailCommandeManager detailCommandeManager = new DetailCommandeManager();
    private MagasinManager        magasinManager        = new MagasinManager();
    private MovementManager       movementManager       = new MovementManager();
    private PersonManager         personManager         = new PersonManager();

    private class WrappedObserver implements Observer, Serializable {

        private static final long serialVersionUID = 1L;

        private ClientInterface ro;

        WrappedObserver(ClientInterface ro) {
            this.ro = ro;
        }

        @Override
        public void update(Observable o, Object arg) {
            try {
                ro.update(o.toString(), arg);
            } catch (RemoteException e) {
                System.out.println("Remote exception removing observer:" + this);
                o.deleteObserver(this);
            }
        }

    }

    @Override
    public void addObserver(ClientInterface o) {
        WrappedObserver mo = new WrappedObserver(o);
        addObserver(mo);
        System.out.println("Added observer:" + mo);
    }

    //__________________________ Manage user __________________________
    @Override
    public int login(String mail, String psw) {
        return personManager.Connect(mail, psw);
    }

    @Override
    public int createUser(Person user) {
        try {
            //create user
            user = personManager.create(user);
            final Person person = user;
            new Thread(() -> MailUtil.sendWelcomeMail(person.getMel())).start();
            //get the general server
            return user.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //_______________________ Shop on DashBoard _______________________
    @Override
    public int createShop(String name, int userId) {
        try {
            //get user
            Person user = personManager.read(userId);

            //get magasin
            Magasin magasin = new Magasin();
            magasin.setCreator(user);
            magasin.setName(name);
            magasin = magasinManager.create(magasin);
            setChanged();
            notifyObservers("shop");
            return magasin.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Magasin> findAllMagasin(int userId) {
        Person person = getUser(userId);
        return magasinManager.findAllMagasin(person);
    }

    @Override
    public List<Magasin> findMagasinByUser(int userId) {
        Person person = getUser(userId);
        return magasinManager.findMagasinByUser(person);
    }

    private Person getUser(int id){
        return personManager.read(id);
    }

    //_________________________ Manage a Shop _________________________
    @Override
    public void addArticle(int idMagasin, String name, String desc, double price) {
        Article article = new Article();
        article.setName(name);
        article.setDesc(desc);
        article.setPrice(price);
        article.setShop(getMagasin(idMagasin));
        articleManager.create(article);

        setChanged();
        notifyObservers("article");
    }

    @Override
    public List<Article> getArticleByMagasin(int id) {
        return articleManager.getArticleByMagasin(getMagasin(id));
    }

    private Magasin getMagasin(int id){
        return magasinManager.read(id);
    }

    private Person getPerson(int id){
        return personManager.read(id);
    }

    @Override
    public boolean transfert(double monnaie, Compte compteGiver, Compte compteReceiver) throws RemoteException {
        try{
            compteGiver.debit(monnaie);
            compteReceiver.credit(monnaie);
            compteManager.update(compteGiver);
            compteManager.update(compteReceiver);
            Timestamp date = Timestamp.from(Instant.now());
            movementManager.create(new Movement(date, -monnaie, compteGiver));
            movementManager.create(new Movement(date, monnaie, compteReceiver));

            setChanged();
            notifyObservers(Arrays.asList(compteGiver, compteReceiver));
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editSolde(double somme, Compte compteModife) throws RemoteException {
        try{
            compteModife.setAmount(somme);
            compteManager.update(compteModife);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean paid(HashMap<Article, Integer> cadie, int idUser, int idMagasin) {
        double sum = cadie.entrySet().stream().mapToDouble(c -> c.getKey().getPrice() * c.getValue()).sum();
        Magasin magasin = getMagasin(idMagasin);
        Person client = getPerson(idUser),
               vendeur= getPerson(magasin.getCreator().getId());
        Compte compteClient = compteManager.getCompteByPersonne(client),
               compteVendeur= compteManager.getCompteByPersonne(vendeur);
        try{
            compteClient.debit(sum);
            compteVendeur.credit(sum);
            compteManager.update(compteClient);
            compteManager.update(compteVendeur);
            Timestamp date = Timestamp.from(Instant.now());
            Commande commande = commandeManager.create(new Commande(date, client, magasin));
            movementManager.create(new Movement(date, sum, compteVendeur));
            movementManager.create(new Movement(date, -sum, compteClient));

            setChanged();
            notifyObservers(Arrays.asList(client, vendeur));
            DetailCommande detailCommande = new DetailCommande();
            detailCommande.setCommande(commande);
            for (Map.Entry<Article, Integer> entry : cadie.entrySet()) {
                detailCommande.setArticle(entry.getKey());
                detailCommande.setQuantity(entry.getValue());
                detailCommandeManager.create(detailCommande);
            }
            new Thread(() ->MailUtil.sendFactureMail(client.getMel(), cadie)).start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void credit(double sum) {
        System.out.println();
    }

    public ServerImpl() {
        new Thread(() -> {
            while (true) setChanged();
        }).start();
    }

    @Override
    public List<Compte> findAllCompte() {
        CompteManager compteManager = new CompteManager();
        return compteManager.findAllCompte();
    }

    @Override
    public List<Compte> findAllCompteByUser(int userId) {
        Person person = getUser(userId);
        CompteManager compteManager = new CompteManager();
        return compteManager.findAllCompteByUser(person);
    }

    @Override
    public List<Movement> findMovementByCompte(Compte compte){
        MovementManager movementManager = new MovementManager();
        return movementManager.findMovementByCompte(compte);
    }

}
