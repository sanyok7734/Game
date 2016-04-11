package com.odintsov.game;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FinishActivity extends Activity {

    private TextView time;
    private TextView timeText;
    private TextView score;
    private TextView scoreText;

    private ImageView reset;
    private ImageView exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_finish);

        Intent intent = getIntent();

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/PFArmonia-Reg.ttf");

        time = (TextView) findViewById(R.id.time);
        timeText = (TextView) findViewById(R.id.timeText);
        score = (TextView) findViewById(R.id.score);
        scoreText = (TextView) findViewById(R.id.scoreText);

        time.setTypeface(custom_font);
        timeText.setTypeface(custom_font);
        score.setTypeface(custom_font);
        scoreText.setTypeface(custom_font);

        timeText.setText(intent.getStringExtra("time"));
        score.setText("" + intent.getIntExtra("score", 0));

        reset = (ImageView) findViewById(R.id.reset);
        exit = (ImageView) findViewById(R.id.exit);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {

    }
}
