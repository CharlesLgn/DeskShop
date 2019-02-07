package com.deskshop.common.link;

import com.deskshop.common.metier.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface ServerInterface extends Remote {
    void addObserver(ClientInterface o) throws RemoteException;

    //__________________________ Manage user __________________________
    int login(String mail, String psw) throws RemoteException;
    int createUser(Person user) throws RemoteException;
    List<Person> findAllUsers() throws RemoteException;
    //_______________________ Shop on DashBoard _______________________
    int createShop(String name, int userId) throws RemoteException;
    List<Magasin> findAllMagasin(int userId) throws RemoteException;
    List<Magasin> findMagasinByUser(int userId) throws RemoteException;

    //_________________________ Manage a Shop _________________________
    void addArticle(int idMagasin, String name, String desc, double price, String image, int stock) throws RemoteException;
    void deleteArticle(Article article) throws RemoteException;
    void updateArticle(Article article, String name, String desc, double price, int stock) throws RemoteException;
    List<Article> getArticleByMagasin(int id) throws RemoteException;
    void uploadFile(Article article, byte[] data, String name) throws RemoteException;

    //_________________________ Go Shopping ___________________________
    boolean paid(HashMap<Article, Integer> cadie, int idUser, int idMagasin) throws RemoteException;

    //______________________ Manage Bank Acount _______________________
    void credit(double sum) throws RemoteException;
    List<Compte> findAllCompteByUser(int userId) throws RemoteException;
    boolean transfert(double somme, Compte compteGiver, Compte compteReceiver) throws RemoteException;

    boolean editSolde(double somme, Compte compteModife) throws RemoteException;
    boolean isBanker(int userId) throws RemoteException;
    List<Compte> getComptesByAdmin(int userId) throws RemoteException;
    List<Compte> findAllCompte() throws RemoteException;
    void createCompte(String nom, double amount, int client) throws  RemoteException;
    //______________________ Manage Movement _______________________
    List<Movement> findMovementByCompte(Compte compte) throws RemoteException;
}
