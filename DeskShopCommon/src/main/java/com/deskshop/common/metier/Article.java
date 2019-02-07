package com.deskshop.common.metier;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "article")
public class Article implements Serializable {
    private int id;
    private String name;
    private String picture;
    private String desc;
    private double price;
    private Magasin shop;
    private int stock;

    public Article() {
        this.id = -1;
    }

    public Article(String name, String picture, String desc, double price, Magasin shop, int stock) {
        this.id = -1;
        this.name = name;
        this.picture = picture;
        this.desc = desc;
        this.price = price;
        this.shop = shop;
        this.stock = stock;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "nom")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "image")
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Column(name = "description")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Column(name = "prix")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "stock")
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_magasin")
    public Magasin getShop() {
        return shop;
    }

    public void setShop(Magasin shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
        return name + " : " + price + "€";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id && Double.compare(article.price, price) == 0 && Objects.equals(name, article.name) && Objects.equals(picture, article.picture) && Objects.equals(desc, article.desc) && Objects.equals(shop, article.shop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, picture, desc, price, shop);
    }
}
