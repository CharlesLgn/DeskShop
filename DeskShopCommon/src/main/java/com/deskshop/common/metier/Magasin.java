package com.deskshop.common.metier;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "magasin")
public class Magasin implements Serializable {
    private int id;
    private String name;
    private String iban;
    private Person creator;

    public Magasin(String name, Person creator) {
        this.id = -1;
        this.name = name;
        this.creator = creator;
    }
    public Magasin() {
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


    @Column(name = "iban")
    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_personne")
    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return name + " by " +creator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Magasin magasin = (Magasin) o;
        return id == magasin.id && Objects.equals(name, magasin.name) && Objects.equals(creator, magasin.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creator);
    }
}
