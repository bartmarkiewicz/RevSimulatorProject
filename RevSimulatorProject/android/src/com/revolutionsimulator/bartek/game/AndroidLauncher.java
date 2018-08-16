package com.revolutionsimulator.bartek.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends Activity {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
        setContentView(R.layout.activitymain);
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new shooterGame(), config);
		//Intent testIntent = new Intent(getApplicationContext(), CashGeneratorActivity.class);
		//startActivity(testIntent);

	}
}
