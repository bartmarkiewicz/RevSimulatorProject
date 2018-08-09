package com.revolutionsimulator.bartek.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
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
import java.util.ResourceBundle;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Runnable  {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<CashGenerator> generatorList = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(ArrayList<CashGenerator> generatorList, Context context) {
        this.generatorList = generatorList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //don't change this
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cash_generator_listitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        //after struggling with having good performance
        /* JUST MAKE THIS STUFF WORK instead of trying using multi-threading
        do all the cash generation in this method, onclicks etc

        do it in multiple simple steps -

        first of all -
        onclick method on icon
        1. onClick
        2. the onclick method makes the progressbar move
        3. then when reaches 100 make it reset
        4. wmake it do something on finish
        5. only then implement cash and gaining of cash


        Other stuff -

        get information about speed and stuff from each cashgenerator
        use the cash generator class only for information ---- NO PROCESSING THERE OR FUNCTONS
        aside from get and set methods if needed (probably will be needed)

        Only after other stuff is done proceed to work on the upgrade system (buy button)
        Most/all processing should be done in this class
         */

        Log.d(TAG, "onBindViewHolder: called.");

        holder.generatorBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + generatorList.get(position));
                Toast.makeText(context, generatorList.get(position).getGeneratorName().toString() + " clicked", Toast.LENGTH_LONG).show();
            }
        });
        holder.generatorBuyButton.setText("Buy\nx1");
        holder.generatorProgressBar.setProgress(50);
        holder.generatorName.setText(generatorList.get(position).getGeneratorName().toString());
        holder.generatorUpgradeLevel.setText(Integer.toString(generatorList.get(position).getGeneratorLevel()));
        String generatorIconString = generatorList.get(position).getGeneratorIcon();
        holder.generatorIcon.setImageDrawable(context.getResources().getDrawable(context.getResources().getIdentifier(generatorIconString, "drawable", context.getPackageName())));
        holder.generatorIcon.setOnClickListener(new iconClick());


    }

    @Override
    public int getItemCount() {
        return generatorList.size();
    }

    @Override
    public void run() {

    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView generatorIcon;
        TextView generatorName;
        TextView generatorUpgradeLevel;
        TextView progressBarLabel;
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
            progressBarLabel = itemView.findViewById(R.id.progressBarLabel);
            parentLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}

