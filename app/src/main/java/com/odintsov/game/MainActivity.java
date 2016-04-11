package com.odintsov.game;

import android.app.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.odintsov.game.listener.GameListener;
import com.odintsov.game.view.GameView;

public class MainActivity extends Activity implements GameListener {

    private FrameLayout scene;
    private TextView missedText;
    private TextView dropText;

    private Chronometer chronometer;

    private int missedCount = 10;
    private int hitCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_game);
        scene = (FrameLayout) findViewById(R.id.scene);
        missedText = (TextView) findViewById(R.id.missedText);
        missedText.setText("10");
        dropText = ((TextView) findViewById(R.id.dropText));
        dropText.setText("0");

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;

        scene.addView(new GameView(this, screenWidth, screenHeight, this));

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.start();
    }


    @Override
    public void missed() {
        missedCount = missedCount - 1;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                missedText.setText("" + missedCount);
                if (missedCount == 0) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
                    intent.putExtra("time", chronometer.getText());
                    intent.putExtra("score", hitCount);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void hit() {
        hitCount = hitCount + 1;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dropText.setText("" + hitCount);
            }
        });
    }

}
