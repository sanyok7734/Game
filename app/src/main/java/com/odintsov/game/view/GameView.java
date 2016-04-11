package com.odintsov.game.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.odintsov.game.R;
import com.odintsov.game.listener.GameListener;
import com.odintsov.game.model.Bucket;
import com.odintsov.game.model.Drop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {


    private int screenWidth;
    private int screenHeight;
    private GameListener gameListener;

    private boolean createBackground = false;


    private List<Drop> ball = new ArrayList<Drop>();
    private Thread thread = new Thread(this);


    private Bucket bucket;
    private Bucket bucket1;
    Bitmap buckets;
    Bitmap buckets1;
    private GameThread mThread;

    public int shotX;
    public int shotY;

    private boolean running = false;

    public class GameThread extends Thread {
        private GameView view;

        public GameThread(GameView view) {
            this.view = view;
        }

        public void setRunning(boolean run) {
            running = run;
        }

        public void run() {
            while (running) {
                Canvas canvas = null;
                try {
                    canvas = view.getHolder().lockCanvas();
                    synchronized (view.getHolder()) {
                        onDraws(canvas);
                        testCollision();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        view.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    public GameView(Context context, int screenWidth, int screenHeight, GameListener gameListener) {
        super(context);

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.gameListener = gameListener;

        mThread = new GameThread(this);

        buckets = BitmapFactory.decodeResource(getResources(), R.drawable.bucket);
        buckets1 = BitmapFactory.decodeResource(getResources(), R.drawable.bucket1);
        bucket = new Bucket(screenWidth, screenHeight, buckets);
        bucket1 = new Bucket(screenWidth, screenHeight, buckets1);

        thread.start();

        getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                mThread.setRunning(false);
                while (retry) {
                    try {
                        mThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            public void surfaceCreated(SurfaceHolder holder) {
                mThread.setRunning(true);
                mThread.start();
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });
    }


    protected void onDraws(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#7bc5cd"));
        onDrawBackground(canvas);
        bucket.onDraw(canvas);

        Iterator<Drop> j = ball.iterator();
        while (j.hasNext()) {
            Drop b = j.next();
            b.onDraw(canvas);
        }

        bucket1.onDraw(canvas);
    }

    private void onDrawBackground(Canvas canvas) {
        Rect dest = new Rect(0, ((int) (screenHeight / 1.3)), screenWidth, screenHeight);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.fon);
        canvas.drawBitmap(background, null, dest, paint);
    }

    private void onDrawHeart(Canvas canvas) {
        Rect dest = new Rect(10, 10, 40, 40);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        canvas.drawBitmap(background, null, dest, paint);
    }

    public Drop createSprite(int resouce) {
        Random rnd = new Random();
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Drop(screenWidth, screenHeight, bmp, rnd.nextInt(screenWidth - bmp.getWidth()));
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        shotX = (int) e.getX();
        shotY = (int) e.getY();

        int width = this.getWidth();
        width = width / 2;
        if (shotX > width) {
            bucket.setX(screenWidth / 20);
            bucket1.setX(screenWidth / 20);
        } else {
            bucket.setX((screenWidth / 20) * -1);
            bucket1.setX((screenWidth / 20) * -1);
        }

        return true;
    }


    @Override
    public void run() {
        while (true) {
            Random rnd = new Random();
            try {
                Thread.sleep(rnd.nextInt(2000) + 300);
                ball.add(createSprite(R.drawable.drop));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void testCollision() {
        Iterator<Drop> b = ball.iterator();
        MediaPlayer player = MediaPlayer.create(getContext(), R.raw.drop_sound);
        while (b.hasNext()) {
            Drop balls = b.next();
            int xBall = balls.getX() + (balls.getWidth() / 2);
            int xBucket = bucket1.getX();
            int wBucket = bucket1.getX() + bucket1.getWidth();

            if (xBucket <= xBall && xBall <= wBucket && (Math.abs(balls.getY() - bucket1.getY()) <= (balls.getHeight() + (bucket1.getHeight() / 1.5)) / 2f)) {
                gameListener.hit();
                player.seekTo(1000);
                player.start();
                b.remove();
                player.reset();
                player.release();
            } else if (balls.getY() > screenHeight) {
                Log.d("GAME_OLOL", "---------------------");
                gameListener.missed();

                b.remove();
            }
        }

    }
}

