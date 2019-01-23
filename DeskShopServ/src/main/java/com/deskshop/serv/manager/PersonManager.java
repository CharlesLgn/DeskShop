package com.deskshop.serv.manager;

import com.deskshop.common.metier.Person;

public class PersonManager extends HibernateFactory<Person> {
    PersonManager() {
        super(Person.class);
    }
}
