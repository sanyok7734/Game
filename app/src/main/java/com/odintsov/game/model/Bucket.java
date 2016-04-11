package com.odintsov.game.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.odintsov.game.view.GameView;

public class Bucket {

    private int screenWidth;
    private int screenHeight;

    private Bitmap bmp;

    private int x;
    private int y;

    private int width;
    private  int height;


    public Bucket(int screenWidth, int screenHeight, Bitmap bmp) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.bmp = bmp;

        this.width = bmp.getWidth();
        this.height = bmp.getHeight();

        this.x = (screenWidth / 2) - (width / 2);
        this.y = (screenHeight - height) ;

    }

    public void onDraw(Canvas c) {
        c.drawBitmap(bmp, x, y, null);
    }


    public void setX(int x) {



        if ((this.x + x) <= 0) {
            this.x = 0;
        } else if ((this.x + x) >= screenWidth - width) {
            this.x = screenWidth - width;
        } else {
            this.x = this.x + x;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
