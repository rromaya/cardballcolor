package com.cardsball.colors;

/**
 * Created by daviduch on 4/01/2019.
 */
public class DisplayArea {
    int xMin = 0, xMax = 0, yMin = 0, yMax = 0;     // The paint style, custom used for drawing

    public DisplayArea(int xMin, int xMax, int yMin, int yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    public void setDisp(int xMax, int yMax) {
        this.xMax = xMax;
        this.yMax = yMax;
    }
}
