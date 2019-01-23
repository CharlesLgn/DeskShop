package com.deskshop.serv.manager;

import com.deskshop.common.metier.Magasin;

public class MagasinManager extends HibernateFactory<Magasin> {
    MagasinManager() {
        super(Magasin.class);
    }
}
