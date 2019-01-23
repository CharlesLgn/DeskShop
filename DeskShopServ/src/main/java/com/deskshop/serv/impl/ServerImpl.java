package com.deskshop.serv.impl;

import com.deskshop.common.link.ClientInterface;
import com.deskshop.common.link.ServerInterface;
import com.deskshop.common.metier.Compte;
import com.deskshop.common.metier.Magasin;
import com.deskshop.common.metier.Person;
import com.deskshop.serv.manager.MagasinManager;
import com.deskshop.serv.manager.PersonManager;
import com.deskshop.utils.MailUtil;

import java.io.Serializable;
import java.rmi.RemoteException;
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

    @Override
    public void credit(double sum) {
        System.out.println();
        compte.credit(sum);
        setChanged();
        notifyObservers(compte);
    }

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

    @Override
    public List<Magasin> findAllMagasin() throws RemoteException {
        MagasinManager magasinManager = new MagasinManager();
        return magasinManager.findAllMagasin();
    }

    public ServerImpl() {
        compte = new Compte();
        compte.setName("compte courant");
        compte.setAmount(1000);
        new Thread(() -> {
            while (true) setChanged();
        }).start();
    }

}
