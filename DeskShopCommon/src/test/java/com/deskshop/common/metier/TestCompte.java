package com.deskshop.common.metier;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

public class TestCompte {
  private Compte compte;

  @Before
  public void init(){
    compte = new Compte();
    compte.setAmount(200);
  }

  /*_________Credit_____________*/
  @Test
  public void testCompteCredit_casStandart(){
    //give
    double sum = 100.;
    //when
    compte.credit(sum);
    //then
    assertThat(compte.getAmount()).isEqualTo(300.);
  }

  @Test
  public void testCompteCredit_cas0(){
    //give
    double sum = 0.;
    //when
    compte.credit(sum);
    //then
    assertThat(compte.getAmount()).isEqualTo(200.);
  }

  @Test
  public void testCompteCredit_casDecimal(){
    //give
    double sum = 50.99;
    //when
    compte.credit(sum);
    //then
    assertThat(compte.getAmount()).isEqualTo(250.99);
  }

  @Test
  public void testCompteCredit_casNegatifStandart(){
    //give
    double sum = -100.;
    //when
    compte.credit(sum);
    //then
    assertThat(compte.getAmount()).isEqualTo(100.);
  }

  @Test
  public void testCompteCredit_casNegatif0(){
    //give
    double sum = -0.;
    //when
    compte.credit(sum);
    //then
    assertThat(compte.getAmount()).isEqualTo(200.);
  }

  @Test
  public void testCompteCredit_casNegatifDecimal(){
    //give
    double sum = -50.99;
    //when
    compte.credit(sum);
    //then
    assertThat(compte.getAmount()).isEqualTo(149.01);
  }

  @Test
  public void testCompteCredit_casNegatifTrop(){
    //give
    double sum = -250;
    //when
    try{
      compte.credit(sum);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    } catch (IllegalArgumentException e){
      //then
      assertThat(e).hasMessage("sum c'ant be negative");
    }
  }

  /*_________Debit_____________*/
  @Test
  public void testCompteDebit_casStandart(){
    //give
    double sum = 100.;
    //when
    compte.debit(sum);
    //then
    assertThat(compte.getAmount()).isEqualTo(100.);
  }

  @Test
  public void testCompteDebit_cas0(){
    //give
    double sum = 0.;
    //when
    compte.debit(sum);
    //then
    assertThat(compte.getAmount()).isEqualTo(200.);
  }

  @Test
  public void testCompteDebit_casDecimal(){
    //give
    double sum = 50.99;
    //when
    compte.debit(sum);
    //then
    assertThat(compte.getAmount()).isEqualTo(149.01);
  }

  @Test
  public void testCompteDebit_casTrop(){
    //give
    double sum = 250;
    //when
    try{
      compte.debit(sum);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    } catch (IllegalArgumentException e){
      //then
      assertThat(e).hasMessage("sum c'ant be negative");
    }
  }

  @Test
  public void testCompteDebit_casNegatifStandart(){
    //give
    double sum = -100.;
    //when
    compte.debit(sum);
    //then
    assertThat(compte.getAmount()).isEqualTo(300.);
  }

  @Test
  public void testCompteDebit_casNegatif0(){
    //give
    double sum = -0.;
    //when
    compte.debit(sum);
    //then
    assertThat(compte.getAmount()).isEqualTo(200.);
  }

  @Test
  public void testCompteDebit_casNegatifDecimal(){
    //give
    double sum = -50.99;
    //when
    compte.debit(sum);
    //then
    assertThat(compte.getAmount()).isEqualTo(250.99);
  }
}
