package com.deskshop.serv.manager;

import com.deskshop.common.metier.Compte;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CompteManager extends HibernateFactory<Compte> {
    public CompteManager() {
        super(Compte.class);
    }


    public List<Compte> findAllCompte() {
        Session session = getSession();
        Query query = session.createQuery("from Compte as compte");
        return query.list();
    }
}
