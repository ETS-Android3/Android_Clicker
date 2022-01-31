package fr.boubix.premiertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class EndActivity extends AppCompatActivity {
    private TextView score;
    private int points;
    private File file;
    private ArrayList res = new ArrayList<String>();
    private TextView percent;
    private String soundCheck;
    private TextView textScore;
    private String themeCheck;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        MediaPlayer clicButton = MediaPlayer.create(this, R.raw.button_sound);
        textScore = (TextView) findViewById(R.id.score);
        view = this.getWindow().getDecorView();

        getData();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String str = extras.getString("caller");
            if (str.equals("CookieActivity")){
                points = extras.getInt("points_clicker");
            }else{
                points = extras.getInt("points_aim_clicker");
            }
            String temp = extras.getString("fichier");
            file = new File(temp + "/save_game_clicker.txt");
        }

        score = (TextView) findViewById(R.id.number_of_points);
        score.setText(points + " ");

        Button button = (Button) findViewById(R.id.menu_principal);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundCheck.equals("on")){
                    clicButton.start();
                }
                Intent otherActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(otherActivity);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        });

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

        percent = (TextView) findViewById(R.id.percent_display);
        float temp = Integer.parseInt((String) res.get(res.size() - 3));
        temp = ((points - temp)/temp) * 100;

        if ((int)temp > 0){
            percent.setTextColor(0xff00ff00); //Green
            percent.setText("+ " + (int)temp + " %");
        }else if ((int)temp < 0){
            percent.setTextColor(0xffff0000); //Red
            percent.setText(" " + (int)temp + " %");
        }else{
            percent.setText("+ 0 %");
        }
    }

    private void getData(){
        File path = getApplicationContext().getExternalFilesDir("");
        File file  = new File(path, "save_data_clicker.txt");
        ArrayList res2 = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null){
                line = reader.readLine();
                res2.add(line);
            }
            reader.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

        soundCheck = res2.get(3).toString();
        themeCheck = res2.get(4).toString();

        setThemeDark();
        setThemeLight();
    }

    private void setThemeLight(){
        if (themeCheck.equals("clair")){
            //score.setTextColor(0xff000000);
            textScore.setTextColor(0xff000000);
            view.setBackgroundColor(0xffffffff);
        }
    }

    private void setThemeDark(){
        System.out.println("Theme : " + themeCheck);
        if (themeCheck.equals("sombre")){
            //score.setTextColor(0xffffffff);
            textScore.setTextColor(0xffffffff);
            view.setBackgroundColor(0xff000000);
        }
    }
}