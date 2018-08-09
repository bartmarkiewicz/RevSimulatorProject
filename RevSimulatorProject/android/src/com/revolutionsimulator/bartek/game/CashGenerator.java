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
    private double initialCashGain;
    private String generatorName;
    private String generatorIcon;
    private int currentProgress;
    public double cashToBeGained =0;
    // 0 to 1 -
    private int timeToGetMoney = 5000;



    // variables which change
    private double cashGainedWithProgress;
    private int generatorLevel = 1;


    public boolean isBought() {
        return isBought;
    }

    public double getInitialCashGain() {
        return initialCashGain;
    }

    public String getGeneratorName() {
        return generatorName;
    }

    public String getGeneratorIcon() {
        return generatorIcon;
    }

    public double getCashToBeGained() {
        return cashToBeGained;
    }

    public int getGeneratorLevel() {
        return generatorLevel;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {

        this.currentProgress = currentProgress;
    }

    public CashGenerator(boolean isBought, double initialCashGain, String generatorName, int timeToGetMoney, String generatorIcon) {
        this.isBought = isBought;
        this.initialCashGain = initialCashGain;
        this.generatorName = generatorName;
        this.timeToGetMoney = timeToGetMoney;
        this.generatorIcon = generatorIcon;
        this.cashGainedWithProgress = initialCashGain*generatorLevel*0.50;
        this.currentProgress = 0;

    }

    public double upgradeGenerator(double cash, double cost){
        if (cash>cost){
            generatorLevel++;
            cash = cash-cost;
        }
        return cash;
    }

    public void getMoney(final double currentCash){
        CountDownTimer countDownTimer = new CountDownTimer(timeToGetMoney,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentProgress = Math.round((millisUntilFinished/timeToGetMoney)/100);
            }

            @Override
            public void onFinish() {
                currentProgress = 0;
                cashToBeGained += cashGainedWithProgress;
            }
        }.start();
    }
}
