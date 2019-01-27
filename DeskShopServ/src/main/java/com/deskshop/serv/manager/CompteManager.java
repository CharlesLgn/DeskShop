package com.deskshop.serv.manager;

import com.deskshop.common.metier.Compte;
import com.deskshop.common.metier.Person;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CompteManager extends HibernateFactory<Compte> {
    public CompteManager() {
        super(Compte.class);
    }

    public Compte getCompteByPersonne(Person person) {
        Session session = getSession();
        Query query = session.createQuery("from Compte as compte where compte.client = :person ");
        query.setParameter("person", person);
        List<Compte> list = query.list();
        for (Compte compte : list) {
            return compte;
        }
        return null;
    }

    public List<Compte> findAllCompte() {
        Session session = getSession();
        Query query = session.createQuery("from Compte as compte");
        return query.list();
    }
}
