package com.revolutionsimulator.bartek.game;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.revolutionsimulator.bartek.game.shooterGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new shooterGame(), config);
		Intent testIntent = new Intent(this, cashGeneratorActivity.class);
		startActivity(testIntent);

	}
}
