package com.deskshop.serv.metier;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "mouvement")
public class Movement {
    private int id;
    private Timestamp date;
    private double amount;
    private Compte compte;

    public Movement(Timestamp date, double amount, Compte compte) {
        this.id = -1;
        this.date = date;
        this.amount = amount;
        this.compte = compte;
    }

    public Movement() {
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

    @Column(name = "date_move")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Column(name = "montant")
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_compte")
    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return id == movement.id && Double.compare(movement.amount, amount) == 0 && Objects.equals(date, movement.date) && Objects.equals(compte, movement.compte);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, amount, compte);
    }
}
