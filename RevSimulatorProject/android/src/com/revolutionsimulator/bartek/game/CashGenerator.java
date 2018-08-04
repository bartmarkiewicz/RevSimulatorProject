package com.revolutionsimulator.bartek.game;

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
    // 0 to 1 -
    private double speedOfGenerator;



    // variables which change
    private double cashGainedWithProgress;
    private int generatorLevel = 1;



    public CashGenerator(boolean isBought, double initialCashGain, String generatorName, double speedOfGenerator, String generatorIcon) {
        this.isBought = isBought;
        this.initialCashGain = initialCashGain;
        this.generatorName = generatorName;
        this.speedOfGenerator = speedOfGenerator;
        this.generatorIcon = generatorIcon

    }

    public String getGeneratorName() {
        return generatorName;
    }

    public String getGeneratorIcon() {
        return generatorIcon;
    }

    public double getCashGainedWithProgress() {
        return cashGainedWithProgress;
    }

    public int getGeneratorLevel() {
        return generatorLevel;
    }

    public void buyGenerator(){
        isBought = true;
        cashGainedWithProgress = initialCashGain;
    }

    public double upgradeGenerator(double cash, double cost){
        if (cash>cost){
            generatorLevel++;
            cash = cash-cost;
        }
        return cash;
    }

    public double gainCash(){
        return cashGainedWithProgress;
    }
}
