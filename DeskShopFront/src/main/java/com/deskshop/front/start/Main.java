package com.deskshop.front.start;

import com.deskshop.common.link.ServerInterface;

import java.rmi.Naming;

public class Main {
    public static void main(String[] args) {
        try {

            ServerInterface remoteService = (ServerInterface) Naming.lookup("//localhost:4000/serverInterface");

            remoteService.createShop("Rue du commerce !!", 2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
