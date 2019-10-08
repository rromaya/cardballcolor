package com.cardsball.colors;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by chenghuh on 6/27/2015.
 */
public class ScrBoard {
    private int strike = 0;
    private int score = 0;
    private int life;
    private Paint p;

    public ScrBoard(int life) {
        p = new Paint();
        this.life = life;
    }

    public void reset() {
        strike = 0;
        score = 0;
    }

    public void addStrike() {
        strike++;
    }

    public void addScore() {
        score += (strike / 2) * strike * 100;
        strike = 0;
        life--;

    }

    public void drawScoreBoard(Canvas canvas) {
        String message = "Strike: " + strike + " Score: " + score + " Life: " + life;
        if (life <= 0) message = "Game Over";
        p.setTextSize(30);
        float width = p.measureText(message);

        float sX = 2, sY = 2, eX = 6 + width, eY = 35, lX = 1, lY = 1;

        p.setColor(Color.YELLOW);
        canvas.drawRect(sX, sY, eX, eY, p);
        p.setColor(Color.GRAY);
        canvas.drawRect(sX + lX, sY + lX, eX - lX, eY - lY, p);

        p.setColor(Color.RED);
        canvas.drawText(message, 4, eY - 4, p);

    }
}
