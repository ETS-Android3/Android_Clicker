package fr.boubix.premiertest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button clicker;
    private Button aim_clicker;
    private View view;
    private boolean checked;
    private String sound_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        MediaPlayer click_sound = MediaPlayer.create(this, R.raw.button_sound);

        File f = new File(getApplicationContext().getExternalFilesDir("") + "/save_data_clicker.txt");
        if(!f.exists()) {
            try {
                saveDefaultValues();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        f = new File(getApplicationContext().getExternalFilesDir("") + "/save_game_clicker.txt");
        if(!f.exists()) {
            try {
                saveSomeValues();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        view = this.getWindow().getDecorView();
        setOption();

        this.clicker = (Button) findViewById(R.id.button_clicker);

        clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sound_check.equals("on")) {
                    click_sound.start();
                }
                Intent otherActivity = new Intent(getApplicationContext(), CookieActivity.class);
                otherActivity.putExtra("switch", checked);
                startActivity(otherActivity);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        });

        this.aim_clicker = (Button) findViewById(R.id.button_aim_clicker);

        aim_clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sound_check.equals("on")) {
                    click_sound.start();
                }
                Intent otherActivity = new Intent(getApplicationContext(), MultiPointActivity.class);
                otherActivity.putExtra("switch", checked);
                startActivity(otherActivity);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        });

        Button piano_tiles_button = (Button) findViewById(R.id.button_piano_tiles);

        piano_tiles_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sound_check.equals("on")) {
                    click_sound.start();
                }
                Intent otherActivity = new Intent(getApplicationContext(), PianoTilesActivity.class);
                startActivity(otherActivity);
                overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);
                finish();
            }
        });

        Button progression_button = (Button) findViewById(R.id.button_progression);

        progression_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sound_check.equals("on")) {
                    click_sound.start();
                }
                Intent otherActivity = new Intent(getApplicationContext(), ProgressionActivity.class);
                startActivity(otherActivity);
                overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);
                finish();
            }
        });

        Button option_button = (Button) findViewById(R.id.button_option);

        option_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sound_check.equals("on")) {
                    click_sound.start();
                }
                Intent otherActivity = new Intent(getApplicationContext(), OptionActivity.class);
                startActivity(otherActivity);
                overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);
                finish();
            }
        });
    }

    private void saveSomeValues() throws FileNotFoundException {
        File path = getApplicationContext().getExternalFilesDir("");
        File file  = new File(path, "save_game_clicker.txt");
        FileOutputStream writer = new FileOutputStream(file, true);
        try {
            String str = "0\n"; //Ligne 0
            writer.write(str.getBytes());
            str = "0\n"; //Ligne 1
            writer.write(str.getBytes());
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Sauvegarde réussie", Toast.LENGTH_SHORT).show();
    }

    private void saveDefaultValues() throws FileNotFoundException {
        File path = getApplicationContext().getExternalFilesDir("");
        File file  = new File(path, "save_data_clicker.txt");
        FileOutputStream writer = new FileOutputStream(file, true);
        try {
            String str = "\n"; //Ligne 0
            writer.write(str.getBytes());
            str = "red\n"; //Ligne 1
            writer.write(str.getBytes());
            str = "30\n"; //Ligne 2
            writer.write(str.getBytes());
            str = "normal\n"; //Ligne 3
            writer.write(str.getBytes());
            str = "on\n"; //Ligne 4
            writer.write(str.getBytes());
            str = "clair\n"; //Ligne 5
            writer.write(str.getBytes());
            str = "switch_false\n"; //Ligne 6 theme
            writer.write(str.getBytes());
            str = "switch_false\n"; //Ligne 7 sons
            writer.write(str.getBytes());
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Sauvegarde réussie", Toast.LENGTH_SHORT).show();
    }

    private void setOption(){
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

        //Set theme
        if (res.get(4).equals("clair")){
            view.setBackgroundResource(R.color.white); //Set theme white
        }else if (res.get(4).equals("sombre")){
            view.setBackgroundResource(R.color.black); //Set theme black
        }
        sound_check = res.get(3).toString();
    }
}