package com.deskshop.common.metier;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "detail_commande")
public class DetailCommande implements Serializable {
    private Commande commande;
    private Article  article;
    private int quantity;

    public DetailCommande(Commande commande, Article article, int quantity) {
        this.commande = commande;
        this.article = article;
        this.quantity = quantity;
    }

    public DetailCommande() {}

    @Id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_commande")
    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    @Id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_article")
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }


    @Column(name = "qtt_cmde")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailCommande that = (DetailCommande) o;
        return quantity == that.quantity && Objects.equals(commande, that.commande) && Objects.equals(article, that.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commande, article, quantity);
    }

    @Override
    public String toString() {
        return "DetailCommande{" + "commande=" + commande + ", article=" + article + ", quantity=" + quantity + '}';
    }
}
