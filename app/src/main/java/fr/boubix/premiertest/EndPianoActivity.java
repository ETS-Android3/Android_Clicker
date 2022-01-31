package fr.boubix.premiertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class EndPianoActivity extends AppCompatActivity {

    private ArrayList res = new ArrayList<String>();
    private TextView percent;
    private String soundCheck;
    private String themeCheck;
    private TextView textScore;
    private View view;
    private TextView score;
    private int counterTime;
    private int pts;
    private boolean etoile1Checked;
    private boolean etoile2Checked;
    private boolean etoile3Checked;
    private boolean etoile4Checked;
    private boolean etoile5Checked;
    private ImageView etoile_1;
    private ImageView etoile_2;
    private ImageView etoile_3;
    private ImageView etoile_4;
    private ImageView etoile_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_piano);

        textScore = (TextView) findViewById(R.id.score_piano);
        score = (TextView) findViewById(R.id.number_of_points_piano);
        view = this.getWindow().getDecorView();
        MediaPlayer clicButton = MediaPlayer.create(this, R.raw.button_sound);

        getData();
        setEtoile();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            score.setText(extras.getString("points_piano_tiles"));
            pts = Integer.parseInt(extras.getString("points_piano_tiles"));
            System.out.println("Score piano : " + extras.getString("points_piano_tiles"));
        }

        checkEtoile();

        Button button = (Button) findViewById(R.id.menu_principal_piano);

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

    private void setEtoile(){
        etoile_1 = (ImageView) findViewById(R.id.etoile_end_1);
        etoile_2 = (ImageView) findViewById(R.id.etoile_end_2);
        etoile_3 = (ImageView) findViewById(R.id.etoile_end_3);
        etoile_4 = (ImageView) findViewById(R.id.etoile_end_4);
        etoile_5 = (ImageView) findViewById(R.id.etoile_end_5);

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

    private void checkEtoile(){
        if (pts > counterTime/3){
            etoile_1.setColorFilter(0x00000000);
        }
        if (pts > counterTime){
            etoile_2.setColorFilter(0x00000000);
        }
        if (pts > counterTime*1.5){
            etoile_3.setColorFilter(0x00000000);
        }
        if (pts > counterTime*2){
            etoile_4.setColorFilter(0x00000000);
        }
        if (pts > counterTime*3){
            etoile_5.setColorFilter(0x00000000);
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

        counterTime = Integer.parseInt(res2.get(1).toString());
        soundCheck = res2.get(3).toString();
        themeCheck = res2.get(4).toString();

        setThemeDark();
        setThemeLight();
    }

    private void setThemeLight(){
        if (themeCheck.equals("clair")){
            score.setTextColor(0xff000000);
            textScore.setTextColor(0xff000000);
            view.setBackgroundColor(0xffffffff);
        }
    }

    private void setThemeDark(){
        System.out.println("Theme : " + themeCheck);
        if (themeCheck.equals("sombre")){
            score.setTextColor(0xffffffff);
            textScore.setTextColor(0xffffffff);
            view.setBackgroundColor(0xff000000);
        }
    }
}