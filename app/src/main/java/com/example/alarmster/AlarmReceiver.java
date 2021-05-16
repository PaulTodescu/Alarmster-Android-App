package com.example.alarmster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.VibrationEffect;
import android.widget.Toast;
import android.os.Vibrator;

import androidx.legacy.content.WakefulBroadcastReceiver;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver(){}
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, AlarmActivity.class);
        i.setFlags((Intent.FLAG_ACTIVITY_NEW_TASK));
        context.startActivity(i);
    }
}
