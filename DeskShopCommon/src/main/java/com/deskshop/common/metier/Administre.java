package com.deskshop.common.metier;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "administre")
public class Administre implements Serializable {
    private int id;
    private Compte compte;
    private Person getionar;

    public Administre(Compte compte, Person getionar) {
        this.id = -1;
        this.compte = compte;
        this.getionar = getionar;
    }

    public Administre() {
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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_compte")
    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_admin")
    public Person getGetionar() {
        return getionar;
    }

    public void setGetionar(Person getionar) {
        this.getionar = getionar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administre that = (Administre) o;
        return id == that.id && Objects.equals(compte, that.compte) && Objects.equals(getionar, that.getionar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, compte, getionar);
    }
}
