package com.deskshop.serv.metier;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "commande")
public class Commande {
    private int id;
    private Timestamp date;
    private int quantity;
    private Person client;
    private Magasin shop;

    public Commande() {
        this.id = -1;
    }

    public Commande(Timestamp date, int quantity, Person client, Magasin shop) {
        this.id = -1;
        this.date = date;
        this.quantity = quantity;
        this.client = client;
        this.shop = shop;
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

    @Column(name = "date_achat")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Column(name = "qtt_cmde")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_personne")
    public Person getClient() {
        return client;
    }

    public void setClient(Person client) {
        this.client = client;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commande commande = (Commande) o;
        return id == commande.id && quantity == commande.quantity && Objects.equals(date, commande.date) && Objects.equals(client, commande.client) && Objects.equals(shop, commande.shop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, quantity, client, shop);
    }
}
