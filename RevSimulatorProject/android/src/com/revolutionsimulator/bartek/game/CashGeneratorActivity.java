package com.revolutionsimulator.bartek.game;

import android.app.Activity;
import android.os.CountDownTimer;
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
    private static final String TAG = "CashGeneratorActivity";

    TextView cashDisplay;

    private ArrayList<CashGenerator> listOfGenerators = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    RecyclerView generatorListRecyclerView;
    public double cashOwned = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_generator);
        initGeneratorRecyclerView();
        cashDisplay = findViewById(R.id.cashDisplay);
        getCashFromGenerators();

    }

    public void initGeneratorRecyclerView(){
        listOfGenerators.add(new CashGenerator(true,0.5, "Get Donations", 5000, "cash_gen_one_icon"));
        listOfGenerators.add(new CashGenerator(true,0.6, "Get Money", 10000, "cash_gen_one_icon"));
        generatorListRecyclerView = (RecyclerView) findViewById(R.id.generatorRecyclerView);
        adapter = new RecyclerViewAdapter(listOfGenerators, this);
        generatorListRecyclerView.setAdapter(adapter);
        generatorListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getCashFromGenerators(){
        Log.d(TAG, "getCashFromGenerators: ");
        for (int i = 0; i < generatorListRecyclerView.getChildCount(); i++) {
            final RecyclerViewAdapter.ViewHolder holder = (RecyclerViewAdapter.ViewHolder) generatorListRecyclerView.findViewHolderForAdapterPosition(i);
            if(Integer.parseInt(holder.generatorUpgradeLevel.getText().toString())>=1) {
                holder.generatorIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CountDownTimer timeToGetCash = new CountDownTimer(5000,1000) {
                            @Override
                            public void onTick(long l) {
                                int progress = (5000 / ((int) l))*10;
                                holder.progressBarLabel.setText(progress);
                                holder.generatorProgressBar.setProgress(progress);
                            }

                            @Override
                            public void onFinish() {
                                cashOwned += 0.50;
                                cashDisplay.setText(Double.toString(cashOwned));

                            }
                        };
                        timeToGetCash.start();
                    }
                });
                adapter.notifyItemChanged(i);

            }
            }
        }

}
