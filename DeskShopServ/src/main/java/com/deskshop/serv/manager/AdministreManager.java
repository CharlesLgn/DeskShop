package com.deskshop.serv.manager;

import com.deskshop.common.metier.Administre;

public class AdministreManager extends HibernateFactory<Administre> {
    AdministreManager() {
        super(Administre.class);
    }
}
