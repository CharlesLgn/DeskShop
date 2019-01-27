package com.deskshop.serv.start;

import com.deskshop.common.metier.Article;
import com.deskshop.common.metier.DetailCommande;
import com.deskshop.serv.manager.CommandeManager;
import com.deskshop.serv.manager.DetailCommandeManager;
import com.deskshop.utils.MailUtil;

import java.util.HashMap;
import java.util.List;

public class Test {

    public static void main(String[] args){
        DetailCommandeManager manager = new DetailCommandeManager();
        CommandeManager commandeManager = new CommandeManager();
        List<DetailCommande> commande = manager.getByCommand(commandeManager.read(5));
        HashMap<Article, Integer> hashMap = new HashMap<>();
        for (DetailCommande detail : commande){
            hashMap.put(detail.getArticle(), detail.getQuantity());
        }
        MailUtil.sendFactureMail("charles.ligony@gmail.com", hashMap);
    }
}
