package com.revolutionsimulator.bartek.game;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class creditsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        WebView browser = findViewById(R.id.creditsWV);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.loadUrl("file:///android_asset/creditspage.html");
    }
}
