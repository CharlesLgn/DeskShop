package com.deskshop.front.start;

import com.deskshop.common.link.ServerInterface;

import java.rmi.Naming;

public class test {
    public static void main(String[] args) {
        try {
            ServerInterface remoteService = (ServerInterface) Naming.lookup("//localhost:720/serverInterface");
            remoteService.credit(20);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

