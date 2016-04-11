package com.odintsov.game;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

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
<<<<<<< HEAD
        missedText.setText("0");
=======
        dropText = (TextView) findViewById(R.id.dropText);
        missedText.setText("10");
        dropText.setText("0");
>>>>>>> 85d6cad1cb4d58bfed13952384ad7b97695a73b6

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
        missedCount =  missedCount - 1;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                missedText.setText("" + missedCount);
            }
        });
    }

<<<<<<< HEAD
     //   Toast.makeText(MainActivity.this, "100", Toast.LENGTH_SHORT).show();
      //  missedCount =  missedCount - 1;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                missedText.setText("9");
            }
        });

      //  missedText = (TextView) findViewById(R.id.missedText);
      //  missedText.setTextColor(Color.parseColor("#000000"));
=======
    @Override
    public void hit() {
        hitCount = hitCount + 1;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dropText.setText("" + hitCount);
            }
        });
>>>>>>> 85d6cad1cb4d58bfed13952384ad7b97695a73b6
    }

}
