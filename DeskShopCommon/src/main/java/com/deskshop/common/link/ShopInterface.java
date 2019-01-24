package com.deskshop.common.link;

import com.deskshop.common.metier.Article;
import com.deskshop.common.metier.Magasin;

import java.rmi.RemoteException;
import java.util.List;

public interface ShopInterface {
    void addObserver(ClientInterface o) throws RemoteException;
    void addArticle(String name, String desc, double price) throws RemoteException;
    List<Article> getArticleByMagasin(Magasin magasin) throws RemoteException;
}
