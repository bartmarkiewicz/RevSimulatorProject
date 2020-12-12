package com.revolutionsimulator.bartek.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.revolutionsimulator.bartek.game.R.drawable.popupmenu_background;

public class CashGeneratorActivity extends Activity {
	// this is the activity which contains the cash generators


    private ArrayList<CashGenerator> listOfGenerators = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    RecyclerView generatorListRecyclerView;
    public static double cashOwned; // this is the variable for cash owned by the player
	public static TextView cashDisplay;
	
    private LayoutInflater layoutInflater;
    private PopupWindow popupWindow;
    private ConstraintLayout constraintLayout;

    //saving stuff -
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_generator);

        initGeneratorRecyclerView();
        cashDisplay = findViewById(R.id.cashDisplay);
        constraintLayout = findViewById(R.id.conLayout);
        loadAllProgress();
        updateDisplay();
    }
    @Override
    public void onResume() {
        // this runs when the user returns to the activity
        loadAllProgress();
        super.onResume();
        Log.d("onResume", "onResume: ");

    }

    @Override
    public void onStop() {
        //this runs when the activity is no longer visible
        saveAllProgress();
        super.onStop();
        Log.d("onStop", "onStop: ");
    }


    public void saveAllProgress(){
        prefs = this.getSharedPreferences(
                "com.revolutionsimulator.app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        for(int i =0; i<listOfGenerators.size()-1; i++){
            CashGenerator currentGen = listOfGenerators.get(i);
            int generatorLevel = currentGen.getGeneratorLevel();
            editor.putInt("generator"+i+"Level", generatorLevel);
            editor.putBoolean("generator"+i+"bought", currentGen.isBought());
        }

        String cashOwnedStr = Double.toString(cashOwned);
        editor.putString("cashOwned", cashOwnedStr);
        editor.commit();
        //todo
    }

    public void loadAllProgress(){
        prefs = this.getSharedPreferences(
                "com.revolutionsimulator.app", Context.MODE_PRIVATE);
        String cashOwnedStr = prefs.getString("cashOwned", "0");
        for(int i =0; i<listOfGenerators.size()-1; i++){
            CashGenerator currentGen = listOfGenerators.get(i);
            currentGen.setGeneratorLevel(prefs.getInt("generator"+i+"Level", 1));
            if (prefs.getBoolean("generator"+i+"bought", false) == true){
                currentGen.buyGenerator();
            }
        }

        cashOwned = Double.valueOf(cashOwnedStr);

        //todo
    }


    public void showCredits(View v){
        Intent intent =  new Intent(getApplicationContext(), creditsActivity.class);
        startActivity(intent);
    }

    public void showOptions(View v){
        Intent intent = new Intent(getApplicationContext(), optionsActivity.class);
        startActivity(intent);
    }
	
	public void showPopupMenu(View v){
		// this is the onclick method for the button which should display the popup menu showing the different options
        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.popup_menu, constraintLayout, false);
        popupWindow = new PopupWindow(container, getScreenWidth(), getScreenHeight()-150, true); // check different screen widths and heights here and google how to make it work
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(false);
        popupWindow.setTouchable(true);


        final View popupLayout = container.findViewById(R.id.popupLayout);
        Button revActBt = container.findViewById(R.id.revActionsBt);
        Button commBt = container.findViewById(R.id.commissarsBt);
        Button upgradesBt = container.findViewById(R.id.upgradesBt);
        Button  revolutionBt = container.findViewById(R.id.revolutionBt);
        Button optionsBt = container.findViewById(R.id.optionsBt);
        Button creditsBt = container.findViewById(R.id.creditsBt);
        Button closeBt = container.findViewById(R.id.closeWindowBt);
        ImageView speakerIV = container.findViewById(R.id.speakerIV);
        ImageView helpIV = container.findViewById(R.id.helpIV);


        revActBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
            }
        });

        commBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
            }
        });

        upgradesBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
            }
        });

        revolutionBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
            }
        });


        optionsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOptions(view);
            }
        });
        creditsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCredits(view);
            }
        });

        speakerIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
            }
        });

        helpIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOptions(view);
            }
        });

        closeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });


        /*popupLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int x = (int)event.getX();
                int y = (int)event.getY();
                int pixel = background.getPixel(x,y);
                int transparency = ((background.getPixel(x,y) & 0xff000000) >> 24);

                if (pixel == Color.TRANSPARENT || transparency == 0){
                    //doesn't work correctly on all sides (works only on the left side for some reason)
                    popupWindow.dismiss();
                    return true;
                }
                else {
                    return false;
                }
            }
        });*/

        popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0,-200);
    }


    public static void updateDisplay(){
        // this updates the cash display
        cashDisplay.setText("Cash: $" + String.format("%.2f", cashOwned));
    }

    public void initGeneratorRecyclerView(){
		/* 
		   initGeneratorRecyclerView()
		// this procedure initialises the generator recycler view 
		// by adding the CashGenerators to the list and creating
		// the adapter and recyclerview objects and then setting the adapter to the recyclerview
        */
		listOfGenerators.add(new CashGenerator(true,0.5,1, "Public fund raising", 5000, "cash_gen_one_icon",0));
        listOfGenerators.add(new CashGenerator(false,2.5, 3,"Propaganda", 10000, "cash_gen_propaganda", 10));
        listOfGenerators.add(new CashGenerator(false,10.0, 15.0,"Sell party membership cards", 30000, "cash_gen_partycard",30));
        listOfGenerators.add(new CashGenerator(false,40.0, 50,"Inner party fund raising", 60000, "cash_gen_innerparty",60));
        listOfGenerators.add(new CashGenerator(false,150.0, 200,"Pamphlet Distribution", 120000, "cash_gen_pamphlet",200));
        listOfGenerators.add(new CashGenerator(false,500.0, 400,"Party Newspaper", 500000, "cash_gen_newspaper",500));
        listOfGenerators.add(new CashGenerator(false,2000.0, 1500,"Funding from abroad", 600000, "cash_gen_foreignfunding",1000));

        //the second addition to the list is placeholder
		// the real way of addition to the list will be done through an onclick method - unless implementation will hinge
		// on making all the elements visible rather than 
		generatorListRecyclerView = (RecyclerView) findViewById(R.id.generatorRecyclerView);
        adapter = new RecyclerViewAdapter(listOfGenerators, this);
        generatorListRecyclerView.setAdapter(adapter);
        generatorListRecyclerView.setLayoutManager(new LinearLayoutManager(this));//this sets how the items are layed out
    }

    public static double getCashOwned() {
		//this returns the amount of cash currently owned by the player
        return cashOwned;
    }

    public static void addCash(double amount){
		//this method adds the provided amount of cash to the cash owned
        cashOwned += amount;
    }
    public static int getScreenWidth() {
		//not sure if this works but this gets the device's screen width
        return Resources.getSystem().getDisplayMetrics().widthPixels; // this gets the size in actual pixels rather than DP pixels
    }

    public static int getScreenHeight() {
		//not sure if this works but this gets the device's screen height
        return Resources.getSystem().getDisplayMetrics().heightPixels; // this gets the size in actual pixels rather than DP pixels
    }

    public void getCash(View view){
        cashOwned += 50;
        updateDisplay();
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
