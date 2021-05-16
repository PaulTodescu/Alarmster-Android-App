package com.example.alarmster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends Activity {

    DBHelper db;

    TextView nextAlarm;

    int timePickerHour;
    int timePickerMinute;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_Alarmster);

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);

        System.out.println("DATA: " + db.getSortedData()); // prints the database content

        getNext(); // prints the next alarm

        MaterialButton createNewAlarmButton = (MaterialButton) findViewById(R.id.create_new_alarm);
        createNewAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAlarmActivity();
            }
        });

        LinearLayout mainLayout = findViewById(R.id.main_layout);

        nextAlarm = (TextView) findViewById(R.id.next_alarm_time);

        if (db.getData().size() > 0) {

            for (int i=0; i<db.getData().size(); i++) {

                String time = db.getData().get(i).get(0);
                StringBuffer day = new StringBuffer();
                day.append(db.getData().get(i).get(1));
                String week = db.getData().get(i).get(2);
                String message = db.getData().get(i).get(3);
                String song = db.getData().get(i).get(4);
                String image = db.getData().get(i).get(5);
                String state = db.getData().get(i).get(6);

                createAlarmView(mainLayout, time, day, week, message, song, image, state);

            }

            scheduleAlarm();

        }

        else {
            noAlarmSetView(mainLayout);
        }

        nextAlarm.setText(getNextAlarmText());

        if (checkIfAlarmExists()){
            System.out.println("An alarm is scheduled!");
        }
        else {
            System.out.println("No alarm is scheduled!");
        }

    }

    public void openCreateAlarmActivity (){
        startActivity(new Intent(this, CreateAlarm.class));
    }

    public void createAlarmView (LinearLayout layout, String time, StringBuffer day, String week, String message, String song, String image, String state){

        LinearLayout alarm = new LinearLayout(this);

        alarm.setBackgroundResource(R.drawable.alarm_background);
        alarm.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams alarmParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        alarmParams.setMargins(30, 50, 30, 30);

        // Add time and days of the alarm

        LinearLayout time_days = new LinearLayout(this);
        time_days.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams time_days_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);


        TextView alarmText = new TextView(this);
        LinearLayout.LayoutParams alarmTextParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        alarmTextParam.setMargins(30, 30, 0, 0);

        alarmText.setTextSize(30);
        alarmText.setText(time);
        alarmText.setTextColor(getResources().getColor(R.color.black));

        time_days.addView(alarmText, alarmTextParam);


        TextView daysText = new TextView(this);
        LinearLayout.LayoutParams daysTextParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        daysTextParam.setMargins(30, 30, 0, 0);

        daysText.setTextSize(15);
        daysText.setText(getSelectedDay(day.toString()));
        daysText.setLines(2);

        time_days.addView(daysText, daysTextParam);

        alarm.addView(time_days, time_days_params);


        LinearLayout edit_delete_alarm = new LinearLayout(this);
        edit_delete_alarm.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams edit_delete_alarm_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);


        // add the edit alarm button

        Button edit_button = new Button(this);
        LinearLayout.LayoutParams editButtonParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        editButtonParam.setMargins(30, 30, 0, 0);

        edit_button.setText(getResources().getString(R.string.edit));
        edit_button.setTextColor(getResources().getColor(R.color.white));
        edit_button.setBackgroundResource(R.drawable.options_button);

        edit_delete_alarm.addView(edit_button, editButtonParam);

        edit_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                        R.style.themeOnverlay_timePicker,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String h;
                                String m;
                                if (hourOfDay < 10){
                                    h = "0" + hourOfDay;
                                }
                                else{
                                    h = String.valueOf(hourOfDay);
                                }
                                if (minute < 10){
                                    m = "0" + minute;
                                }
                                else{
                                    m = String.valueOf(minute);
                                }

                                String selectedTime = h + ":" + m;

                                db.updateTime(day, selectedTime);

                                Calendar c1 = Calendar.getInstance();
                                c1.set(Calendar.DAY_OF_WEEK, getCalendarDay(day.toString()));
                                c1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c1.set(Calendar.MINUTE, minute);
                                c1.set(Calendar.SECOND, 0);
                                c1.set(Calendar.MILLISECOND, 0);

                                Calendar c2 = Calendar.getInstance();
                                c2.set(Calendar.DAY_OF_WEEK, getCalendarDay(day.toString()));
                                c2.set(Calendar.HOUR_OF_DAY, getHour(time));
                                c2.set(Calendar.MINUTE, getMinute(time));
                                c2.set(Calendar.SECOND, 0);
                                c2.set(Calendar.MILLISECOND, 0);

                                String previous_week = String.valueOf(ConvertIntoNumeric(week) - 1);

                                if (c1.getTimeInMillis() >= System.currentTimeMillis() && c2.getTimeInMillis() <= System.currentTimeMillis()){
                                    db.updateWeek(day, previous_week);
                                }

                                alarmText.setText(selectedTime);
                                nextAlarm.setText(getNextAlarmText());
                                unscheduleAlarm();
                                scheduleAlarm();
                            }
                        }, 12, 0, DateFormat.is24HourFormat(MainActivity.this));
                timePickerDialog.updateTime(timePickerHour, timePickerMinute);
                timePickerDialog.show();
            }

        });

        // add the delete alarm button

        Button delete_button = new Button (this);
        LinearLayout.LayoutParams deleteButtonParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        deleteButtonParam.setMargins(30, 30, 0, 0);


        delete_button.setText(getResources().getString(R.string.delete));
        delete_button.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
        delete_button.setBackgroundResource(R.drawable.options_button);

        edit_delete_alarm.addView(delete_button, deleteButtonParam);

        // add the edit and delete buttons to the alarm layout

        alarm.addView(edit_delete_alarm, edit_delete_alarm_params);

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Delete Alarm?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                layout.removeView(alarm);
                                db.deleteData(day);
                                displayDeleteMessage(time);
                                if (db.getSortedData().size() == 0){
                                    noAlarmSetView(findViewById(R.id.main_layout));
                                    nextAlarm.setText(getNextAlarmText());
                                }
                                else{
                                    nextAlarm.setText(getNextAlarmText());
                                }

                                unscheduleAlarm();
                                scheduleAlarm();

                                if(checkIfAlarmExists()){
                                    System.out.println("Alarm was unscheduled!");
                                }
                                else{
                                    System.out.println("Failed to unschedule alarm!");
                                }

                            }
                        })

                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }

        });

        SwitchCompat toggle_alarm_button = new SwitchCompat(this);
        LinearLayout.LayoutParams toggleAlarmButtonParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        toggleAlarmButtonParam.setMargins(30, 30, 0, 0);
        toggle_alarm_button.setThumbResource(R.drawable.thumb1);
        toggle_alarm_button.setTrackResource(R.drawable.track);
        toggle_alarm_button.setShowText(true);
        toggle_alarm_button.setTextOff(getResources().getString(R.string.off));
        toggle_alarm_button.setTextOn(getResources().getString(R.string.on));

        if (state.equals("on")){
            toggle_alarm_button.setChecked(true);
        }
        else if (state.equals("off")){
            toggle_alarm_button.setChecked(false);
        }

        toggle_alarm_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    db.updateState(day, "on");
                    nextAlarm.setText(getNextAlarmText());
                    unscheduleAlarm();
                    scheduleAlarm();

                }
                else {
                    db.updateState(day, "off");
                    nextAlarm.setText(getNextAlarmText());
                    unscheduleAlarm();
                    scheduleAlarm();
                }
            }
        });

        edit_delete_alarm.addView(toggle_alarm_button, toggleAlarmButtonParam);

        // add the alarm layout to the main layout

        layout.addView(alarm, alarmParams);

    }

    public void displayDeleteMessage(String time){
        Toast del_message = Toast.makeText(this , "Alarm Deleted!", Toast.LENGTH_SHORT);
        del_message.show();
    }

    public void noAlarmSetView(LinearLayout layout){

        TextView noAlarm = new TextView(this);
        noAlarm.setText(getResources().getString(R.string.noalarm));
        noAlarm.setTextSize(25);
        noAlarm.setTextColor(getResources().getColor(R.color.white));
        noAlarm.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout.LayoutParams noAlarmParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;

        noAlarmParams.setMargins(0, height/3, 0, 0);

        layout.addView(noAlarm, noAlarmParams);

    }

    public int getHour(String time){
        StringBuilder minute = new StringBuilder();
        if (time.charAt(0) != '0'){
            minute.append(time.charAt(0));
        }
        minute.append(time.charAt(1));
        return Integer.parseInt(String.valueOf(minute));
    }

    public int getMinute(String time){
        StringBuilder minute = new StringBuilder();
        if (time.charAt(3) != '0'){
            minute.append(time.charAt(3));
        }
        minute.append(time.charAt(4));
        return Integer.parseInt(String.valueOf(minute));
    }

    boolean checkIfAlarmExists(){
        return (PendingIntent.getBroadcast(getApplicationContext(), 0,
                new Intent(getApplicationContext(), AlarmReceiver.class), PendingIntent.FLAG_NO_CREATE) != null);
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

    public StringBuilder getSelectedDay(String days){

        String [] days_array = days.split("\\s+");

        StringBuilder selected_days = new StringBuilder();

        if (days_array.length == 1){
            if (days_array[0].equals("1"))
                selected_days.append("Monday");
            if (days_array[0].equals("2"))
                selected_days.append("Tuesday");
            if (days_array[0].equals("3"))
                selected_days.append("Wednesday");
            if (days_array[0].equals("4"))
                selected_days.append("Thursday");
            if (days_array[0].equals("5"))
                selected_days.append("Friday");
            if (days_array[0].equals("6"))
                selected_days.append("Saturday");
            if (days_array[0].equals("7"))
                selected_days.append("Sunday");
        }
        else {
            for (String s : days_array) {
                if (s.equals("1"))
                    selected_days.append("Mon ");
                if (s.equals("2"))
                    selected_days.append("Tue ");
                if (s.equals("3"))
                    selected_days.append("Wed ");
                if (s.equals("4"))
                    selected_days.append("Thur ");
                if (s.equals("5"))
                    selected_days.append("Fri ");
                if (s.equals("6"))
                    selected_days.append("Sat ");
                if (s.equals("7"))
                    selected_days.append("Sun ");
            }
        }
        return selected_days;
    }

    public String getNextAlarmText(){
        String NextAlarmText = null;

        if (db.getSortedData().size() > 0) {
            for (int i = 0; i < db.getSortedData().size(); i++) {
                if (db.getSortedData().get(i).get(6).equals("on")) {
                    NextAlarmText = ":  " + getSelectedDay(db.getSortedData().get(i).get(1)) + ", " + db.getSortedData().get(i).get(0);
                    break;
                } else if (i == db.getSortedData().size() - 1) {
                    NextAlarmText = ":  None";
                }
            }
        }
        else{
            NextAlarmText = ":  None";
        }

        return NextAlarmText;
    }

    public void getNext(){
        String NextAlarmText = null;

        if (db.getSortedData().size() > 0) {
            for (int i = 0; i < db.getSortedData().size(); i++) {
                if (db.getSortedData().get(i).get(6).equals("on")) {
                    NextAlarmText = ":  " + getSelectedDay(db.getSortedData().get(i).get(1)) + ", " + db.getSortedData().get(i).get(0);
                    break;
                } else if (i == db.getSortedData().size() - 1) {
                    NextAlarmText = ":  None";
                }
            }
        }
        else{
            NextAlarmText = ":  None";
        }

        System.out.println("NEXT ALARM: " + NextAlarmText);
    }

    public void scheduleAlarm(){
        for (int i=0; i<db.getSortedData().size(); i++){
            if (db.getSortedData().get(i).get(6).equals("on")){

                Calendar c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK, getCalendarDay(db.getSortedData().get(i).get(1)));
                c.set(Calendar.HOUR_OF_DAY, getHour(db.getSortedData().get(i).get(0)));
                c.set(Calendar.MINUTE, getMinute(db.getSortedData().get(i).get(0)));
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);

                if (c.getTimeInMillis() <= System.currentTimeMillis()) {
                    c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 7);
                }

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                        0, intent, 0);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

                break;
            }
        }
    }

    public void unscheduleAlarm(){
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                0, intent, PendingIntent.FLAG_NO_CREATE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}