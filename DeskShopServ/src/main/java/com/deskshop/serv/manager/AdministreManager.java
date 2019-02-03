package com.deskshop.serv.manager;

import com.deskshop.common.metier.Administre;
import com.deskshop.common.metier.Compte;
import com.deskshop.common.metier.Person;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class AdministreManager extends HibernateFactory<Administre> {
    public AdministreManager() {
        super(Administre.class);
    }

    public List<Compte> getComptesByAdmin(Person person) {
        Session session = getSession();
        Query query = session.createQuery("Select admin.compte from Administre as admin where admin.getionar=:person");
        query.setParameter("person", person);
        return query.list();
    }
}
