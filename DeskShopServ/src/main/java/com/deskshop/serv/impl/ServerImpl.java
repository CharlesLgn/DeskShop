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
import com.deskshop.utils.Iban;
import com.deskshop.utils.MailUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

public class ServerImpl extends Observable implements ServerInterface {
    private AdministreManager     administreManager     = new AdministreManager();
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

    public List<Person> findAllUsers(){
        return personManager.findAllUsers();
    }

    //_______________________ Shop on DashBoard _______________________
    @Override
    public int createShop(String name, int userId, String iban) {
        if(compteManager.getCompteByIban(iban) == null){
            return -1;
        }
        try {
            //get user
            Person user = personManager.read(userId);

            //get magasin
            Magasin magasin = new Magasin();
            magasin.setCreator(user);
            magasin.setName(name);
            magasin.setIban(iban);
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
    public void addArticle(int idMagasin, String name, String desc, double price, String image, int stock) {
        Article article = new Article();
        article.setName(name);
        article.setDesc(desc);
        article.setPrice(price);
        article.setPicture(image);
        article.setShop(getMagasin(idMagasin));
        article.setStock(stock);
        articleManager.create(article);

        setChanged();
        notifyObservers("article");
    }

    @Override
    public void updateArticle(Article article, String name, String desc, double price, int stock) {
        article.setName(name);
        article.setDesc(desc);
        article.setPrice(price);
        article.setStock(stock);
        articleManager.update(article);

        setChanged();
        notifyObservers("article");
    }

    @Override
    public void deleteArticle(Article article) {
        articleManager.delete(article);
        setChanged();
        notifyObservers("article");
    }

    @Override
    public void uploadFile(Article article, byte[] data, String filename){
        try {
            filename = filename.replaceAll(" ", "_");
            File file = new File("../../data/" + filename);
            System.out.println(file.getAbsolutePath().replace(" ", "?"));
            FileUtils.writeByteArrayToFile(file, data);
            String path = file.getPath();
            System.out.println(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public boolean transfert(double monnaie, Compte compteGiver, Compte compteReceiver) {
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
    public boolean editSolde(double somme, Compte compteModife) {
        try{
            compteModife.setAmount(somme);
            compteManager.update(compteModife);
            setChanged();
            notifyObservers(compteModife);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean paid(HashMap<Article, Integer> cadie, int idUser, String iban, int idMagasin) {
        double sum = cadie.entrySet().stream().mapToDouble(c -> c.getKey().getPrice() * c.getValue()).sum();
        Magasin magasin = getMagasin(idMagasin);
        Person client = getPerson(idUser),
               vendeur= getPerson(magasin.getCreator().getId());
        try{
            Compte compteClient = compteManager.getCompteByIban(iban),
                    compteVendeur= compteManager.getCompteByIban(magasin.getIban());
            if (compteClient == null) {
                return false;
            }
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

            cadie.forEach((Article, Integer) ->
                    updateArticle(Article, Article.getName(), Article.getDesc(),
                            Article.getPrice(), Article.getStock() - Integer));

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
        return compteManager.findAllCompte();
    }

    @Override
    public List<Compte> findAllCompteByUser(int userId) {
        Person person = getUser(userId);
        return compteManager.findAllCompteByUser(person);
    }

    @Override
    public List<Movement> findMovementByCompte(Compte compte){
        return movementManager.findMovementByCompte(compte);
    }

    @Override
    public boolean isBanker(int userId) {
        return personManager.isBanker(getPerson(userId));
    }

    @Override
    public List<Compte> getComptesByAdmin(int userId) {
        return administreManager.getComptesByAdmin(getPerson(userId));
    }

    public void createCompte(String nom, double amount, int userId) {
        Person user = personManager.read(userId);
        Compte compte = new Compte();
        compte.setName(nom);
        compte.setAmount(amount);
        compte.setClient(user);

        int id = compteManager.lastId()+1;
        compte.setIban(Iban.getIban(id));

        compteManager.create(compte);

        setChanged();
        notifyObservers("compte");
    }
}
