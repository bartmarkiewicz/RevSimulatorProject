package com.revolutionsimulator.bartek.game;

import android.os.AsyncTask;
import android.os.CountDownTimer;

public class CashGenerator {
    /*
    this should be a class you instantiate into an object and it represents
    the data and workings of a particular cash generator
    by providing the constructor with different parameters you create different
    types of cash generators

    OR it could be an abstract class which every cash generator extends, and I would have classes
    for every single cash generator (seems like an awful lot of work)
     */

    //variables for constructor -
    private boolean isBought;
    private double baseCashGain;
    private String generatorName;
    private String generatorIcon;
    private int currentProgress = 0;

    private int timeToGetMoney;
    private double generatorUpgradeCost;
    private double baseCost;



    // variables which change
    private double cashGainedWithProgress;
    private int generatorLevel = 1;


    public boolean isBought() {
        return isBought;
    }

    public void incrementCurrentProgress(){
        currentProgress++;
    }

    public void resetCurrentProgress(){
        currentProgress = 0;
    }

    public int getCurrentProgress(){
        return currentProgress;
    }

    public String getGeneratorName() {
        return generatorName;
    }

    public String getGeneratorIcon() {
        return generatorIcon;
    }

    public double getCashGainedWithProgress() {
        return baseCashGain*generatorLevel*0.9;
    }

    public int getGeneratorLevel() {
        return generatorLevel;
    }

    public int getTimeToGetMoney() {
        return timeToGetMoney;
    }

    public void setCurrentProgress(int currentProgress) {

        this.currentProgress = currentProgress;
    }

    public CashGenerator(boolean isBought, double baseCashGain, double baseCost, String generatorName, int timeToGetMoney, String generatorIcon) {
        this.isBought = isBought;
        this.baseCashGain = baseCashGain;
        this.generatorName = generatorName;
        this.timeToGetMoney = timeToGetMoney;
        this.generatorIcon = generatorIcon;
        this.baseCost = baseCost;
        this.currentProgress = 0;

    }

    public double upgradeGenerator(double cash, double cost){
            generatorLevel++;
            cash = cash-cost;
        return cash;
    }

    public double getGeneratorUpgradeCost() {
        generatorUpgradeCost=baseCost*Math.pow(1.15, generatorLevel);
        return generatorUpgradeCost;
    }
}
