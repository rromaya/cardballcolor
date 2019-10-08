package com.cardsball.colors;

/**
 * Created by daviduch on 4/01/2019.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

class BackCalculating {
    private int maximumX;
    private int maximumY;
    private Paint paint;
    List<MoveBall> mBalls;
    static int[] myGreatArr = {Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.LTGRAY};
    private final int BALL_NUM = 50;
    private ScrBoard scrBoard;
    PatternBoard patternBoard;
    static boolean isPattern = true;

    BackCalculating(int xMin, int minimumY, int xMax, int maximumY, int colorIn, int colorOut) {
        this.maximumX = xMax;
        this.maximumY = maximumY;
        paint = new Paint();
        paint.setColor(colorIn);
        // paint style and custom
        Paint paintingOut = new Paint();
        paintingOut.setColor(colorOut);
        mBalls = new LinkedList<MoveBall>();
        initMBalls(this.BALL_NUM);
        scrBoard = new ScrBoard(10);
        patternBoard = new PatternBoard(7);
    }

    private void pushBalls() {
        for (MoveBall mB : this.mBalls) {
            mB.moveWithCollisionDetection(this);
        }
    }

    void draw(Canvas canvas, DisplayArea disp, CentralBall cball) {
        // Draw the board
        canvas.drawRect(0, 0, disp.xMax, disp.yMax, paint);
        // JP: cancle collision
//        float dLeft = disp.maximumX / 2 - cball.ballX;
//        float dRight = disp.maximumX / 2 - (this.maximumX - cball.ballX);
//        float dTop = disp.maximumY / 2 - cball.ballY;
//        float dBottom = disp.maximumY / 2 - (this.maximumY - cball.ballY);
//        if (dLeft > 0) {
//            canvas.drawRect(0, 0, dLeft, disp.maximumY, paintingOut);
//        }
//        if (dRight > 0) {
//            canvas.drawRect(disp.maximumX - dRight, 0, disp.maximumX, disp.maximumY, paintingOut);
//        }
//        if (dTop > 0) {
//            canvas.drawRect(0, 0, disp.maximumX, dTop, paintingOut);
//        }
//        if (dBottom > 0) {
//            canvas.drawRect(0, disp.maximumY - dBottom, disp.maximumX, disp.maximumY, paintingOut);
//        }

        // Draw central ball
        cball.draw(canvas, disp);

        // Collision detection.
        cball.collisionDetection(this, scrBoard);

        // Update the position of the ball, including collision detection and reaction.
        cball.moveWithCollisionDetection(this);

        // Draw other balls.
        for (MoveBall ball : mBalls) {
            ball.updateLocation(disp, cball);
            ball.draw(canvas, disp, cball);
        }

        // Update ball positions
        pushBalls();

        if (isPattern) patternBoard.drawPatternBoard(canvas);
        scrBoard.drawScoreBoard(canvas);

        addOneBall(disp, cball);
    }

    private void initMBalls(int numOfBalls) {
        Random randomGenerator = new Random();
        for (int i = 0; i < numOfBalls; i++) {
            float x = randomGenerator.nextInt(this.maximumX) + randomGenerator.nextFloat();
            if (x > maximumX) x -= 1;
            float y = randomGenerator.nextInt(this.maximumY) + randomGenerator.nextFloat();
            if (y > maximumY) y -= 1;
            float radius = 20;
            int color = myGreatArr[randomGenerator.nextInt(myGreatArr.length)];
            float dx = randomGenerator.nextFloat() - (float)0.5;
            float dy = randomGenerator.nextFloat() - (float)0.5;
            MoveBall mB = new MoveBall(x, y, radius, color, 5, dx, dy);
            this.mBalls.add(mB);
        }
    }

    private void addOneBall(DisplayArea disp, CentralBall cBall) {
        if (this.mBalls.size() < this.BALL_NUM) {
            Random randomGenerator = new Random();
            float x = randomGenerator.nextInt(this.maximumX) + randomGenerator.nextFloat();
            if (x > maximumX) x -= 1;
            float y = randomGenerator.nextInt(this.maximumY) + randomGenerator.nextFloat();
            if (y > maximumY) y -= 1;

            // avoid putting new ball near central ball
            if (Math.abs(cBall.ballX - x) < (disp.xMax / 2)) x = cBall.ballX - (disp.xMax / 2);
            if (Math.abs(cBall.ballY - y) < (disp.yMax / 2)) y = cBall.ballY - (disp.yMax / 2);

            float radius = 20;
            int color = myGreatArr[randomGenerator.nextInt(myGreatArr.length)];
            float dx = randomGenerator.nextFloat() - (float)0.5;
            float dy = randomGenerator.nextFloat() - (float)0.5;
            MoveBall mB = new MoveBall(x, y, radius, color, 5, dx, dy);
            this.mBalls.add(mB);
        }
    }
}
