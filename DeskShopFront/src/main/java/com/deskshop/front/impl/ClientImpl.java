package com.deskshop.front.impl;

import com.deskshop.common.link.ClientInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface, Serializable {
    public ClientImpl() throws RemoteException {
        super();
    }

    private static final long serialVersionUID = 1L;

    @Override
    public void update(Object observable, Object updateMsg) {
        System.out.println("got message:" + updateMsg);
    }
}
