package com.deskshop.serv.manager;

import com.deskshop.serv.metier.Commande;

public class CommandeManager extends HibernateFactory<Commande> {
    CommandeManager() {
        super(Commande.class);
    }
}
