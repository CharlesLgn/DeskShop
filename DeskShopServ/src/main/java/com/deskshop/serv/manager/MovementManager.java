package com.deskshop.serv.manager;

import com.deskshop.serv.metier.Movement;

public class MovementManager extends HibernateFactory<Movement> {
    MovementManager() {
        super(Movement.class);
    }
}
