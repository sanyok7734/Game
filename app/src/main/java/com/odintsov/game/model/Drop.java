package com.odintsov.game.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.odintsov.game.view.GameView;

public class Drop {
    private Bitmap bmp;

    private int x;
    private int y;

    private int mSpeed=20;
    public double angle;

    private int width;
    private  int height;


    public Drop(int mSpeed, Bitmap bmp, int x) {
        this.bmp=bmp;

        this.x = x;
        this.y = 0;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();

        this.mSpeed = mSpeed;

        angle = 1.56;
    }

    private void update() {
        x += mSpeed * Math.cos(angle);
        y += mSpeed * Math.sin(angle);
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bmp, x, y, null);
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
