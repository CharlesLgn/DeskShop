package com.deskshop.serv.impl;

import com.deskshop.common.link.ClientInterface;
import com.deskshop.common.link.ServerInterface;
import com.deskshop.common.link.ShopInterface;
import com.deskshop.common.metier.Article;
import com.deskshop.common.metier.Compte;
import com.deskshop.common.metier.Magasin;
import com.deskshop.serv.manager.ArticleManager;
import com.deskshop.serv.manager.MagasinManager;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ShopImpl extends Observable implements ShopInterface {
    Magasin magasin;

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

    @Override
    public void addArticle(String name, String desc, double price) {
        Article article = new Article();
        article.setName(name);
        article.setDesc(desc);
        article.setPrice(price);
        article.setShop(magasin);
        ArticleManager manager = new ArticleManager();
        manager.create(article);
        setChanged();
        notifyObservers("0");
    }

    @Override
    public List<Article> getArticleByMagasin() {
        ArticleManager manager = new ArticleManager();
        return manager.getArticleByMagasin(magasin);
    }

    public ShopImpl(int id) {
        MagasinManager manager = new MagasinManager();
        magasin = manager.read(id);
        new Thread(() -> {
            while (true) setChanged();
        }).start();
    }
}
