package com.deskshop.serv.manager;

import com.deskshop.common.metier.Movement;

public class MovementManager extends HibernateFactory<Movement> {
    public MovementManager() {
        super(Movement.class);
    }
}
