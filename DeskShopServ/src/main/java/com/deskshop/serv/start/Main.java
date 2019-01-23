package com.deskshop.serv.start;

import com.deskshop.common.link.ServerInterface;
import com.deskshop.serv.impl.ServerImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String[] args) {
        try {
            Registry rmiRegistry = LocateRegistry.createRegistry(720);
            ServerInterface serverInterface = (ServerInterface) UnicastRemoteObject
                    .exportObject(new ServerImpl(), 720);
            rmiRegistry.bind("serverInterface", serverInterface);
            System.out.println("server ready");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
