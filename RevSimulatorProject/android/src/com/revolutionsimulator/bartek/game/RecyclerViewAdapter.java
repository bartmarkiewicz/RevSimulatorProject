package com.revolutionsimulator.bartek.game;

import android.content.Context;
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

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> generatorNames = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(ArrayList<String> generatorIconName, Context context) {
        this.generatorNames = generatorIconName;
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
        Log.d(TAG, "onBindViewHolder: called.");

        holder.generatorBuyButton.setText("Buy\nx1");
        holder.generatorBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + generatorNames.get(position));
                Toast.makeText(context, generatorNames.get(position) + " clicked", Toast.LENGTH_LONG).show();
            }
        });
        holder.generatorProgressBar.setProgress(50);
    }

    @Override
    public int getItemCount() {
        return generatorNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView generatorIcon;
        TextView generatorName;
        TextView generatorUpgradeLevel;
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
            parentLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}

