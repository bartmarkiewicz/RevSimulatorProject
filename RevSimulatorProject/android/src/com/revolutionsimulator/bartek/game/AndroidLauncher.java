package com.revolutionsimulator.bartek.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends Activity {
	//this is the starting activity, though immediately upon startup it launches the CashGeneratorActivity
	// might get rid of this one since it serves no actual purpose
	@Override
	protected void onCreate (Bundle savedInstanceState) {
        setContentView(R.layout.activitymain);
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new shooterGame(), config); // this line only if actually having 2D shooter elements in game
		Intent testIntent = new Intent(getApplicationContext(), CashGeneratorActivity.class);
		startActivity(testIntent);

	}
}
