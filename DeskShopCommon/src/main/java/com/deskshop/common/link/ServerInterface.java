package com.deskshop.common.link;

import com.deskshop.common.metier.Article;
import com.deskshop.common.metier.Compte;
import com.deskshop.common.metier.Magasin;
import com.deskshop.common.metier.Person;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface ServerInterface extends Remote {
    void addObserver(ClientInterface o) throws RemoteException;

    //__________________________ Manage user __________________________
    int login(String mail, String psw) throws RemoteException;
    int createUser(Person user) throws RemoteException;

    //_______________________ Shop on DashBoard _______________________
    int createShop(String name, int userId) throws RemoteException;
    List<Magasin> findAllMagasin(int userId) throws RemoteException;
    List<Magasin> findMagasinByUser(int userId) throws RemoteException;

    //_________________________ Manage a Shop _________________________
    void addArticle(int idMagasin, String name, String desc, double price) throws RemoteException;
    List<Article> getArticleByMagasin(int id) throws RemoteException;

    //_________________________ Go Shopping ___________________________
    void paid(HashMap<Article, Integer> cadie, int idUser, int idMagasin) throws RemoteException;

    //______________________ Manage Bank Acount _______________________
    void credit(double sum) throws RemoteException;
    List<Compte> findAllCompte() throws RemoteException;
    List<Compte> findAllCompteByUser(int userId) throws RemoteException;
}
