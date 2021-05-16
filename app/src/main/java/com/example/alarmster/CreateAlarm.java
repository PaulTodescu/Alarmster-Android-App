package com.example.alarmster;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import yuku.ambilwarna.AmbilWarnaDialog;

public class CreateAlarm extends Activity {

    TimePicker timePicker;

    int mon_ind = 0;
    int tue_ind = 0;
    int wed_ind = 0;
    int thur_ind = 0;
    int fri_ind = 0;
    int sat_ind = 0;
    int sun_ind = 0;

    DBHelper db;

    String current_day;

    EditText editText;

    int defaultColor;

    int selectedColor = 0;

    Button pickColor;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_create_alarm);

        Button confirmButton = findViewById(R.id.confirm_alarm_button);

        Button cancelButton = findViewById(R.id.cancel_alarm_button);

        pickColor = findViewById(R.id.pick_color_button);

        timePicker = (TimePicker) findViewById(R.id.createalarm_timePicker);
        timePicker.setIs24HourView(true);

        editText = (EditText) findViewById(R.id.enter_text);

        defaultColor = ContextCompat.getColor(CreateAlarm.this, R.color.white);

        db = new DBHelper(this);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        Date date = calendar.getTime();
        current_day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());

        Button mo = findViewById(R.id.monday);
        Button tue = findViewById(R.id.tuesday);
        Button wed = findViewById(R.id.wednesday);
        Button thur = findViewById(R.id.thursday);
        Button fri = findViewById(R.id.friday);
        Button sat = findViewById(R.id.saturday);
        Button sun = findViewById(R.id.sunday);

        if (mon_ind == 1 || current_day.equals("Monday"))
            mo.setBackgroundResource(R.drawable.day_button_pressed);
        else if (tue_ind == 1 || current_day.equals("Tuesday"))
            tue.setBackgroundResource(R.drawable.day_button_pressed);
        else if (wed_ind == 1 || current_day.equals("Wednesday"))
            wed.setBackgroundResource(R.drawable.day_button_pressed);
        else if (thur_ind == 1 || current_day.equals("Thursday"))
            thur.setBackgroundResource(R.drawable.day_button_pressed);
        else if (fri_ind == 1 || current_day.equals("Friday"))
            fri.setBackgroundResource(R.drawable.day_button_pressed);
        else if (sat_ind == 1 || current_day.equals("Saturday"))
            sat.setBackgroundResource(R.drawable.day_button_pressed);
        else if (sun_ind == 1 || current_day.equals("Sunday"))
            sun.setBackgroundResource(R.drawable.day_button_pressed);

        mo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mon_ind++;
                if (mon_ind == 1) {
                    mo.setBackgroundResource(R.drawable.day_button_pressed);
                    tue.setBackgroundResource(R.drawable.day_button_unpressed);
                    wed.setBackgroundResource(R.drawable.day_button_unpressed);
                    thur.setBackgroundResource(R.drawable.day_button_unpressed);
                    fri.setBackgroundResource(R.drawable.day_button_unpressed);
                    sat.setBackgroundResource(R.drawable.day_button_unpressed);
                    sun.setBackgroundResource(R.drawable.day_button_unpressed);
                    tue_ind = 0;
                    wed_ind = 0;
                    thur_ind = 0;
                    fri_ind = 0;
                    sat_ind = 0;
                    sun_ind = 0;
                }

            }
        });

        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tue_ind++;
                if (tue_ind == 1) {
                    tue.setBackgroundResource(R.drawable.day_button_pressed);
                    mo.setBackgroundResource(R.drawable.day_button_unpressed);
                    wed.setBackgroundResource(R.drawable.day_button_unpressed);
                    thur.setBackgroundResource(R.drawable.day_button_unpressed);
                    fri.setBackgroundResource(R.drawable.day_button_unpressed);
                    sat.setBackgroundResource(R.drawable.day_button_unpressed);
                    sun.setBackgroundResource(R.drawable.day_button_unpressed);
                    mon_ind = 0;
                    wed_ind = 0;
                    thur_ind = 0;
                    fri_ind = 0;
                    sat_ind = 0;
                    sun_ind = 0;
                }

            }
        });

        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wed_ind++;
                if (wed_ind == 1) {
                    wed.setBackgroundResource(R.drawable.day_button_pressed);
                    mo.setBackgroundResource(R.drawable.day_button_unpressed);
                    tue.setBackgroundResource(R.drawable.day_button_unpressed);
                    thur.setBackgroundResource(R.drawable.day_button_unpressed);
                    fri.setBackgroundResource(R.drawable.day_button_unpressed);
                    sat.setBackgroundResource(R.drawable.day_button_unpressed);
                    sun.setBackgroundResource(R.drawable.day_button_unpressed);
                    mon_ind = 0;
                    tue_ind = 0;
                    thur_ind = 0;
                    fri_ind = 0;
                    sat_ind = 0;
                    sun_ind = 0;
                }

            }
        });

        thur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thur_ind++;
                if (thur_ind == 1) {
                    thur.setBackgroundResource(R.drawable.day_button_pressed);
                    mo.setBackgroundResource(R.drawable.day_button_unpressed);
                    tue.setBackgroundResource(R.drawable.day_button_unpressed);
                    wed.setBackgroundResource(R.drawable.day_button_unpressed);
                    fri.setBackgroundResource(R.drawable.day_button_unpressed);
                    sat.setBackgroundResource(R.drawable.day_button_unpressed);
                    sun.setBackgroundResource(R.drawable.day_button_unpressed);
                    mon_ind = 0;
                    tue_ind = 0;
                    wed_ind = 0;
                    fri_ind = 0;
                    sat_ind = 0;
                    sun_ind = 0;
                }

            }
        });

        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fri_ind++;
                if (fri_ind == 1) {
                    fri.setBackgroundResource(R.drawable.day_button_pressed);
                    mo.setBackgroundResource(R.drawable.day_button_unpressed);
                    tue.setBackgroundResource(R.drawable.day_button_unpressed);
                    wed.setBackgroundResource(R.drawable.day_button_unpressed);
                    thur.setBackgroundResource(R.drawable.day_button_unpressed);
                    sat.setBackgroundResource(R.drawable.day_button_unpressed);
                    sun.setBackgroundResource(R.drawable.day_button_unpressed);
                    mon_ind = 0;
                    tue_ind = 0;
                    wed_ind = 0;
                    thur_ind = 0;
                    sat_ind = 0;
                    sun_ind = 0;
                }

            }
        });

        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sat_ind++;
                if (sat_ind == 1) {
                    sat.setBackgroundResource(R.drawable.day_button_pressed);
                    mo.setBackgroundResource(R.drawable.day_button_unpressed);
                    tue.setBackgroundResource(R.drawable.day_button_unpressed);
                    wed.setBackgroundResource(R.drawable.day_button_unpressed);
                    thur.setBackgroundResource(R.drawable.day_button_unpressed);
                    fri.setBackgroundResource(R.drawable.day_button_unpressed);
                    sun.setBackgroundResource(R.drawable.day_button_unpressed);
                    mon_ind = 0;
                    tue_ind = 0;
                    wed_ind = 0;
                    thur_ind = 0;
                    fri_ind = 0;
                    sun_ind = 0;
                }

            }
        });

        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sun_ind++;
                if (sun_ind == 1) {
                    sun.setBackgroundResource(R.drawable.day_button_pressed);
                    mo.setBackgroundResource(R.drawable.day_button_unpressed);
                    tue.setBackgroundResource(R.drawable.day_button_unpressed);
                    wed.setBackgroundResource(R.drawable.day_button_unpressed);
                    thur.setBackgroundResource(R.drawable.day_button_unpressed);
                    fri.setBackgroundResource(R.drawable.day_button_unpressed);
                    sat.setBackgroundResource(R.drawable.day_button_unpressed);
                    mon_ind = 0;
                    tue_ind = 0;
                    wed_ind = 0;
                    thur_ind = 0;
                    fri_ind = 0;
                    sat_ind = 0;
                }
            }
        });

        ImageView addSong = (ImageView) findViewById(R.id.add_music_button);
        addSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectMusicActivity();
            }
        });

        ImageView addImage = (ImageView) findViewById(R.id.add_image_button);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectImageActivity();
            }
        });

        pickColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openColorPicker();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences1 = getSharedPreferences("myKey", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                editor1.remove("value");
                editor1.apply();

                SharedPreferences sharedPreferences2 = getSharedPreferences("imageKey", MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                editor2.remove("value");
                editor2.apply();

                openMainActivity();
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                String hour;
                String minute;
                if (timePicker.getHour() < 10){
                    hour = "0" + timePicker.getHour();
                }
                else{
                    hour = String.valueOf(timePicker.getHour());
                }
                if (timePicker.getMinute() < 10){
                    minute = "0" + timePicker.getMinute();
                }
                else{
                    minute = String.valueOf(timePicker.getMinute());
                }

                String selectedTime = hour + ":" + minute;

                StringBuffer selectedDay = new StringBuffer("");

                if (mon_ind == 1)
                    selectedDay.append(1);
                else if (tue_ind == 1)
                    selectedDay.append(2);
                else if (wed_ind == 1)
                    selectedDay.append(3);
                else if (thur_ind == 1)
                    selectedDay.append(4);
                else if (fri_ind == 1)
                    selectedDay.append(5);
                else if (sat_ind == 1)
                    selectedDay.append(6);
                else if (sun_ind == 1)
                    selectedDay.append(7);
                else
                    selectedDay.append(getDayIndex(current_day));

                String message = editText.getText().toString();

                SharedPreferences sharedPreferences1 = getSharedPreferences("myKey", MODE_PRIVATE);
                String songName = sharedPreferences1.getString("value","");

                SharedPreferences sharedPreferences2 = getSharedPreferences("imageKey", MODE_PRIVATE);
                String image = sharedPreferences2.getString("value","");

                String state = "on";

                Calendar c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK, getCalendarDay(selectedDay.toString()));
                c.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                c.set(Calendar.MINUTE, timePicker.getMinute());
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);

                String week;
                if (c.getTimeInMillis() <= System.currentTimeMillis()) {
                    week = String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR)+1);
                }
                else {
                    week = String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
                }

                String selectedColorValue = String.valueOf(selectedColor);

                if (songName.length()>0 && image.length()>0) {

                    boolean checkInsertedData = db.insertData(selectedTime, selectedDay, week, message, songName, image, state, selectedColorValue);

                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                    editor1.remove("value");
                    editor1.apply();

                    SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                    editor2.remove("value");
                    editor2.apply();

                    if (checkInsertedData) {
                        Toast.makeText(CreateAlarm.this, "New alarm added!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CreateAlarm.this, "Alarm exists, Use edit!", Toast.LENGTH_SHORT).show();
                    }

                    openMainActivity();
                    finish();
                }
                else{
                    String alertMessage = null;
                    if (songName.length() == 0 && image.length() == 0)
                        alertMessage = "Please select a song and image!";
                    else if (songName.length() == 0)
                        alertMessage = "Please select a song!";
                    else
                        alertMessage = "Please select an image!";

                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateAlarm.this);
                    builder.setMessage(alertMessage)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            }
        });

    }

    public void openColorPicker() {

        AmbilWarnaDialog picker = new AmbilWarnaDialog(CreateAlarm.this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                defaultColor = color;
                pickColor.setBackgroundColor(color);
                selectedColor = color;

            }
        });
        picker.show();


    }

    public void openMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if (view.getId() == R.id.radio_24){
            if (checked)
                timePicker.setIs24HourView(true);
        }

        else if (view.getId() == R.id.radio_am_pm){
            if (checked)
                timePicker.setIs24HourView(false);
        }

    }

    public void openSelectMusicActivity(){
        startActivity(new Intent (this, SelectMusic.class));
    }

    public void openSelectImageActivity() { startActivity(new Intent( this, SelectImage.class)); }

    public void displaySaveMessage(){
        Toast.makeText(this, "Alarm Saved!", Toast.LENGTH_SHORT).show();
    }

    public int getDayIndex(String day){
        switch (day) {
            case "Monday":
                return 1;
            case "Tuesday":
                return 2;
            case "Wednesday":
                return 3;
            case "Thursday":
                return 4;
            case "Friday":
                return 5;
            case "Saturday":
                return 6;
            case "Sunday":
                return 7;
            default:
                return 0;
        }
    }

    public int getCalendarDay(String day){
        int day_of_week = ConvertIntoNumeric(day);
        switch (day_of_week){
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
                return 6;
            case 6:
                return 7;
            case 7:
                return 1;
        }
        return 0;
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

}
