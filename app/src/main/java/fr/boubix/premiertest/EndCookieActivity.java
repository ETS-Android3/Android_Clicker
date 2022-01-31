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

public class EndCookieActivity extends AppCompatActivity {
    private int points;
    private TextView clics;
    private String soundCheck;
    private String themeCheck;
    private TextView textClics;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        MediaPlayer clicButton = MediaPlayer.create(this, R.raw.button_sound);
        textClics=  (TextView) findViewById(R.id.text_score);
        File path = getApplicationContext().getExternalFilesDir("");
        File file  = new File(path, "save_data_clicker.txt");
        ArrayList res = new ArrayList<String>();
        view = this.getWindow().getDecorView();

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

        soundCheck = res.get(3).toString();
        themeCheck = res.get(4).toString();

        setThemeDark();
        setThemeLight();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            points = extras.getInt("points_clicker");
        }

        clics = (TextView) findViewById(R.id.score_cookie);
        System.out.println("Points : " + points);
        System.out.println("Valeur de TextView : " + clics);
        //clics.setText(points + " ");

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
    }

    private void setThemeLight(){
        if (themeCheck.equals("clair")){
            //clics.setTextColor(0xffffffff);
            //textClics.setTextColor(0xffffffff);
            view.setBackgroundColor(0xffffffff);
        }
    }

    private void setThemeDark(){
        if (themeCheck.equals("sombre")){
            //clics.setTextColor(0xff000000);
            //textClics.setTextColor(0xff000000);
            view.setBackgroundColor(0xff000000);
        }
    }
}