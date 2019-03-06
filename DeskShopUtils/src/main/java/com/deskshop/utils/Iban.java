package com.deskshop.utils;

public class Iban {

    public static String getIban(int x){
        StringBuilder iban = new StringBuilder(x);
        while (iban.length()<18){
            iban.insert(0, "0");
        }
        iban.insert(0, "FR");
        return iban.toString();
    }
}
