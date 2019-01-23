package com.deskshop.common.link;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    void addObserver(ClientInterface o) throws RemoteException;
    void credit(double sum) throws RemoteException;
}
