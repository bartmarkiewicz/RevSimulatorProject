package com.revolutionsimulator.bartek.game;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class CashGeneratorActivity extends Activity implements Runnable{
    private static final String TAG = "CashGeneratorActivity";

    private ArrayList<CashGenerator> listOfGenerators = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_generator);

    }

    @Override
    public void run() {
        listOfGenerators.add(new CashGenerator(true,0.5, "Get Donations", 0.1, "getDonationsIcon.png"));
    }
}
