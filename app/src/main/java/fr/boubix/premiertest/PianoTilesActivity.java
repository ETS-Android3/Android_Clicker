package fr.boubix.premiertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.ColorFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class PianoTilesActivity extends AppCompatActivity {
    private TextView scoreText;
    private TextView pointsText;
    private ImageView piano_button_left_1;
    private ImageView piano_button_left_2;
    private ImageView piano_button_left_3;
    private ImageView piano_button_left_4;
    private ImageView piano_button_middle_left_1;
    private ImageView piano_button_middle_left_2;
    private ImageView piano_button_middle_left_3;
    private ImageView piano_button_middle_left_4;
    private ImageView piano_button_middle_right_1;
    private ImageView piano_button_middle_right_2;
    private ImageView piano_button_middle_right_3;
    private ImageView piano_button_middle_right_4;
    private ImageView piano_button_right_1;
    private ImageView piano_button_right_2;
    private ImageView piano_button_right_3;
    private ImageView piano_button_right_4;
    private ImageView etoile_1;
    private ImageView etoile_2;
    private ImageView etoile_3;
    private ImageView etoile_4;
    private ImageView etoile_5;
    private int speed;
    private int counterTime;
    private int counter;
    private int pts;
    private String difficulte;
    private String soundCheck;
    private MediaPlayer clic_sound;
    private MediaPlayer starSound;
    private boolean etoile1Checked;
    private boolean etoile2Checked;
    private boolean etoile3Checked;
    private boolean etoile4Checked;
    private boolean etoile5Checked;
    private int[] tabPosition;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano_tiles);

        initialisationImageView();
        scoreText = (TextView) findViewById(R.id.piano_time);
        pointsText = (TextView) findViewById(R.id.piano_text_score);
        clic_sound = MediaPlayer.create(this, R.raw.clic_ball_sound);
        starSound = MediaPlayer.create(this, R.raw.etoile_sound);

        tabPosition = new int[12];
        tabPosition[0] = -390;
        for (int i = 1; i < 12; i++){
            tabPosition[i] = tabPosition[i - 1] - 390;
        }

        getData();
        setEtoile();
        setDifficulte();

        new CountDownTimer(counterTime*1000, 1){
            public void onTick(long millisUntilFinished){
                scoreText.setText("Temps : " + String.valueOf(counterTime - counter/25));

                checkButton(piano_button_left_1);
                checkButton(piano_button_left_2);
                checkButton(piano_button_left_3);
                checkButton(piano_button_left_4);
                checkButton(piano_button_middle_left_1);
                checkButton(piano_button_middle_left_2);
                checkButton(piano_button_middle_left_3);
                checkButton(piano_button_middle_left_4);
                checkButton(piano_button_middle_right_1);
                checkButton(piano_button_middle_right_2);
                checkButton(piano_button_middle_right_3);
                checkButton(piano_button_middle_right_4);
                checkButton(piano_button_right_1);
                checkButton(piano_button_right_2);
                checkButton(piano_button_right_3);
                checkButton(piano_button_right_4);

                pointsText.setText("Points : " + pts);
                setSpeedButton();
                checkButtonPosition();
                checkEtoile();
                counter ++;
            }

            public void onFinish(){
                if (counter >= 10) {
                    Intent nextActivity = new Intent(getApplicationContext(), EndPianoActivity.class);
                    try {
                        saveGame();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    nextActivity.putExtra("points_piano_tiles", String.valueOf(pts));
                    startActivity(nextActivity);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    finish();
                }
            }
        }.start();
    }

    public void saveGame() throws FileNotFoundException {
        File path = getApplicationContext().getExternalFilesDir("");
        File file  = new File(path, "save_game_piano_tiles_" + String.valueOf(counterTime) + ".txt");
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
            String str = String.valueOf(pts) + "\n"; //Ligne 0
            writer.write(str.getBytes());
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "Sauvegarde réussie", Toast.LENGTH_SHORT).show();
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
        counterTime = Integer.parseInt(res2.get(1).toString());
        difficulte = res2.get(2).toString();
    }

    public void setDifficulte(){
        if (difficulte.equals("facile")){
            speed = 8;
        } else if (difficulte.equals("normal")){
            speed = 12;
        } else if (difficulte.equals("difficile")){
            speed = 18;
        }
    }

    public void checkButton(ImageView bt){
        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (soundCheck.equals("on")){
                    clic_sound.start();
                }
                bt.setImageResource(R.drawable.pinao_tiles_button_pressed);
                pts++;
            }
        });
    }

    public void onBackPressed(){
        Intent nextActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextActivity);
        overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);
        finish();
    }

    private void setSpeedButton(){
        //Left
        piano_button_left_1.setY(piano_button_left_1.getY() + speed);
        piano_button_left_2.setY(piano_button_left_2.getY() + speed);
        piano_button_left_3.setY(piano_button_left_3.getY() + speed);
        piano_button_left_4.setY(piano_button_left_4.getY() + speed);

        //Right
        piano_button_right_1.setY(piano_button_right_1.getY() + speed);
        piano_button_right_2.setY(piano_button_right_2.getY() + speed);
        piano_button_right_3.setY(piano_button_right_3.getY() + speed);
        piano_button_right_4.setY(piano_button_right_4.getY() + speed);


        //Middle left
        piano_button_middle_left_1.setY(piano_button_middle_left_1.getY() + speed);
        piano_button_middle_left_2.setY(piano_button_middle_left_2.getY() + speed);
        piano_button_middle_left_3.setY(piano_button_middle_left_3.getY() + speed);
        piano_button_middle_left_4.setY(piano_button_middle_left_4.getY() + speed);

        //Middle right
        piano_button_middle_right_1.setY(piano_button_middle_right_1.getY() + speed);
        piano_button_middle_right_2.setY(piano_button_middle_right_2.getY() + speed);
        piano_button_middle_right_3.setY(piano_button_middle_right_3.getY() + speed);
        piano_button_middle_right_4.setY(piano_button_middle_right_4.getY() + speed);
    }

    private void checkButtonPosition(){
        Random rand = new Random();

        //Left
        if (piano_button_left_1.getY() > 2340 && piano_button_left_2.getY() > 0){
            piano_button_left_1.setY(rand.nextInt(((int)piano_button_left_2.getY() - (int)piano_button_left_2.getY() - 390) + 1170) - 1170);
            if ((piano_button_left_1.getY() + 390) > piano_button_left_2.getY()){
                piano_button_left_1.setY(piano_button_left_1.getY() - 390);
            }
            piano_button_left_1.setImageResource(R.drawable.piano_tiles_button);
        }
        if (piano_button_left_2.getY() > 2340 && piano_button_left_3.getY() > 0){
            piano_button_left_2.setY(rand.nextInt(((int)piano_button_left_3.getY() - (int)piano_button_left_3.getY() - 390) + 1170) - 1170);
            if ((piano_button_left_2.getY() + 390) > piano_button_left_3.getY()){
                piano_button_left_2.setY(piano_button_left_2.getY() - 390);
            }
            piano_button_left_2.setImageResource(R.drawable.piano_tiles_button);
        }
        if (piano_button_left_3.getY() > 2340 && piano_button_left_4.getY() > 0){
            piano_button_left_3.setY(rand.nextInt(((int)piano_button_left_4.getY() - (int)piano_button_left_4.getY() - 390) + 1170) - 1170);
            if ((piano_button_left_3.getY() + 390) > piano_button_left_4.getY()){
                piano_button_left_3.setY(piano_button_left_3.getY() - 390);
            }
            piano_button_left_3.setImageResource(R.drawable.piano_tiles_button);
        }
        if (piano_button_left_4.getY() > 2340 && piano_button_left_1.getY() > 0){
            piano_button_left_4.setY(rand.nextInt(((int)piano_button_left_1.getY() - (int)piano_button_left_1.getY() - 390) + 1170) - 1170);
            if ((piano_button_left_4.getY() + 390) > piano_button_left_1.getY()){
                piano_button_left_4.setY(piano_button_left_4.getY() - 390);
            }
            piano_button_left_4.setImageResource(R.drawable.piano_tiles_button);
        }

        //Middle left
        if (piano_button_middle_left_1.getY() > 2340 && piano_button_middle_left_2.getY() > 0){
            piano_button_middle_left_1.setY(rand.nextInt(((int)piano_button_middle_left_2.getY() - (int)piano_button_middle_left_2.getY() - 390) + 1170) - 1170);
            if ((piano_button_middle_left_1.getY() + 390) > piano_button_middle_left_2.getY()){
                piano_button_middle_left_1.setY(piano_button_middle_left_1.getY() - 390);
            }
            piano_button_middle_left_1.setImageResource(R.drawable.piano_tiles_button);
        }
        if (piano_button_middle_left_2.getY() > 2340 && piano_button_middle_left_3.getY() > 0){
            piano_button_middle_left_2.setY(rand.nextInt(((int)piano_button_middle_left_3.getY() - (int)piano_button_middle_left_3.getY() - 390) + 1170) - 1170);
            if ((piano_button_middle_left_2.getY() + 390) > piano_button_middle_left_3.getY()){
                piano_button_middle_left_2.setY(piano_button_middle_left_2.getY() - 390);
            }
            piano_button_middle_left_2.setImageResource(R.drawable.piano_tiles_button);
        }
        if (piano_button_middle_left_3.getY() > 2340 && piano_button_middle_left_4.getY() > 0){
            piano_button_middle_left_3.setY(rand.nextInt(((int)piano_button_middle_left_4.getY() - (int)piano_button_middle_left_4.getY() - 390) + 1170) - 1170);
            if ((piano_button_middle_left_3.getY() + 390) > piano_button_middle_left_4.getY()){
                piano_button_middle_left_3.setY(piano_button_middle_left_3.getY() - 390);
            }
            piano_button_middle_left_3.setImageResource(R.drawable.piano_tiles_button);
        }
        if (piano_button_middle_left_4.getY() > 2340 && piano_button_middle_left_1.getY() > 0){
            piano_button_middle_left_4.setY(rand.nextInt(((int)piano_button_middle_left_1.getY() - (int)piano_button_middle_left_1.getY() - 390) + 1170) - 1170);
            if ((piano_button_middle_left_4.getY() + 390) > piano_button_middle_left_1.getY()){
                piano_button_middle_left_4.setY(piano_button_middle_left_4.getY() - 390);
            }
            piano_button_middle_left_4.setImageResource(R.drawable.piano_tiles_button);
        }

        //Middle right
        if (piano_button_middle_right_1.getY() > 2340 && piano_button_middle_right_2.getY() > 0){
            piano_button_middle_right_1.setY(rand.nextInt(((int)piano_button_middle_right_2.getY() - (int)piano_button_middle_right_2.getY() - 390) + 1170) - 1170);
            if ((piano_button_middle_right_1.getY() + 390) > piano_button_middle_right_2.getY()){
                piano_button_middle_right_1.setY(piano_button_middle_right_1.getY() - 390);
            }
            piano_button_middle_right_1.setImageResource(R.drawable.piano_tiles_button);
        }
        if (piano_button_middle_right_2.getY() > 2340 && piano_button_middle_right_3.getY() > 0){
            piano_button_middle_right_2.setY(rand.nextInt(((int)piano_button_middle_right_3.getY() - (int)piano_button_middle_right_3.getY() - 390) + 1170) - 1170);
            if ((piano_button_middle_right_2.getY() + 390) > piano_button_middle_right_3.getY()){
                piano_button_middle_right_2.setY(piano_button_middle_right_2.getY() - 390);
            }
            piano_button_middle_right_2.setImageResource(R.drawable.piano_tiles_button);
        }
        if (piano_button_middle_right_3.getY() > 2340 && piano_button_middle_right_4.getY() > 0){
            piano_button_middle_right_3.setY(rand.nextInt(((int)piano_button_middle_right_4.getY() - (int)piano_button_middle_right_4.getY() - 390) + 1170) - 1170);
            if ((piano_button_middle_right_3.getY() + 390) > piano_button_middle_right_4.getY()){
                piano_button_middle_right_3.setY(piano_button_middle_right_3.getY() - 390);
            }
            piano_button_middle_right_3.setImageResource(R.drawable.piano_tiles_button);
        }
        if (piano_button_middle_right_4.getY() > 2340 && piano_button_middle_right_1.getY() > 0){
            piano_button_middle_right_4.setY(rand.nextInt(((int)piano_button_middle_right_1.getY() - (int)piano_button_middle_right_1.getY() - 390) + 1170) - 1170);
            if ((piano_button_middle_right_4.getY() + 390) > piano_button_middle_right_1.getY()){
                piano_button_middle_right_4.setY(piano_button_middle_right_4.getY() - 390);
            }
            piano_button_middle_right_4.setImageResource(R.drawable.piano_tiles_button);
        }

        //Right
        if (piano_button_right_1.getY() > 2340 && piano_button_right_2.getY() > 0){
            piano_button_right_1.setY(rand.nextInt(((int)piano_button_right_2.getY() - (int)piano_button_right_2.getY() - 390) + 1170) - 1170);
            if ((piano_button_right_1.getY() + 390) > piano_button_right_2.getY()){
                piano_button_right_1.setY(piano_button_right_1.getY() - 390);
            }
            piano_button_right_1.setImageResource(R.drawable.piano_tiles_button);
        }
        if (piano_button_right_2.getY() > 2340 && piano_button_right_3.getY() > 0){
            piano_button_right_2.setY(rand.nextInt(((int)piano_button_right_3.getY() - (int)piano_button_right_3.getY() - 390) + 1170) - 1170);
            if ((piano_button_right_2.getY() + 390) > piano_button_right_3.getY()){
                piano_button_right_2.setY(piano_button_right_2.getY() - 390);
            }
            piano_button_right_2.setImageResource(R.drawable.piano_tiles_button);
        }
        if (piano_button_right_3.getY() > 2340 && piano_button_right_4.getY() > 0){
            piano_button_right_3.setY(rand.nextInt(((int)piano_button_right_4.getY() - (int)piano_button_right_4.getY() - 390) + 1170) - 1170);
            if ((piano_button_right_3.getY() + 390) > piano_button_right_3.getY()){
                piano_button_right_3.setY(piano_button_right_4.getY() - 390);
            }
            piano_button_right_3.setImageResource(R.drawable.piano_tiles_button);
        }
        if (piano_button_right_4.getY() > 2340 && piano_button_right_1.getY() > 0){
            piano_button_right_4.setY(rand.nextInt(((int)piano_button_right_1.getY() - (int)piano_button_right_1.getY() - 390) + 1170) - 1170);
            if ((piano_button_right_4.getY() + 390) > piano_button_right_1.getY()){
                piano_button_right_4.setY(piano_button_right_4.getY() - 390);
            }
            piano_button_right_4.setImageResource(R.drawable.piano_tiles_button);
        }
    }

    private void checkEtoile(){
        if (pts > counterTime/3){
            if (soundCheck.equals("on") && !etoile1Checked){
                starSound.start();
                etoile1Checked = true;
            }
            etoile_1.setColorFilter(0x00000000);
        }
        if (pts > counterTime){
            if (soundCheck.equals("on") && !etoile2Checked){
                starSound.start();
                etoile2Checked = true;
            }
            etoile_2.setColorFilter(0x00000000);
        }
        if (pts > counterTime*1.5){
            if (soundCheck.equals("on") && !etoile3Checked){
                starSound.start();
                etoile3Checked = true;
            }
            etoile_3.setColorFilter(0x00000000);
        }
        if (pts > counterTime*2){
            if (soundCheck.equals("on") && !etoile4Checked){
                starSound.start();
                etoile4Checked = true;
            }
            etoile_4.setColorFilter(0x00000000);
        }
        if (pts > counterTime*3){
            if (soundCheck.equals("on") && !etoile5Checked){
                starSound.start();
                etoile5Checked = true;
            }
            etoile_5.setColorFilter(0x00000000);
        }
    }

    private void setEtoile(){
        etoile_1.setColorFilter(0xff000000);
        etoile_2.setColorFilter(0xff000000);
        etoile_3.setColorFilter(0xff000000);
        etoile_4.setColorFilter(0xff000000);
        etoile_5.setColorFilter(0xff000000);

        etoile1Checked = false;
        etoile2Checked = false;
        etoile3Checked = false;
        etoile4Checked = false;
        etoile5Checked = false;
    }

    private void initialisationImageView(){
        piano_button_left_1 = (ImageView) findViewById(R.id.piano_button_left_1);
        piano_button_left_2 = (ImageView) findViewById(R.id.piano_button_left_2);
        piano_button_left_3 = (ImageView) findViewById(R.id.piano_button_left_3);
        piano_button_left_4 = (ImageView) findViewById(R.id.piano_button_left_4);
        piano_button_middle_left_1 = (ImageView) findViewById(R.id.piano_button_middle_left_1);
        piano_button_middle_left_2 = (ImageView) findViewById(R.id.piano_button_middle_left_2);
        piano_button_middle_left_3 = (ImageView) findViewById(R.id.piano_button_middle_left_3);
        piano_button_middle_left_4 = (ImageView) findViewById(R.id.piano_button_middle_left_4);
        piano_button_middle_right_1 = (ImageView) findViewById(R.id.piano_button_middle_right_1);
        piano_button_middle_right_2 = (ImageView) findViewById(R.id.piano_button_middle_right_2);
        piano_button_middle_right_3 = (ImageView) findViewById(R.id.piano_button_middle_right_3);
        piano_button_middle_right_4 = (ImageView) findViewById(R.id.piano_button_middle_right_4);
        piano_button_right_1 = (ImageView) findViewById(R.id.piano_button_right_1);
        piano_button_right_2 = (ImageView) findViewById(R.id.piano_button_right_2);
        piano_button_right_3 = (ImageView) findViewById(R.id.piano_button_right_3);
        piano_button_right_4 = (ImageView) findViewById(R.id.piano_button_right_4);

        etoile_1 = (ImageView) findViewById(R.id.star_1);
        etoile_2 = (ImageView) findViewById(R.id.star_2);
        etoile_3 = (ImageView) findViewById(R.id.star_3);
        etoile_4 = (ImageView) findViewById(R.id.star_4);
        etoile_5 = (ImageView) findViewById(R.id.star_5);
    }
}