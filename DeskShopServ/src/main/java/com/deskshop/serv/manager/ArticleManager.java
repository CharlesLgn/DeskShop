package com.deskshop.serv.manager;

import com.deskshop.common.metier.Article;
import com.deskshop.common.metier.Magasin;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ArticleManager extends HibernateFactory<Article> {
    public ArticleManager() {
        super(Article.class);
    }

    public List<Article> getArticleByMagasin(Magasin magasin) {
        Session session = getSession();
        Query query = session.createQuery("from Article as art where art.shop=:shop");
        query.setParameter("shop", magasin);
        return query.list();
    }
}
