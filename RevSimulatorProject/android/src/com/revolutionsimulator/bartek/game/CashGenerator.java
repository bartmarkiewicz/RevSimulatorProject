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

    private boolean isBought;
    private double baseCashGain;
    private String generatorName;
    private String generatorIcon;
    private int currentProgress = 0;

    private int timeToGetMoney;
    private double generatorUpgradeCost;
    private double baseCost;
    private double generatorPurchasePrice;


    private double cashGainedWithProgress;
    private int generatorLevel = 1;

    public void buyGenerator(){
        isBought = true;
    }

    public boolean isBought() {
		// this returns the boolean isBought
        return isBought;
    }

    public void setGeneratorLevel(int level){
        generatorLevel = level;
    }

    public void incrementCurrentProgress(){
		// this method increments the current progress - but might be redundant since there is already a setCurrentProgress method
        currentProgress++;
    }

    public void resetCurrentProgress(){
		// this resets the current progress - method might be redundant since there is already a setprogressbar method
        currentProgress = 0;
    }

    public int getCurrentProgress(){
		// this returns the current progress of the generator
        return currentProgress;
    }

    public String getGeneratorName() {
		// this returns the generator name string
        return generatorName;
    }

    public String getGeneratorIcon() {
		// this returns the generatorIcon string 
        return generatorIcon;
    }

    public double getCashGainedWithProgress() {
		// this method returns the amount of cash gained with each generator completion 
        return baseCashGain*generatorLevel*0.9;
    }

    public int getGeneratorLevel() {
		// this returns the current generator upgrade level
        return generatorLevel;
    }

    public int getTimeToGetMoney() {
		// this returns the time in millis it takes for the generator to give money
        return timeToGetMoney;
    }

    public void setCurrentProgress(int currentProgress) {
		// this is the method for setting currentProgress of the progress bar
        this.currentProgress = currentProgress;
    }

    public CashGenerator(boolean isBought, double baseCashGain, double baseCost, String generatorName, int timeToGetMoney, String generatorIcon, double generatorPurchasePrice) {
		// this is the constructor
		this.isBought = isBought;
        this.baseCashGain = baseCashGain;
        this.generatorName = generatorName;
        this.timeToGetMoney = timeToGetMoney;
        this.generatorIcon = generatorIcon;
        this.baseCost = baseCost;
        this.currentProgress = 0;
        this.generatorPurchasePrice = generatorPurchasePrice;


    }

    public double getGeneratorPurchasePrice(){
        return generatorPurchasePrice;
    }

    public double upgradeGenerator(double cash, double cost){
        //this is the method for upgrading the generator
		generatorLevel++;
		cash = cash-cost;
        return cash;
    }

    public double getGeneratorUpgradeCost() {
		// this is the method for determining the generator upgrade cost
        generatorUpgradeCost=baseCost*Math.pow(1.15, generatorLevel);
        return generatorUpgradeCost;
    }
}
