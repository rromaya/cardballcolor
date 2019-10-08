package com.cardsball.colors;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.Iterator;
import java.util.List;

/**
 * Created by daviduch on 4/01/2019.
 */
class CentralBall {
    private float ballRadius; // Ball's radius
    float ballX;  // Ball's center (x,y)
    float ballY;
    float ballSpeedX;  // Ball's speed (x,y)
    float ballSpeedY;
    private RectF ballBounds;
    private Paint paint;
    private float originalRadius;

    CentralBall(float x, float y, float radius, float speed, float dx, float dy) {
        this.ballX = x;
        this.ballY = y;
        this.ballRadius = originalRadius = radius;

        if (dx ==0 && dy == 0) {
            ballSpeedX = 0;
            ballSpeedY = 0;
        }
        else {
            ballSpeedX = (float) (speed * dx / Math.sqrt(dx * dx + dy * dy));
            ballSpeedY = (float) (speed * dy / Math.sqrt(dx * dx + dy * dy));
        }

        ballBounds = new RectF();
        paint = new Paint();
        paint.setColor(Color.WHITE);
    }

    public void collisionDetection(BackCalculating back, ScrBoard score){
        List<MoveBall> moving_balls = back.mBalls;

        for (Iterator<MoveBall> iter = moving_balls.listIterator(); iter.hasNext(); ) {
            MoveBall ball = iter.next();
            if (isCollided(ball)) {
                int cbColor = paint.getColor();
                int mbColor = ball.paint.getColor();

                // Pattern Mode
                if (back.isPattern) {
//                    if (cbColor == Color.WHITE) {
//                        paint.setColor(mbColor);
//                        this.ballRadius += 5;
//                        score.addStrike();
//                    }
//                    else
                    if (mbColor == back.patternBoard.peekPattern().paint.getColor()) {
                        this.ballRadius += 5;
                        paint.setColor(mbColor);
                        score.addStrike();
                        back.patternBoard.addPattern();
                    }
                    else {
                        paint.setColor(Color.WHITE);
                        this.ballRadius = originalRadius;
                        score.addScore();
                    }
                }else {

                    // unPattern Mode
                    if (cbColor == Color.WHITE) {
                        paint.setColor(mbColor);
                        this.ballRadius += 5;
                        score.addStrike();
                    }
                    else if (cbColor == mbColor) {
                        this.ballRadius += 5;
                        score.addStrike();
                    }
                    else {
                        paint.setColor(Color.WHITE);
                        this.ballRadius = originalRadius;
                        score.addScore();
                    }
                }

                iter.remove();
                Log.d("Hackathon", "Remove ball!");
            }
        }
    }

    private boolean isCollided(MoveBall ball){
        double distance = Math.sqrt( Math.pow(this.ballX - ball.x, 2) + Math.pow(this.ballY - ball.y, 2) );

        if (distance < this.ballRadius + ball.radius){
            return true;
        }

        return false;
    }

    void moveWithCollisionDetection(BackCalculating back) {
        // Get new (x,y) position
        ballX += ballSpeedX;
        ballY += ballSpeedY;
    }

    void draw(Canvas canvas, DisplayArea disp) {
        ballBounds.set(disp.xMax / 2 - ballRadius, disp.yMax / 2 - ballRadius, disp.xMax / 2 + ballRadius, disp.yMax / 2 + ballRadius);
        canvas.drawOval(ballBounds, paint);
    }

    void setSpeed(float speed, float dx, float dy) {
        if (dx ==0 && dy == 0) {
            ballSpeedX = 0;
            ballSpeedY = 0;
        }
        else {
            ballSpeedX = (float) (speed * dx / Math.sqrt(dx * dx + dy * dy));
            ballSpeedY = (float) (speed * dy / Math.sqrt(dx * dx + dy * dy));
        }
    }
}
