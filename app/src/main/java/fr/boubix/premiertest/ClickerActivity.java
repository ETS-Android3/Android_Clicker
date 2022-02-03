package fr.boubix.premiertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ClickerActivity extends AppCompatActivity {

    private TextView points;
    private ImageView cookie;
    private int clicks = 0;
    private int counterTime;
    private int counter;
    private String soundCheck;
    private boolean quit = false;
    private int colorId;
    private String color;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookie);

        this.points = (TextView) findViewById(R.id.points);
        this.cookie = (ImageView) findViewById(R.id.cookie);
        TextView time = (TextView) findViewById(R.id.text_time);
        MediaPlayer click_sound = MediaPlayer.create(this, R.raw.button_sound);
        view = getWindow().getDecorView();

        setOptionValues();


        new CountDownTimer(counterTime*1000, 100){
            public void onTick(long millisUntilFinished) {
                time.setText("Temps : " + String.valueOf(counterTime - counter/10));

                cookie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (soundCheck.equals("on")){
                            click_sound.start();
                        }
                        clicks++;
                        points.setText("Points : " + clicks);
                        cookie.getLayoutParams().width += 14;
                        cookie.getLayoutParams().height += 14;
                    }
                });
                counter++;
                cookie.getLayoutParams().width -= 8;
                cookie.getLayoutParams().height -= 8;

                if (!quit && (cookie.getLayoutParams().height < 0 || cookie.getLayoutParams().width < 0)){
                    quit = true;
                    try {
                        saveGame();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Intent nextActivity = new Intent(getApplicationContext(), EndClickerActivity.class);
                    nextActivity.putExtra("points_clicker", clicks);
                    startActivity(nextActivity);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    finish();
                }
            }

            public void onFinish(){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (counter >= 10 && (cookie.getLayoutParams().height > 0 || cookie.getLayoutParams().width > 0) && !quit) {
                    quit = true;
                    try {
                        saveGame();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Intent nextActivity = new Intent(getApplicationContext(), EndClickerActivity.class);
                    nextActivity.putExtra("points_clicker", clicks);
                    startActivity(nextActivity);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    finish();
                }
            }
        }.start();
    }

    public void saveGame() throws FileNotFoundException {
        File path = getApplicationContext().getExternalFilesDir("");
        File file  = new File(path, "save_game_clicker_" + String.valueOf(counterTime) + ".txt");
        FileOutputStream writer = new FileOutputStream(file, true);
        if (file.length() == 0){
            try {
                String str = "0\n"; //Ligne 0
                writer.write(str.getBytes());
                str = "0\n"; //Ligne 1
                writer.write(str.getBytes());
                str = "0\n"; //Ligne 2
                writer.write(str.getBytes());
                writer.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            String str = clicks + "\n"; //Ligne 0
            writer.write(str.getBytes());
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed(){
        Intent nextActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextActivity);
        overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);
        finish();
    }

    private void setOptionValues(){
        File path = getApplicationContext().getExternalFilesDir("");
        File file  = new File(path, "save_data_clicker.txt");
        ArrayList res = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null){
                line = reader.readLine();
                res.add(line);
            }
            reader.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

        counterTime = Integer.parseInt(res.get(1).toString());
        soundCheck = res.get(3).toString();

        //Set theme
        if (res.get(4).equals("clair")){
            view.setBackgroundResource(R.color.white); //Set theme white
        }else if (res.get(4).equals("sombre")){
            view.setBackgroundResource(R.color.black); //Set theme black
        }

        color = res.get(0).toString();
        //Set color button
        if (color.equals("red")){
            colorId = Color.argb(255, 255, 0, 0);
        } else if (color.equals("blue")){
            colorId = Color.argb(255, 0, 255, 255);
        } else if (color.equals("green")){
            colorId = Color.argb(255, 0, 255, 0);
        } else if (color.equals("yellow")){
            colorId = Color.argb(255, 255, 255, 0);
        } else if (color.equals("pink")){
            colorId = Color.argb(255, 255, 0, 255);
        }

        if (res.get(4).equals("clair") || res.get(4).equals("sombre")) {
            cookie.setColorFilter(colorId);
        } else {
            cookie.setImageResource(R.drawable.planete);
            view.setBackgroundResource(R.drawable.background_clicker);
        }
    }
}