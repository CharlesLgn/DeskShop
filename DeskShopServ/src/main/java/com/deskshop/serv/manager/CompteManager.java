package com.deskshop.serv.manager;

import com.deskshop.common.metier.Compte;

public class CompteManager extends HibernateFactory<Compte> {
    CompteManager() {
        super(Compte.class);
    }
}
