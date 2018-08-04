package com.revolutionsimulator.bartek.game;

public class CashGenerator extends Thread{

    boolean isBought = false;

    private double cashGain;



    public CashGenerator(){

    }
    public void buyGenerator(){
        isBought = true;
    }

    public double getCashGain(){
        return 2.0;
    }
}
