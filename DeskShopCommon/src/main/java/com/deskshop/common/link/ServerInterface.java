package com.deskshop.common.link;

import com.deskshop.common.metier.Magasin;
import com.deskshop.common.metier.Person;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerInterface extends Remote {
    void addObserver(ClientInterface o) throws RemoteException;
    void credit(double sum) throws RemoteException;
    int login(String mail, String psw) throws RemoteException;
    int createUser(Person user) throws RemoteException;
    int createShop(String name, int userId) throws RemoteException;

    List<Magasin> findAllMagasin(int userId) throws RemoteException;
    List<Magasin> findMagasinByUser(int userId) throws RemoteException;
}
