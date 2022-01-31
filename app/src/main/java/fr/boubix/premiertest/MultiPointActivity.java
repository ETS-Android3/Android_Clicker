package fr.boubix.premiertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

public class MultiPointActivity extends AppCompatActivity {
    private View view;
    private TextView time;
    private int counter;
    private TextView points;
    private int pts = 0;
    private ImageView button1;
    private ImageView button2;
    private ImageView button3;
    private File file;
    private File path;
    private int counterTime;
    private String difficulte;
    private int coeff;
    private int size;
    private MediaPlayer clic_sound;
    private String sound_check;
    private int colorId;
    private String color;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_point);

        view = this.getWindow().getDecorView();
        this.time = (TextView) findViewById(R.id.time);
        this.points = (TextView) findViewById(R.id.points);

        setOptionValues();

        button1 = (ImageView) findViewById(R.id.button1);
        button2 = (ImageView) findViewById(R.id.button2);
        button3 = (ImageView) findViewById(R.id.button3);

        button1.setColorFilter(colorId);
        button2.setColorFilter(colorId);
        button3.setColorFilter(colorId);


        clic_sound = MediaPlayer.create(this, R.raw.clic_ball_sound);

        setDifficulte();


        new CountDownTimer(counterTime*1000, 100){
            public void onTick(long millisUntilFinished){
                time.setText("Temps : " + String.valueOf(counterTime - counter/10));

                button1.getLayoutParams().width -= coeff;
                button1.getLayoutParams().height -= coeff;
                checkButton(button1);

                button2.getLayoutParams().width -= coeff;
                button2.getLayoutParams().height -= coeff;
                checkButton(button2);

                button3.getLayoutParams().width -= coeff;
                button3.getLayoutParams().height -= coeff;
                checkButton(button3);

                counter++;
            }

            public void onFinish(){
                time.setText("FINISH!!");
                try {
                    saveInterne();
                } catch (IOException e) {
                    System.out.println("Erreur sauvegarde : ");
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (counter >= 10) {
                    Intent nextActivity = new Intent(getApplicationContext(), EndActivity.class);
                    nextActivity.putExtra("points_aim_clicker", pts);
                    nextActivity.putExtra("caller", "MultiPointActivity");
                    nextActivity.putExtra("fichier", path.toString());
                    startActivity(nextActivity);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    finish();
                }
            }
        }.start();
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
            view.setBackgroundResource(R.color.white); //Set theme white
            time.setTextColor(0xff000000);
            points.setTextColor(0xff000000);
        }else if (res.get(4).equals("sombre")){
            view.setBackgroundResource(R.color.black); //Set theme black
            time.setTextColor(0xffffffff);
            points.setTextColor(0xffffffff);
        }

        counterTime = Integer.parseInt(res.get(1).toString());
        difficulte = res.get(2).toString();
        sound_check = res.get(3).toString();

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
    }

    public void onBackPressed(){
        Intent nextActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextActivity);
        overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);
        finish();
    }

    public void checkButton(ImageView bt){
        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (sound_check.equals("on")) {
                    clic_sound.start();
                }
                Random rand = new Random();
                int left = rand.nextInt(900 - 0);
                int top = rand.nextInt(1900 - 0);

                bt.setX(left);
                bt.setY(top);
                bt.getLayoutParams().width = size;
                bt.getLayoutParams().height = size;

                pts++;
                points.setText("Points : " + pts);
            }
        });
        if (bt.getLayoutParams().height < 0 && bt.getLayoutParams().width < 0){
            Random rand = new Random();
            int left = rand.nextInt(900 - 0);
            int top = rand.nextInt(1900 - 0);

            bt.setX(left);
            bt.setY(top);
            bt.getLayoutParams().width = size;
            bt.getLayoutParams().height = size;

            points.setText("Points : " + pts);
        }
    }

    private void saveInterne() throws IOException {
        path = getApplicationContext().getExternalFilesDir("");
        file  = new File(path, "save_game_clicker.txt");
        FileOutputStream writer = new FileOutputStream(file, true);
        String str = String.valueOf(pts);
        writer.write(str.getBytes());
        String temp = "\n";
        writer.write(temp.getBytes());
        writer.close();
        Toast.makeText(getApplicationContext(), "Sauvegarde réussie", Toast.LENGTH_SHORT).show();
        System.out.println("Chemin : " + file);
    }

    private void setDifficulte(){
        if (difficulte.equals("facile")){
            coeff = 2;
            size = 300;
        } else if (difficulte.equals("normal")){
            coeff = 4;
            size = 200;
        } else if (difficulte.equals("difficile")){
            coeff = 4;
            size = 100;
        }
    }
}