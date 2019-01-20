package com.deskshop.serv.manager;

import com.deskshop.serv.metier.Compte;

public class CompteManager extends HibernateFactory<Compte> {
    CompteManager() {
        super(Compte.class);
    }
}
