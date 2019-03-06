package com.deskshop.utils;

public class Iban {

    /**
     * crré un Iban sous la form : "FR[0]{nbChiffreId}id"
     * @param x l'id
     * @return
     */
    public static String getIban(int x){
        String iban = ""+x;
        while (iban.length()<10){
            iban = "0" + iban;
        }
        iban = "FR" + iban;
        return iban;
    }
}
