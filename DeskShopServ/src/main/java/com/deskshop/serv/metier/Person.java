package com.deskshop.serv.metier;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "personne")
public class Person {
    private int id;
    private String name;
    private String firstName;
    private String mel;
    private String psw;

    public Person(String name, String firstName, String mel, String psw) {
        this.id=-1;
        this.name = name;
        this.firstName = firstName;
        this.mel = mel;
        this.psw = psw;
    }

    public Person() {
        this.id=-1;
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

    @Column(name = "prenom")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "mail")
    public String getMel() {
        return mel;
    }

    public void setMel(String mel) {
        this.mel = mel;
    }

    @Column(name = "password")
    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    @Override
    public String toString() {
        return name + ' ' + firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(name, person.name) && Objects.equals(firstName, person.firstName) && Objects.equals(mel, person.mel) && Objects.equals(psw, person.psw);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, firstName, mel, psw);
    }
}
