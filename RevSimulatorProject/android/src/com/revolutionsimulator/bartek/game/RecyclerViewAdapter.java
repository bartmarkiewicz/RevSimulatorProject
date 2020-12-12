package com.revolutionsimulator.bartek.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
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
	// this is the recyclerview adapter for the cash generator recyclerview
	
    private static final String TAG = "RecyclerViewAdapter"; //debugging tag - not used
	
    private ArrayList<CashGenerator> generatorList = new ArrayList<>(); // the list of cashgenerators used to create each recyclerview item
    private Context context; 
    private ViewHolder viewHolder; //the object which holds the views
    Handler handlerThread = new Handler(); //the handler object which updates the UI from a non-UI thread



    public RecyclerViewAdapter(ArrayList<CashGenerator> generatorList, Context context) {
		//constructor
        this.generatorList = generatorList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //don't change this
		//this is what creates the viewHolder and inflates the layout of the recyclerview item
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
        // perhaps try having them all invisible until the generator is bought
        // if is not bought then have all views invisible except a new view element - big buy button which makes all the views visible except itself invisible


        Log.d(TAG, "onBindViewHolder: called."); // - debugging thing
        final CashGenerator currentGen = generatorList.get(position); // this allows me to state 'currentGen' instead of the long winded generatorList.get(position);

        if (currentGen.isBought()) {
            holder.generatorUpgradeButton.setVisibility(View.VISIBLE);
            holder.buyGeneratorButton.setVisibility(View.INVISIBLE);
            holder.generatorName.setVisibility(View.VISIBLE);
            holder.generatorProgressBar.setVisibility(View.VISIBLE);
            holder.generatorUpgradeLevel.setVisibility(View.VISIBLE);
            holder.progressBarLabel.setVisibility(View.VISIBLE);
            holder.progressBarTimer.setVisibility(View.VISIBLE);
            holder.parentLayout.setVisibility(View.VISIBLE);
        } else {
            String buyGenBtText = "" + currentGen.getGeneratorName().toString() + "\n Price: $" + String.format("%.2f", currentGen.getGeneratorPurchasePrice());
            holder.buyGeneratorButton.setText(buyGenBtText);

            holder.buyGeneratorButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CashGeneratorActivity.getCashOwned() > currentGen.getGeneratorPurchasePrice()) {
                        CashGeneratorActivity.cashOwned = CashGeneratorActivity.getCashOwned() - currentGen.getGeneratorPurchasePrice();
                        updateCashDisplay();
                        holder.generatorUpgradeButton.setVisibility(View.VISIBLE);
                        holder.buyGeneratorButton.setVisibility(View.INVISIBLE);
                        holder.generatorName.setVisibility(View.VISIBLE);
                        holder.generatorProgressBar.setVisibility(View.VISIBLE);
                        holder.generatorUpgradeLevel.setVisibility(View.VISIBLE);
                        holder.progressBarLabel.setVisibility(View.VISIBLE);
                        holder.progressBarTimer.setVisibility(View.VISIBLE);
                        holder.parentLayout.setVisibility(View.VISIBLE);
                        currentGen.buyGenerator();
                    } else {
                        Toast.makeText(context, "Not enough cash.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


        // buy button stuff below
        String buyButtonText = "Buy\nx1\n$" + String.format("%.2f", currentGen.getGeneratorUpgradeCost());
        holder.generatorUpgradeButton.setOnClickListener(new View.OnClickListener() {
            //buy button onclick
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + currentGen); // debugging line
                Toast.makeText(context, currentGen.getGeneratorName().toString() + " clicked", Toast.LENGTH_LONG).show(); //debugging line
                if (CashGeneratorActivity.cashOwned > currentGen.getGeneratorUpgradeCost()) {
                    CashGeneratorActivity.cashOwned = currentGen.upgradeGenerator(CashGeneratorActivity.getCashOwned(), currentGen.getGeneratorUpgradeCost());
                    notifyDataSetChanged();
                    updateCashDisplay();
                } else {
                    Toast.makeText(context, "Not enough cash.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.generatorUpgradeButton.setText(buyButtonText);

        holder.generatorName.setText(currentGen.getGeneratorName().toString());
        holder.generatorUpgradeLevel.setText(Integer.toString(currentGen.getGeneratorLevel()));

        String generatorIconString = currentGen.getGeneratorIcon();
        // below the icon is set using its generatorIconString
        holder.generatorIcon.setImageDrawable(context.getResources().getDrawable(context.getResources().getIdentifier(generatorIconString, "drawable", context.getPackageName())));

        String cashToBeGained = "$" + String.format("%.2f", currentGen.getCashGainedWithProgress());

        holder.progressBarLabel.setText(cashToBeGained);
        holder.progressBarTimer.setText("00:00:00");
        final int timeForMoney = currentGen.getTimeToGetMoney();

        int seconds = Math.round(timeForMoney / 1000);
        int minutes = seconds / 60;
        int hours = minutes / 60;
        seconds = seconds % 60;
        minutes = minutes % 60;
        hours = hours % 60;
        final String timeLeft = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        updateTimeLeftDisplay(holder, timeLeft);

        holder.progressBarTimer.setText(timeLeft);
        holder.generatorIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onclick method for clicking the gen icon
                // - commented out, gonna test it without new Threads yo

                if (currentGen.isBought()) {
                    if (currentGen.getCurrentProgress() == 0) {
                        CountDownTimer myTimer = new CountDownTimer(timeForMoney+999, 1000) {
                            @Override
                            public void onTick(long l) {
                                int seconds = Math.round(l / 1000);
                                int minutes = seconds / 60;
                                int hours = minutes / 60;
                                seconds = seconds % 60;
                                minutes = minutes % 60;
                                hours = hours % 60;
                                String timeLeft = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
                                updateTimeLeftDisplay(holder, timeLeft);
                            }

                            @Override
                            public void onFinish() {
                            }


                        };
                        myTimer.start();
                        new Thread(new Runnable() {
                            // the progress bar progressing is done on a new thread
                            int timeRemainingInMilis = timeForMoney+1000; // the time remaining till it completes, 1000 is added to make it sync for some reason

                            @Override
                            public void run() {
                                // this run method is the logic which makes the cash generator progress bar work
                                // countdown timer -

                                // progress bar progressing -
                                while (currentGen.getCurrentProgress() < 100) {
                                    int onePixelTick = timeForMoney/100;
                                    //android.os.SystemClock.sleep(timeForMoney / 100);
                                    timeRemainingInMilis -= timeForMoney / 100;
                                    currentGen.incrementCurrentProgress();
                                    updateProgressBarDisplay(holder, currentGen.getCurrentProgress());
                                    android.os.SystemClock.sleep(onePixelTick);
                                }
                                onFinished();
                            }

                            public void onFinished() {
                                // this procedure is called when the progress bar reaches 100%
                                // it resets the generator and grants cash to the player
                                currentGen.resetCurrentProgress();
                                updateProgressBarDisplay(holder, currentGen.getCurrentProgress());
                                double cashAmount = currentGen.getCashGainedWithProgress();
                                CashGeneratorActivity.addCash(cashAmount);

                                int seconds = Math.round(currentGen.getTimeToGetMoney() / 1000);
                                int minutes = seconds / 60;
                                int hours = minutes / 60;
                                seconds = seconds % 60;
                                minutes = minutes % 60;
                                hours = hours % 60;
                                final String timeLeft = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
                                updateTimeLeftDisplay(holder, timeLeft);
                                updateCashDisplay();
                            }
                        }).start();

                    }
                }
            }
        });
        }


        @Override
        public int getItemCount (){
            // this method acquires the number of items in the recyclerview/list
            return generatorList.size();
        }

        public void updateTimeLeftDisplay ( final ViewHolder holder, final String timeLeft){
            handlerThread.post(new Runnable() {
                @Override
                public void run() {
                    holder.progressBarTimer.setText(timeLeft);
                }
            });
        }

        public void updateProgressBarDisplay (final ViewHolder holder, final int currentProgress){
            handlerThread.post(new Runnable() {
                @Override
                public void run() {
                    holder.generatorProgressBar.setProgress(currentProgress);
                }
            });
        }

        public void updateCashDisplay () {
            //this is the method which updates the cash display
            handlerThread.post(new Runnable() {
                // don't remember why this is done on a new thread
                // but probably necessary idk lol
                @Override
                public void run() {
                    CashGeneratorActivity.updateDisplay();
                }
            });
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            // this is the class which is meant to hold all the views, thanks to having this class  findViewById (which is performance expensive) is not needed
            //to be called every time
            ImageView generatorIcon;
            TextView generatorName;
            TextView generatorUpgradeLevel;
            TextView progressBarLabel;
            TextView progressBarTimer;
            ProgressBar generatorProgressBar;
            Button generatorUpgradeButton;
            Button buyGeneratorButton;
            RelativeLayout parentLayout;


            public ViewHolder(View itemView) {
                super(itemView);
                //this is the constructor which assigns all the views of the recyclerview item to variables
                generatorIcon = itemView.findViewById(R.id.generatorImageView);
                generatorName = itemView.findViewById(R.id.generatorNameTextView);
                generatorUpgradeLevel = itemView.findViewById(R.id.generatorLevelTextView);
                generatorProgressBar = itemView.findViewById(R.id.generatorProgressBar);
                generatorUpgradeButton = itemView.findViewById(R.id.buyButton);
                progressBarLabel = itemView.findViewById(R.id.progressBarCashLabel);
                parentLayout = itemView.findViewById(R.id.relativeLayout);
                progressBarTimer = itemView.findViewById(R.id.progressBarTimerLabel);
                buyGeneratorButton = itemView.findViewById(R.id.buyGeneratorBt);
                generatorProgressBar.setScaleY(4f);

            }
        }
    }

