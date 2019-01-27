package com.deskshop.serv.impl;

import com.deskshop.common.link.ClientInterface;
import com.deskshop.common.link.ServerInterface;
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
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ServerImpl extends Observable implements ServerInterface {
    private Compte compte;

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
        PersonManager personManager = new PersonManager();
        return personManager.Connect(mail, psw);
    }

    @Override
    public int createUser(Person user) {
        try {
            //create user
            PersonManager manager = new PersonManager();
            user = manager.create(user);
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
            PersonManager personManager = new PersonManager();
            Person user = personManager.read(userId);

            //get magasin
            MagasinManager manager = new MagasinManager();
            Magasin magasin = new Magasin();
            magasin.setCreator(user);
            magasin.setName(name);
            magasin = manager.create(magasin);
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
        MagasinManager magasinManager = new MagasinManager();
        return magasinManager.findAllMagasin(person);
    }

    @Override
    public List<Magasin> findMagasinByUser(int userId) {
        Person person = getUser(userId);
        MagasinManager magasinManager = new MagasinManager();
        return magasinManager.findMagasinByUser(person);
    }

    private Person getUser(int id){
        PersonManager personManager = new PersonManager();
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
        ArticleManager manager = new ArticleManager();
        manager.create(article);
        setChanged();
        notifyObservers("article");
    }

    @Override
    public List<Article> getArticleByMagasin(int id) {
        ArticleManager manager = new ArticleManager();
        return manager.getArticleByMagasin(getMagasin(id));
    }

    private Magasin getMagasin(int id){
        MagasinManager magasinManager = new MagasinManager();
        return magasinManager.read(id);
    }

    @Override
    public void paid(HashMap<Article, Integer> cadie, int idUser, int idMagasin) throws RemoteException {

    }

    @Override
    public void credit(double sum) {
        System.out.println();
        compte.credit(sum);
        setChanged();
        notifyObservers(compte);
    }

    public ServerImpl() {
        compte = new Compte();
        compte.setName("compte courant");
        compte.setAmount(1000);
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
}
