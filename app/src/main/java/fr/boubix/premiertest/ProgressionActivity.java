package fr.boubix.premiertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ProgressionActivity extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series;
    private File file;
    private ArrayList res = new ArrayList<String>();
    private View view;
    private TextView text;
    private ImageView back_button;
    private GraphView graph;
    private String sound_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progression2);

        view = this.getWindow().getDecorView();
        MediaPlayer back_sound = MediaPlayer.create(this, R.raw.back_sound);
        text = (TextView) findViewById(R.id.progression_text);
        back_button = (ImageView) findViewById(R.id.button_back_progression);

        setOption();

        File path = getApplicationContext().getExternalFilesDir("");
        file  = new File(path, "save_game_clicker.txt");

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


        double x, y;
        x = 0;
        y = 0;

        graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i < res.size() - 1; i++){
            x += 1;
            series.appendData(new DataPoint(x, Integer.parseInt((String) res.get(i))), true, res.size() - 1);
        }
        graph.addSeries(series);

        ImageView back = (ImageView) findViewById(R.id.button_back_progression);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sound_check.equals("on")) {
                    back_sound.start();
                }
                Intent nextActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(nextActivity);
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
            text.setTextColor(0xff000000);
        }else if (res.get(4).equals("sombre")){
            view.setBackgroundResource(R.color.black); //Set theme black
            text.setTextColor(0xffffffff);
            back_button.setColorFilter(0xffffffff);
        }
        sound_check = res.get(3).toString();
    }
}