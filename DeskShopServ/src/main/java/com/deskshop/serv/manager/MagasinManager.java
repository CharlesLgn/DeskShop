package com.deskshop.serv.manager;

import com.deskshop.common.metier.Magasin;
import com.deskshop.common.metier.Person;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class MagasinManager extends HibernateFactory<Magasin> {
    public MagasinManager() {
        super(Magasin.class);
    }

    public List<Magasin> findAllMagasin() {
        Session session = getSession();
        Query query = session.createQuery("from Magasin as magasin");
        return query.list();
    }

    public List<Magasin> findMagasinByUser(Person person) {
        Session session = getSession();
        Query query = session.createQuery("from Magasin as magasin where magasin.creator = :person ");
        query.setParameter("person", person);
        return query.list();
    }
}
