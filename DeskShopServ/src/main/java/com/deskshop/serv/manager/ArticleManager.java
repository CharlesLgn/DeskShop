package com.deskshop.serv.manager;

import com.deskshop.common.metier.Article;

public class ArticleManager extends HibernateFactory<Article> {
    ArticleManager() {
        super(Article.class);
    }
}
