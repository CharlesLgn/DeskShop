package com.deskshop.serv.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class HibernateFactory<T> {
    private Class<T> tType;

    HibernateFactory(Class<T> tType) {
        this.tType = tType;
    }

    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration cfg = new Configuration();
            sessionFactory = cfg.configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            Logger.getLogger(HibernateFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public T create(T t) {
        Session session = getSession();
        session.beginTransaction();
        int id = (int) session.save(t);
        session.getTransaction().commit();
        session.close();
        return read(id);
    }

    public T read(int id) {
        Session session = getSession();
        T t = session.get(tType, id);
        session.close();
        return t;
    }

    public void delete(T t) {
        Session session = getSession();
        session.beginTransaction();
        session.delete(t);
        session.getTransaction().commit();
        session.close();
    }

    public void update(T t) {
        Session session = getSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
        session.close();
    }

}
