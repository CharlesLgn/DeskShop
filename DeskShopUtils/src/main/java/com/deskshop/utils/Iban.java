package com.deskshop.utils;

public class Iban {

    public static String getIban(int x){
        String iban = ""+x;
        while (iban.length()<10){
            iban = "0" + iban;
        }
        iban = "FR" + iban;
        return iban;
    }
}
