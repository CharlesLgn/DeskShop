package com.deskshop.serv.manager;

import com.deskshop.common.metier.Commande;
import com.deskshop.common.metier.Compte;
import com.deskshop.common.metier.DetailCommande;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DetailCommandeManager extends HibernateFactory<DetailCommande> {

    public DetailCommandeManager() {
        super(DetailCommande.class);
    }

    public List<DetailCommande> getByCommand(Commande commande){
        Session session = getSession();
        Query query = session.createQuery("from DetailCommande as detail where detail.commande = :command ");
        query.setParameter("command", commande);
        return query.list();
    }
}
