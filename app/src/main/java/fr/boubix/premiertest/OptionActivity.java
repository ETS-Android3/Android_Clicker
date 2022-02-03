package fr.boubix.premiertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class OptionActivity extends AppCompatActivity {

    private String color;
    private String colorId;
    private String time;
    private String level;
    private String sound;
    private String theme;
    private String switchSoundPosition;

    private View viewMain;
    private Button colorButton;
    private EditText timeCibles;
    private Button levelButton;
    private Button themeButton;
    private Switch switchSound;
    private Button supprButton;
    private Switch switchTheme;
    private Switch switchThemePlanet;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    private TextView text6;
    private TextView text7;
    private TextView text8;
    private ImageView back;
    private TextView text9;
    private Button saveSupprButton;
    private String difficulte;
    private String sound_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        MediaPlayer back_sound = MediaPlayer.create(this, R.raw.back_sound);

        initialisationVariable();
        setOptionValues();

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (color.equals("red")){
                    colorButton.setText("Bleu");
                    color = "blue";
                    colorButton.setBackgroundColor(0xff00ffff);
                } else if (color.equals("blue")){
                    colorButton.setText("Vert");
                    color = "green";
                    colorButton.setBackgroundColor(0xff00ff00);
                } else if (color.equals("green")){
                    colorButton.setText("Jaune");
                    color = "yellow";
                    colorButton.setBackgroundColor(0xffffff00);
                } else if (color.equals("yellow")){
                    colorButton.setText("Rose");
                    color = "pink";
                    colorButton.setBackgroundColor(0xffff00ff);
                } else if (color.equals("pink")){
                    colorButton.setText("Rouge");
                    color = "red";
                    colorButton.setBackgroundColor(0xffff0000);
                }
                colorId = color;
            }
        });

        timeCibles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time = timeCibles.getText().toString();
                if (Integer.parseInt(time) > 120){
                    Toast.makeText(getApplicationContext(), "Erreur, entrez un nombre inférieur à 120 !", Toast.LENGTH_LONG).show();
                }
            }
        });

        levelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (difficulte.equals("facile")){
                    difficulte = "normal";
                    levelButton.setText("Normal");
                } else if (difficulte.equals("normal")) {
                    difficulte = "difficile";
                    levelButton.setText("Difficile");
                } else if (difficulte.equals("difficile")){
                    difficulte = "facile";
                    levelButton.setText("Facile");
                }
                level = difficulte;
            }
        });

        switchSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchSoundPosition.equals("switch_true")){
                    switchSound.setText("On");
                    sound = "On";
                    switchSoundPosition = "switch_false";
                } else if (switchSoundPosition.equals("switch_false")){
                    switchSound.setText("Off");
                    sound = "Off";
                    switchSoundPosition = "switch_true";
                }
            }
        });

        themeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (theme.equals("clair")){
                    themeButton.setText("Sombre");
                    theme = "sombre";
                    viewMain.setBackgroundColor(0xff000000);
                    setTexColorDark();
                } else if (theme.equals("sombre")){
                    themeButton.setText("Galaxie");
                    theme = "galaxie";
                    viewMain.setBackgroundResource(R.drawable.background);
                    setTexColorDark();
                } else if (theme.equals("galaxie")){
                    themeButton.setText("Clair");
                    theme = "clair";
                    viewMain.setBackgroundColor(0xffffffff);
                    setTexColorLight();
                }
            }
        });

        supprButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Supprimer le data txt
                File path = getApplicationContext().getExternalFilesDir("");
                File file  = new File(path, "save_data_clicker.txt");
                file.delete();
                setDefaultValues();
                Toast.makeText(getApplicationContext(), "Suppression réussie", Toast.LENGTH_SHORT).show();
            }
        });

        saveSupprButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Supprimer les save game txt
                File path = getApplicationContext().getExternalFilesDir("");
                File file;
                for (int i = 0; i < 120; i++){
                    file = new File(path, "save_game_clicker_" + String.valueOf(i) + ".txt");
                    if (file.exists()){
                        file.delete();
                    }

                    file = new File(path, "save_game_aim_clicker_" + String.valueOf(i) + ".txt");
                    if (file.exists()){
                        file.delete();
                    }

                    file = new File(path, "save_game_piano_tiles_" + String.valueOf(i) + ".txt");
                    if (file.exists()){
                        file.delete();
                    }
                }
                Toast.makeText(getApplicationContext(), "Suppression réussie", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sound_check.equals("on") && !switchSound.isChecked()) {
                    back_sound.start();
                }
                //Save data option
                try {
                    saveOptiontValues();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Intent nextActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(nextActivity);
                overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);
                finish();
            }
        });
    }

    private void initialisationVariable(){
        viewMain = this.getWindow().getDecorView();
        colorButton = (Button) findViewById(R.id.button_color);
        timeCibles = (EditText) findViewById(R.id.edit_cibles_time);
        levelButton = (Button) findViewById(R.id.button_cibles_level);
        switchSound = (Switch) findViewById(R.id.switch_sound);
        themeButton = (Button) findViewById(R.id.theme_option);
        supprButton = (Button) findViewById(R.id.button_delete_data);
        saveSupprButton = (Button) findViewById(R.id.button_delate_save);
        back = (ImageView) findViewById(R.id.back_button);
        text1 = (TextView) findViewById(R.id.textView1);
        text2 = (TextView) findViewById(R.id.textView2);
        text3 = (TextView) findViewById(R.id.textView3);
        text4 = (TextView) findViewById(R.id.textView4);
        text5 = (TextView) findViewById(R.id.textView5);
        text6 = (TextView) findViewById(R.id.textView6);
        text7 = (TextView) findViewById(R.id.textView7);
        text8 = (TextView) findViewById(R.id.textView8);
        text9=  (TextView) findViewById(R.id.text_delete_save);
    }

    @Override
    public void onBackPressed(){
        //Save data option
        try {
            saveOptiontValues();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Intent nextActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextActivity);
        overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);
        finish();
    }

    private void setDefaultValues(){
        //Bouton couleur
        colorButton.setText("Rouge");
        color = "red";
        colorButton.setBackgroundColor(0xffff0000);

        //Temps
        time = "30";

        //Difficulte
        difficulte = "normal";
        levelButton.setText("Normal");

        //Sons
        switchSound.setText("On");
        sound = "On";
        switchSoundPosition = "switch_false";
        switchSound.setChecked(false);

        //Theme
        themeButton.setText("Clair");
        theme = "clair";
        viewMain.setBackgroundResource(R.color.white);
        setTexColorLight();
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

        //Set theme
        if (res.get(4).equals("clair")){
            viewMain.setBackgroundResource(R.color.white); //Set theme white
            setTexColorLight();
        } else if (res.get(4).equals("sombre")){
            viewMain.setBackgroundResource(R.color.black); //Set theme black
            setTexColorDark();
        }else if (res.get(4).equals("galaxie")){
            viewMain.setBackgroundResource(R.drawable.background);
            setTexColorDark();
        }

        //Set sound
        if (res.get(5).equals("switch_false")){
            switchSound.setChecked(false);
            switchSound.setText("On");
        } else if (res.get(5).equals("switch_true")){
            switchSound.setText("Off");
            switchSound.setChecked(true);
        }
        switchSoundPosition = res.get(5).toString();


        color = res.get(0).toString();
        //Set color button
        if (color.equals("red")){
            colorButton.setText("Rouge");
            colorButton.setBackgroundColor(0xffff000);
        } else if (color.equals("blue")){
            colorButton.setText("Bleu");
            colorButton.setBackgroundColor(0xff00ffff);
        } else if (color.equals("green")){
            colorButton.setText("Vert");
            colorButton.setBackgroundColor(0xff00ff00);
        } else if (color.equals("yellow")){
            colorButton.setText("Jaune");
            colorButton.setBackgroundColor(0xffffff00);
        } else if (color.equals("pink")){
            colorButton.setText("Rose");
            colorButton.setBackgroundColor(0xffff00ff);
        }

        time = res.get(1).toString();
        difficulte = res.get(2).toString();
        levelButton.setText(difficulte);
        sound_check = res.get(3).toString();

        //Set bouton theme
        theme = res.get(4).toString();
        if (theme.equals("clair")){
            themeButton.setText("Clair");
        } else if (theme.equals("sombre")){
            themeButton.setText("Sombre");
        } else {
            themeButton.setText("Galaxie");
        }
    }

    private void saveOptiontValues() throws FileNotFoundException {
        File path = getApplicationContext().getExternalFilesDir("");
        File file  = new File(path, "save_data_clicker.txt");
        FileOutputStream writer = new FileOutputStream(file, false);
        try {
            String str = "\n"; //Ligne 0
            writer.write(str.getBytes());
            if (colorId == null){
                colorId = "red";
            }
            str = colorId + "\n"; //Ligne 1
            writer.write(str.getBytes());
            if (time == null){
                time = "30";
            }
            str = time + "\n"; //Ligne 2
            writer.write(str.getBytes());
            if (level == null){
                level = "normal";
            }
            str = level + "\n"; //Ligne 3
            writer.write(str.getBytes());
            if (sound == null){
                sound = "on";
            }
            if (switchSound.isChecked()){
                sound = "off";
            }
            if (!switchSound.isChecked()){
                sound = "on";
            }
            str = sound + "\n"; //Ligne 4
            writer.write(str.getBytes());
            if (theme == null){
                theme = "clair";
            }
            str = theme + "\n"; //Ligne 5
            writer.write(str.getBytes());
            if (switchSoundPosition == null){
                switchSoundPosition = "switch_false";
            }
            str = switchSoundPosition + "\n"; //Ligne 6
            writer.write(str.getBytes());
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Sauvegarde réussie", Toast.LENGTH_SHORT).show();
    }

    private void setTexColorDark(){
        colorButton.setTextColor(0xffffffff);
        timeCibles.setTextColor(0xffffffff);
        timeCibles.setBackgroundColor(0xffffffff);
        timeCibles.setTextColor(0xff000000);
        levelButton.setTextColor(0xffffffff);
        switchSound.setTextColor(0xffffffff);
        themeButton.setTextColor(0xff000000);
        supprButton.setTextColor(0xffffffff);
        saveSupprButton.setTextColor(0xffffffff);
        text1.setTextColor(0xffffffff);
        text2.setTextColor(0xffffffff);
        text3.setTextColor(0xffffffff);
        text4.setTextColor(0xffffffff);
        text5.setTextColor(0xffffffff);
        text6.setTextColor(0xffffffff);
        text7.setTextColor(0xffffffff);
        text8.setTextColor(0xffffffff);
        text9.setTextColor(0xffffffff);
        back.setColorFilter(0xffffffff);
    }

    private void setTexColorLight(){
        colorButton.setTextColor(0xff000000);
        timeCibles.setTextColor(0xffffffff);
        timeCibles.setBackgroundColor(0xff000000);
        levelButton.setTextColor(0xff000000);
        themeButton.setTextColor(0xff000000);
        switchSound.setTextColor(0xff000000);
        supprButton.setTextColor(0xff000000);
        saveSupprButton.setTextColor(0xff000000);
        back.setColorFilter(0xff000000);
        text1.setTextColor(0xff000000);
        text2.setTextColor(0xff000000);
        text3.setTextColor(0xff000000);
        text4.setTextColor(0xff000000);
        text5.setTextColor(0xff000000);
        text6.setTextColor(0xff000000);
        text7.setTextColor(0xff000000);
        text8.setTextColor(0xff000000);
        text9.setTextColor(0xff000000);
    }
}