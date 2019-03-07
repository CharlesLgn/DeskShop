package com.deskshop.common.metier;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "compte")
public class Compte implements Serializable {
    private int id;
    private String name;
    private double amount;
    private String iban;
    private Person client;

    public Compte(String name, double amount, Person client, String iban) {
        this.id = -1;
        this.name = name;
        setAmount(amount);
        this.client = client;
        this.iban = iban;
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

    @Column(name = "iban")
    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    @Override
    public String toString() {
        return name;
    }

    public void credit(double sum){
        setAmount(getAmount()+sum);
    }

    public void debit(double sum){
        setAmount(getAmount()-sum);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Compte && ((Compte)obj).getId() == this.id ;
    }
}
