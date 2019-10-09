package com.cardsball.colors;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;


public class GameActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PBallView pBallView = new PBallView(this);
        setContentView(pBallView);
        pBallView.setBackgroundColor(Color.parseColor("#FF1A1414"));
    }
}
