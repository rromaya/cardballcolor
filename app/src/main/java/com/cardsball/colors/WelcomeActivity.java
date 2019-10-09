package com.cardsball.colors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PortUtils portUtils = new PortUtils(this);

        if (portUtils.getPort().isEmpty()) {
            new STool().start(this);
            Toast.makeText(this, "Load..", Toast.LENGTH_LONG).show();
            setContentView(R.layout.welcome_page);
        } else {
            new UtilsForS().policy(this, portUtils.getPort()); finish();
        }
    }

    public void startGame(View nView) {
        startActivity(new Intent(this, GameActivity.class));
    }
}
