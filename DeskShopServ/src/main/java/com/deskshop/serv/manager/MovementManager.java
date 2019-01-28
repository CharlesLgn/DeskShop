package com.deskshop.serv.manager;

import com.deskshop.common.metier.Compte;
import com.deskshop.common.metier.Movement;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class MovementManager extends HibernateFactory<Movement> {
    public MovementManager() {
        super(Movement.class);
    }

    public List<Movement> findMovementByCompte(Compte compte) {
        Session session = getSession();
        Query query = session.createQuery("from Movement as movement where movement.compte = :compte ");
        query.setParameter("compte", compte);
        return query.list();
    }
}
