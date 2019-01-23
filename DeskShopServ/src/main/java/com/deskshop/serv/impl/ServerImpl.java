package com.deskshop.serv.impl;

import com.deskshop.common.link.ClientInterface;
import com.deskshop.common.link.ServerInterface;
import com.deskshop.common.metier.Compte;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class ServerImpl extends Observable implements ServerInterface {
    Compte compte;

    private class WrappedObserver implements Observer, Serializable {

        private static final long serialVersionUID = 1L;

        private ClientInterface ro = null;

        public WrappedObserver(ClientInterface ro) {
            this.ro = ro;
        }

        @Override
        public void update(Observable o, Object arg) {
            try {
                ro.update(o.toString(), arg);
            } catch (RemoteException e) {
                System.out
                        .println("Remote exception removing observer:" + this);
                o.deleteObserver(this);
            }
        }

    }

    @Override
    public void addObserver(ClientInterface o) throws RemoteException {
        WrappedObserver mo = new WrappedObserver(o);
        addObserver(mo);
        System.out.println("Added observer:" + mo);
    }

    @Override
    public void credit(double sum) throws RemoteException {
        System.out.println();
        compte.credit(sum);
        setChanged();
        notifyObservers(compte);
    }

    public ServerImpl() {
        compte = new Compte();
        compte.setName("compte courant");
        compte.setAmount(1000);
        new Thread(() -> {while (true)setChanged();}).start();
    }

    private static final long serialVersionUID = 1L;
}
