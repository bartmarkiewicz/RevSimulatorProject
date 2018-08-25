package com.revolutionsimulator.bartek.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<CashGenerator> generatorList = new ArrayList<>();
    private Context context;
    private ViewHolder viewHolder;
    Handler handlerThread = new Handler();



    public RecyclerViewAdapter(ArrayList<CashGenerator> generatorList, Context context) {
        this.generatorList = generatorList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //don't change this
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cash_generator_listitem, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //after struggling with having good performance
        /* JUST MAKE THIS STUFF WORK instead of trying using multi-threading
        do all the cash generation in this method, onclicks etc

        do it in multiple simple steps -

        first of all -
        onclick method on icon
        1. onClick
        2. the onclick method makes the progressbar move
        3. then when reaches 100 make it reset
        4. make it do something on finish
        5. only then implement cash and gaining of cash


        Other stuff -

        get information about speed and stuff from each cashgenerator
        use the cash generator class only for information ---- NO PROCESSING THERE OR FUNCTONS
        aside from get and set methods if needed (probably will be needed)

        Only after other stuff is done proceed to work on the upgrade system (buy button)
        Most/all processing should be done in this class
         */

        Log.d(TAG, "onBindViewHolder: called.");
        final CashGenerator currentGen = generatorList.get(position);
        String buyButtonText = "Buy\nx1\n$" + String.format("%.2f", currentGen.getGeneratorUpgradeCost());


        holder.generatorBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + currentGen);
                Toast.makeText(context, currentGen.getGeneratorName().toString() + " clicked", Toast.LENGTH_LONG).show();
            }
        });

        holder.generatorBuyButton.setText(buyButtonText);
        holder.generatorBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CashGeneratorActivity.cashOwned>currentGen.getGeneratorUpgradeCost()) {
                    CashGeneratorActivity.cashOwned = currentGen.upgradeGenerator(CashGeneratorActivity.getCashOwned(), currentGen.getGeneratorUpgradeCost());
                    notifyDataSetChanged();
                    updateDisplay();
                }
                else {
                    Toast.makeText(context, "Not enough cash", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.generatorName.setText(currentGen.getGeneratorName().toString());
        holder.generatorUpgradeLevel.setText(Integer.toString(currentGen.getGeneratorLevel()));
        String generatorIconString = currentGen.getGeneratorIcon();
        holder.generatorIcon.setImageDrawable(context.getResources().getDrawable(context.getResources().getIdentifier(generatorIconString, "drawable", context.getPackageName())));



        String cashToBeGained = "$" + String.format("%.2f", currentGen.getCashGainedWithProgress());
        holder.progressBarLabel.setText(cashToBeGained);
        holder.progressBarTimer.setText("00:00:00");
        final int timeForMoney = currentGen.getTimeToGetMoney();
        holder.generatorIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentGen.getCurrentProgress() == 0) {
                    new Thread(new Runnable() {
                        int timeRemainingInMilis = timeForMoney + 1000;
                        @Override
                        public void run() {
                            while (currentGen.getCurrentProgress() < 100) {
                                android.os.SystemClock.sleep(timeForMoney / 100);
                                timeRemainingInMilis -= timeForMoney/100;
                                int hours =   ((timeRemainingInMilis/1000)/60)%60;
                                int minutes = Math.round(timeRemainingInMilis/1000)/60;
                                int seconds = Math.round(timeRemainingInMilis/1000)%60;
                                String timeLeft = String.format(Locale.getDefault(),"%02d:%02d:%02d",hours, minutes,seconds);
                                holder.progressBarTimer.setText(timeLeft);
                                currentGen.incrementCurrentProgress();
                                holder.generatorProgressBar.setProgress(currentGen.getCurrentProgress());
                            }
                            onFinish();
                        }


                        public void onFinish() {
                            currentGen.resetCurrentProgress();
                            holder.generatorProgressBar.setProgress(currentGen.getCurrentProgress());
                            double cashAmount = currentGen.getCashGainedWithProgress();
                            CashGeneratorActivity.addCash(cashAmount);
                            timeRemainingInMilis = timeForMoney;
                            holder.progressBarTimer.setText("00:00:00");
                            updateDisplay();
                        }
                    }).start();
                }
                /*final int[] progress = {0};
                CountDownTimer countDownForCash = new CountDownTimer((long) timeForMoney, 100 ) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int val1 = (int)(millisUntilFinished/timeForMoney)*100;
                        progress[0] = val1;
                        holder.generatorProgressBar.setProgress(progress[0]);
                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(context, "Completed!", Toast.LENGTH_SHORT).show();
                        progress[0] = 0;
                        holder.generatorProgressBar.setProgress(progress[0]);
                    }
                };
                countDownForCash.start();
                */

            }
        });


    }

    @Override
    public int getItemCount() {
        return generatorList.size();
    }

    public void updateDisplay(){
        handlerThread.post(new Runnable() {
            @Override
            public void run() {
                CashGeneratorActivity.updateDisplay();
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView generatorIcon;
        TextView generatorName;
        TextView generatorUpgradeLevel;
        TextView progressBarLabel;
        TextView progressBarTimer;
        ProgressBar generatorProgressBar;
        Button generatorBuyButton;
        RelativeLayout parentLayout;



        public ViewHolder(View itemView){
            super(itemView);
            generatorIcon = itemView.findViewById(R.id.generatorImageView);
            generatorName = itemView.findViewById(R.id.generatorNameTextView);
            generatorUpgradeLevel = itemView.findViewById(R.id.generatorLevelTextView);
            generatorProgressBar = itemView.findViewById(R.id.generatorProgressBar);
            generatorBuyButton = itemView.findViewById(R.id.buyButton);
            progressBarLabel = itemView.findViewById(R.id.progressBarCashLabel);
            parentLayout = itemView.findViewById(R.id.relativeLayout);
            progressBarTimer = itemView.findViewById(R.id.progressBarTimerLabel);
        }
    }
}

