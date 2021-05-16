package com.example.alarmster;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

import java.lang.reflect.Field;

public class SelectMusic extends Activity {

    MediaPlayer player = null;

    String Ringtone = null;

    String RingtonePathName = null;

    RadioButton radioButton;

    private static final int PERMISSION_REQUEST = 0;
    private static final int RESULT_LOAD_SONG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select_music);

        radioButton = (RadioButton) findViewById(R.id.default_songs);
        if (radioButton.isChecked()) {
            displayDefaultSongs();
        }

        Button saveSongSelectionButton = (Button) findViewById(R.id.save_song_selection_button);
        saveSongSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("value", Ringtone);
                editor.apply();
                displaySaveMessage();
                openCreateAlarmActivity();
            }
        });

        Button cancelSongSelectionButton = (Button) findViewById(R.id.cancel_song_selection__button);
        cancelSongSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAlarmActivity();
            }
        });

    }


    public void onSongsTypeClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        if (view.getId() == R.id.default_songs){
            if (checked) {
                displayDefaultSongs();
            }
        }
        else if (view.getId() == R.id.custom_songs){
            if (checked) {

                radioButton.setChecked(true);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
                }

                else {
                    Intent i = new Intent(Intent.ACTION_PICK,
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_SONG);
                }

            }
        }
    }

    // create song button

    public void createSongView(RadioGroup songs, String title, int id){
        LinearLayout.LayoutParams songParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        RadioButton song = new RadioButton(this);

        song.setBackgroundResource(R.drawable.song_button);
        song.setText(title);
        song.setTextSize(20);
        song.setTextColor(getResources().getColor(R.color.white));
        song.setId(id);

        ColorStateList myColorStateList = new ColorStateList(
                new int[][]{
                        new int[]{getResources().getColor(R.color.white)}
                },
                new int[]{getResources().getColor(R.color.white)}
        );

        song.setButtonTintList(myColorStateList);
        ViewCompat.setLayoutDirection(song, ViewCompat.LAYOUT_DIRECTION_RTL);
        song.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

        song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSelectedDefaultSong(song);
            }
        });


        songs.addView(song, songParams);

        View horizontal_bar = new View(this);
        horizontal_bar.setBackgroundResource(R.color.black);
        LinearLayout.LayoutParams horizontalBarParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 3);

        songs.addView(horizontal_bar, horizontalBarParams);
    }

    // display default songs

    public void displayDefaultSongs (){
        RadioGroup songs = findViewById(R.id.songs_section);

        // add default songs

        createSongView(songs, "Ringtone 1", 1);

        createSongView(songs, "Ringtone 2", 2);

        createSongView(songs, "Ringtone 3", 3);

        createSongView(songs, "Ringtone 4", 4);

        createSongView(songs, "Ringtone 5", 5);

        createSongView(songs, "Ringtone 6", 6);

        createSongView(songs, "Ringtone 7", 7);

        createSongView(songs, "Ringtone 8", 8);

        createSongView(songs, "Ringtone 9", 9);

        createSongView(songs, "Ringtone 10", 10);

        createSongView(songs, "Ringtone 11", 11);

        createSongView(songs, "Ringtone 12", 12);

    }

    @SuppressLint("ResourceType")
    public void playSelectedDefaultSong (View view){


        boolean checked = ((RadioButton) view).isChecked();

        if (view.getId() == 1){
            if (checked) {
                Ringtone = "song" + view.getId();
                if (player != null){
                    stopPlayer();
                }
                if (player == null){
                    player = MediaPlayer.create(this, R.raw.song1);
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();
                        }
                    });
                }
                player.start();
            }
        }

        else if (view.getId() == 2){
            if (checked) {
                Ringtone = "song" + view.getId();
                if (player != null){
                    stopPlayer();
                }
                if (player == null){
                    player = MediaPlayer.create(this, R.raw.song2);
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();
                        }
                    });
                }
                player.start();
            }
        }

        else if (view.getId() == 3){
            if (checked) {
                Ringtone = "song" + view.getId();
                if (player != null){
                    stopPlayer();
                }
                if (player == null){
                    player = MediaPlayer.create(this, R.raw.song3);
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();
                        }
                    });
                }
                player.start();
            }
        }

        else if (view.getId() == 4){
            if (checked) {
                Ringtone = "song" + view.getId();
                if (player != null){
                    stopPlayer();
                }
                if (player == null){
                    player = MediaPlayer.create(this, R.raw.song4);
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();
                        }
                    });
                }
                player.start();
            }
        }

        else if (view.getId() == 5){
            if (checked) {
                Ringtone = "song" + view.getId();
                if (player != null){
                    stopPlayer();
                }
                if (player == null){
                    player = MediaPlayer.create(this, R.raw.song5);
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();
                        }
                    });
                }
                player.start();
            }
        }

        else if (view.getId() == 6){
            Ringtone = "song" + view.getId();
            if (checked) {
                if (player != null){
                    stopPlayer();
                }
                if (player == null){
                    player = MediaPlayer.create(this, R.raw.song6);
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();
                        }
                    });
                }
                player.start();
            }
        }

        else if (view.getId() == 7){
            if (checked) {
                Ringtone = "song" + view.getId();
                if (player != null){
                    stopPlayer();
                }
                if (player == null){
                    player = MediaPlayer.create(this, R.raw.song7);
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();
                        }
                    });
                }
                player.start();
            }
        }

        else if (view.getId() == 8){
            Ringtone = "song" + view.getId();
            if (checked) {
                if (player != null){
                    stopPlayer();
                }
                if (player == null){
                    player = MediaPlayer.create(this, R.raw.song8);
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();
                        }
                    });
                }
                player.start();
            }
        }

        else if (view.getId() == 9){
            Ringtone = "song" + view.getId();
            if (checked) {
                if (player != null){
                    stopPlayer();
                }
                if (player == null){
                    player = MediaPlayer.create(this, R.raw.song9);
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();
                        }
                    });
                }
                player.start();
            }
        }

        else if (view.getId() == 10){
            Ringtone = "song" + view.getId();
            if (checked) {
                if (player != null){
                    stopPlayer();
                }
                if (player == null){
                    player = MediaPlayer.create(this, R.raw.song10);
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();
                        }
                    });
                }
                player.start();
            }
        }

        else if (view.getId() == 11){
            Ringtone = "song" + view.getId();
            if (checked) {
                if (player != null){
                    stopPlayer();
                }
                if (player == null){
                    player = MediaPlayer.create(this, R.raw.song11);
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();
                        }
                    });
                }
                player.start();
            }
        }

        else if (view.getId() == 12){
            Ringtone = "song" + view.getId();
            if (checked) {
                if (player != null){
                    stopPlayer();
                }
                if (player == null){
                    player = MediaPlayer.create(this, R.raw.song12);
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();
                        }
                    });
                }
                player.start();
            }
        }

    }

    public void stopPlayer(){
        if (player != null){
            player.release();
            player = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }

    public void openCreateAlarmActivity (){
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_SONG);
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_SONG) {
            if (resultCode == RESULT_OK) {
                Uri selectedSong = data.getData();
                String[] filePathColumn = {MediaStore.Audio.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedSong, filePathColumn,
                        null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                Ringtone = cursor.getString(columnIndex);
                RingtonePathName = Uri.parse(cursor.getString(columnIndex)).getLastPathSegment().toString();
                // Toast.makeText(SelectMusic.this, songName, Toast.LENGTH_SHORT).show();
                cursor.close();
                SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("value", Ringtone);
                editor.apply();
                displaySaveMessage();
                openCreateAlarmActivity();
            }
        }
    }

    private void displaySaveMessage() {
        if (RingtonePathName != null) {
            Toast.makeText(this, RingtonePathName + " saved!", Toast.LENGTH_SHORT).show();
        }
        else if (Ringtone != null){
            Toast.makeText(this, Ringtone + " saved!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "No Ringtone Selected!", Toast.LENGTH_SHORT).show();
        }
    }

}
