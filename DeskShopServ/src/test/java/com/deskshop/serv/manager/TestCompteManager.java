package com.deskshop.serv.manager;

import com.deskshop.common.metier.Compte;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestCompteManager {

    @Test
    public void testRead() {
        CompteManager dao = mock(CompteManager.class);
        Compte monCompteTest = new Compte();
        monCompteTest.setAmount(300);
        when(dao.read(Mockito.anyInt())).thenReturn(monCompteTest);
        Compte compte = dao.read(0);
        assertThat(compte).isEqualTo(monCompteTest);
    }


}
