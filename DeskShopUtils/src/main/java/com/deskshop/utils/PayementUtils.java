package com.deskshop.utils;

import com.deskshop.common.metier.Article;

import java.util.HashMap;

public class PayementUtils {

    public static double getTotal(HashMap<Article, Integer> cadie){
        return  (double)Math.round(cadie.entrySet().stream().mapToDouble(c -> c.getKey().getPrice() * c.getValue()).sum()*100)/100;
    }
}
