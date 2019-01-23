package com.deskshop.common.link;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    void update(Object observable, Object updateMsg) throws RemoteException;
}
