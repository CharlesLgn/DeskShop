package com.deskshop.front.start;

import com.deskshop.common.link.ServerInterface;
import com.deskshop.common.metier.Article;

import java.rmi.Naming;
import java.util.HashMap;
import java.util.stream.Collectors;

public class test {
    public static void main(String[] args) {
        HashMap<Article, Integer> hashMap = new HashMap<>();
        Article art1 = new Article();
        Article art2 = new Article();
        Article art3 = new Article();
        art1.setPrice(1.5);
        art2.setPrice(15.7);
        art3.setPrice(8.99);

        hashMap.put(art1, 2);
        hashMap.put(art2, 4);
        hashMap.put(art3, 5);

        System.out.println( hashMap.entrySet().stream().mapToDouble(c -> c.getKey().getPrice() * c.getValue()).sum());
    }
}

