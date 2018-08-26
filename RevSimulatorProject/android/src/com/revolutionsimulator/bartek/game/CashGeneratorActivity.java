package com.revolutionsimulator.bartek.game;

import android.app.Activity;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CashGeneratorActivity extends Activity {
    public static TextView cashDisplay;

    private ArrayList<CashGenerator> listOfGenerators = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    RecyclerView generatorListRecyclerView;
    public static double cashOwned = 0;
    private LayoutInflater layoutInflater;
    private PopupWindow popupWindow;
    private ConstraintLayout constraintLayout;
    public void showPopupMenu(View v){
        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.popup_menu, null);
        popupWindow = new PopupWindow(container, getScreenHeight(), getScreenWidth(), true);
        popupWindow.showAtLocation(constraintLayout, Gravity.NO_GRAVITY, 0,0);
        /*container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                return true;
            }
        });*/



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_generator);
        initGeneratorRecyclerView();
        cashDisplay = findViewById(R.id.cashDisplay);
        constraintLayout = findViewById(R.id.conLayout);
        updateDisplay();
    }
    public static void updateDisplay(){
        // this updates the cash display
        cashDisplay.setText("Cash: $" + String.format("%.2f", cashOwned));
    }

    public void initGeneratorRecyclerView(){
        listOfGenerators.add(new CashGenerator(true,0.5,1, "Get Donations", 5000, "cash_gen_one_icon"));
        listOfGenerators.add(new CashGenerator(true,0.6, 2,"Get Money", 10000, "cash_gen_one_icon"));
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
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    /*@Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.revActionsButton:
                Toast.makeText(this, "Item1 clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.commissarsButton:
                Toast.makeText(this, "item2", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.upgradesButton:
                Toast.makeText(this, "item3", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.revolutionButton:
                Toast.makeText(this, "item4", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.optionsButton:
                Toast.makeText(this, "item5", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.creditsButton:
                Toast.makeText(this, "item5", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return true;
        }
    };*/
}
