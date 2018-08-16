package com.revolutionsimulator.bartek.game;

import android.app.Activity;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CashGeneratorActivity extends Activity {
    public static TextView cashDisplay;

    private ArrayList<CashGenerator> listOfGenerators = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    RecyclerView generatorListRecyclerView;
    public static double cashOwned = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_generator);
        initGeneratorRecyclerView();
        cashDisplay = findViewById(R.id.cashDisplay);
        updateDisplay();
    }
    public static void updateDisplay(){
        // this updates the cash display
        cashDisplay.setText("Cash: " + String.format("%.2f", cashOwned));
    }

    public void initGeneratorRecyclerView(){
        listOfGenerators.add(new CashGenerator(true,0.5, "Get Donations", 5000, "cash_gen_one_icon"));
        listOfGenerators.add(new CashGenerator(true,0.6, "Get Money", 10000, "cash_gen_one_icon"));
        generatorListRecyclerView = (RecyclerView) findViewById(R.id.generatorRecyclerView);
        adapter = new RecyclerViewAdapter(listOfGenerators, this);
        generatorListRecyclerView.setAdapter(adapter);
        generatorListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public static double getCashOwned() {
        return cashOwned;
    }

    public static void addCash(double amount){
        cashOwned += amount;
    }
}
