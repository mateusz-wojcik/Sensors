package com.example.rudy.sensors;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private MediaPlayer mediaPlayer;
    private SensorManager sensorManager;
    private Sensor lightSensor, accelerometer;
    private long lastUpdate = 0;
    private float x, y, z;
    private static final int MIN_LUX_PAUSE = 50;
    private static final int MIN_TIME_BETWEEN_SHAKES = 1000;
    private static final float SHAKE_THRESHOLD = 12.0f;
    private final int[] songList = {R.raw.song_1, R.raw.song_2, R.raw.song_3, R.raw.song_4, R.raw.song_5};
    private int currentSong;
    private ListView listView;
    private ArrayList<Song> songArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setReferences();
        startMusic(currentSong);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSensorChanged(SensorEvent event) {
        lightService(event);
        shakeService(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, lightSensor , SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void setReferences(){
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mediaPlayer = new MediaPlayer();
        listView = findViewById(R.id.listView);
        fillSongList();
        listView.setAdapter(new ListViewAdapter(this, songArrayList));
        currentSong = 0;
    }

    public void startMusic(int songId){
        mediaPlayer = MediaPlayer.create(MainActivity.this, songList[songId]);
        mediaPlayer.start();

    }

    public void stopMusic(){
        mediaPlayer.stop();
    }

    public void shakeService(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            long currentTime = System.currentTimeMillis();
            if((currentTime - lastUpdate) > MIN_TIME_BETWEEN_SHAKES) {

                x = event.values[0];
                y = event.values[1];
                z = event.values[2];

                double acceleration = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2))
                        - SensorManager.GRAVITY_EARTH;

                if(acceleration > SHAKE_THRESHOLD) {
                    lastUpdate = currentTime;
                    changeSong();
                }
            }
        }
    }

    public void lightService(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_LIGHT) {
            Float lux = event.values[0];
            if(lux < MIN_LUX_PAUSE) mediaPlayer.pause();
            if(!mediaPlayer.isPlaying() && lux > MIN_LUX_PAUSE) mediaPlayer.start();
        }
    }

    public void changeSong(){
        listView.getChildAt(currentSong).setBackgroundColor(Color.TRANSPARENT);
        ++currentSong;
        if(currentSong == songList.length) currentSong = 0;
        listView.getChildAt(currentSong).setBackgroundColor(Color.BLUE);
        stopMusic();
        startMusic(currentSong);
    }

    public void fillSongList(){
        songArrayList = new ArrayList<>();

        songArrayList.add(new Song("Song 1", "Gatunek 1", R.drawable.tune));
        songArrayList.add(new Song("Song 2", "Gatunek 2", R.drawable.tune));
        songArrayList.add(new Song("Song 3", "Gatunek 3", R.drawable.tune));
        songArrayList.add(new Song("Song 4", "Gatunek 4", R.drawable.tune));
        songArrayList.add(new Song("Song 5", "Gatunek 5", R.drawable.tune));

    }

}
