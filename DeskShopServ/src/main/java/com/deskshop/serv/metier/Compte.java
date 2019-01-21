package com.deskshop.serv.metier;

import javax.persistence.*;

@Entity
@Table(name = "compte")
public class Compte {
    private int id;
    private String name;
    private double amount;
    private Person client;

    public Compte(String name, double amount, Person client) {
        this.id = -1;
        this.name = name;
        setAmount(amount);
        this.client = client;
    }

    public Compte() {
        this.id = -1;
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

    @Column(name = "solde")
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if(amount < 0.){
            throw new IllegalArgumentException("sum c'ant be negative");
        }
        this.amount = amount;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_personne")
    public Person getClient() {
        return client;
    }

    public void setClient(Person client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return name + " de " + client + " : " + amount;
    }

    public void credit(double sum){
        setAmount(getAmount()+sum);
    }

    public void debit(double sum){
        setAmount(getAmount()-sum);
    }
}
