package com.deskshop.front.start;

import com.deskshop.common.link.ClientInterface;
import com.deskshop.common.link.ServerInterface;
import com.deskshop.front.impl.ClientImpl;

import java.rmi.Naming;

public class Main {
    public static void main(String[] args) {
        try {

            ServerInterface remoteService = (ServerInterface) Naming.lookup("//localhost:720/serverInterface");

            ClientInterface client = new ClientImpl();
            remoteService.addObserver(client);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
