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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progression);

        File path = getApplicationContext().getExternalFilesDir("");
        File file  = new File(path, "save_data_clicker.txt");
        ArrayList res = new ArrayList<String>();
        MediaPlayer click_sound = MediaPlayer.create(this, R.raw.button_sound);
        back_sound = MediaPlayer.create(this, R.raw.back_sound);
        View viewMain = this.getWindow().getDecorView();

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
        String theme = res.get(4).toString();

        TextView text = (TextView) findViewById(R.id.text_progression);
        ImageView img = (ImageView) findViewById(R.id.back_button_progression);

        if (theme.equals("clair")){
            text.setTextColor(0xff000000);
            img.setColorFilter(0xff000000);
            viewMain.setBackgroundColor(0xffffffff);
        } else if (theme.equals("sombre")){
            text.setTextColor(0xffffffff);
            img.setColorFilter(0xffffffff);
            viewMain.setBackgroundColor(0xff000000);
        }

        Button clickerBtn = (Button) findViewById(R.id.button_clicker_progression);
        Button aimClickerBtn = (Button) findViewById(R.id.button_aim_clicker_progression);
        Button pianoTilesBtn = (Button) findViewById(R.id.button_piano_tiles_progression);

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

    public void onBackPressed(){
        Intent nextActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextActivity);
        overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);
        finish();
    }
}