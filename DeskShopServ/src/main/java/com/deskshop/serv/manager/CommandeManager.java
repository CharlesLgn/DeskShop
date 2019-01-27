package com.deskshop.serv.manager;

import com.deskshop.common.metier.Commande;

public class CommandeManager extends HibernateFactory<Commande> {
    public CommandeManager() {
        super(Commande.class);
    }
}
