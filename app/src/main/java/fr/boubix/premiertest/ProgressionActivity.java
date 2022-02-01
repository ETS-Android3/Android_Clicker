package fr.boubix.premiertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ProgressionActivity extends AppCompatActivity {

    private String sound_check;
    private MediaPlayer back_sound;
    private String theme;
    private ArrayList res = new ArrayList<String>();
    private TextView text;
    private View viewMain;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progression);

        MediaPlayer click_sound = MediaPlayer.create(this, R.raw.button_sound);
        back_sound = MediaPlayer.create(this, R.raw.back_sound);
        viewMain = this.getWindow().getDecorView();
        text = (TextView) findViewById(R.id.text_progression);
        img = (ImageView) findViewById(R.id.back_button_progression);

        getData();
        checkTheme();

        Button clickerBtn = (Button) findViewById(R.id.button_clicker_progression);
        clickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sound_check.equals("on")) {
                    click_sound.start();
                }
                Intent otherActivity = new Intent(getApplicationContext(), ProgressionClickerActivity.class);
                startActivity(otherActivity);
                overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);
                finish();
            }
        });

        Button aimClickerBtn = (Button) findViewById(R.id.button_aim_clicker_progression);
        aimClickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sound_check.equals("on")) {
                    click_sound.start();
                }
                Intent otherActivity = new Intent(getApplicationContext(), ProgressionAimClickerActivity.class);
                startActivity(otherActivity);
                overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);
                finish();
            }
        });

        Button pianoTilesBtn = (Button) findViewById(R.id.button_piano_tiles_progression);
        pianoTilesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sound_check.equals("on")) {
                    click_sound.start();
                }
                Intent otherActivity = new Intent(getApplicationContext(), ProgressionPianoActivity.class);
                startActivity(otherActivity);
                overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);
                finish();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sound_check.equals("on")) {
                    back_sound.start();
                }
                Intent otherActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(otherActivity);
                overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);
                finish();
            }
        });
    }

    private void getData(){
        File path = getApplicationContext().getExternalFilesDir("");
        File file  = new File(path, "save_data_clicker.txt");
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

        sound_check = res.get(3).toString();
        theme = res.get(4).toString();
    }

    private void checkTheme(){
        if (theme.equals("clair")){
            text.setTextColor(0xff000000);
            img.setColorFilter(0xff000000);
            viewMain.setBackgroundColor(0xffffffff);
        } else if (theme.equals("sombre")){
            text.setTextColor(0xffffffff);
            img.setColorFilter(0xffffffff);
            viewMain.setBackgroundColor(0xff000000);
        }
    }

    public void onBackPressed(){
        Intent nextActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextActivity);
        overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);
        finish();
    }
}