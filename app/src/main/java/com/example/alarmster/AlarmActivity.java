package com.example.alarmster;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class AlarmActivity extends Activity {

    LottieAnimationView lottieAnimationView;

    DBHelper db;

    MediaPlayer player = new MediaPlayer();

    String next_week = null;

    String time = null;
    StringBuffer day = new StringBuffer();
    String week = null;
    String message = null;
    String song = null;
    String image = null;
    String state = null;
    String color = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1){
            setShowWhenLocked(true);
            setTurnScreenOn(true);
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            keyguardManager.requestDismissKeyguard(this, null);
        }
        else{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        setContentView(R.layout.activity_alarm);

        db = new DBHelper(this);

        if (db.getSortedData().size() > 0){
            for (int i=0; i<db.getSortedData().size(); i++){
                if (db.getSortedData().get(i).get(6).equals("on")){
                    time = db.getSortedData().get(i).get(0);
                    day.append(db.getSortedData().get(i).get(1));
                    week = db.getSortedData().get(i).get(2);
                    message = db.getSortedData().get(i).get(3);
                    song = db.getSortedData().get(i).get(4);
                    image = db.getSortedData().get(i).get(5);
                    state = db.getSortedData().get(i).get(6);
                    color = db.getSortedData().get(i).get(7);
                    break;
                }
            }
        }

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.alarm_layout);

        relativeLayout.setBackground(Drawable.createFromPath(image));

        next_week = String.valueOf(ConvertIntoNumeric(week) + 1);

        TextView messageTextView = (TextView) findViewById(R.id.alarm_text_message);

        if (message != null) {
            messageTextView.setText(message);
            if (ConvertIntoNumeric(color) != 0)
                messageTextView.setTextColor(ConvertIntoNumeric(color));
            else
                messageTextView.setTextColor(ContextCompat.getColor(AlarmActivity.this, R.color.white));
        }

        if (player != null){
            stopPlayer();
        }

        if (player == null){
            if (getResId(song, R.raw.class) != -1) {
                player = MediaPlayer.create(this, getResId(song, R.raw.class));
            }
            else{
                player = MediaPlayer.create(this, Uri.parse(song));
            }
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setLooping(true);
            player.start();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });

//            Handler handler=new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (player != null) {
//                        player.stop();
//                    }
//                    stopPlayer();
//                    db.updateData(time, day, next_week, message, song, image, state);
//                    openMainActivity();
//                }
//            }, 60 * 1000);

        }

        lottieAnimationView = findViewById(R.id.dismiss_alarm_animation_button);

        lottieAnimationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlayer();
                db.updateWeek(day, next_week);
                finish();
                openMainActivity();
            }
        });

    }

    public void stopPlayer(){
        if (player != null){
            player.release();
            player = null;
        }
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void openMainActivity(){
        startActivity(new Intent(AlarmActivity.this, MainActivity.class));
    }


    public int ConvertIntoNumeric(String xVal)
    {
        try
        {
            return Integer.parseInt(xVal);
        }
        catch(Exception ex)
        {
            return 0;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
