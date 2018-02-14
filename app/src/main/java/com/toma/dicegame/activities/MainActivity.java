package com.toma.dicegame.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.toma.dicegame.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.toma.dicegame.utils.Constants.MAX_DICE_VALUE;

public class MainActivity extends AppCompatActivity {

    private TextView rollResult;
    private TextView totalScore;
    private ImageView dice1Image;
    private ImageView dice2Image;
    private ImageView dice3Image;
    private ArrayList<ImageView> diceImages;
    private int dice1, dice2, dice3;
    private int score;
    private List<Integer> dice;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(rollDice);

        rollResult = findViewById(R.id.result_tv);
        totalScore = findViewById(R.id.score_tv);

        dice1Image = findViewById(R.id.dice_1_iv);
        dice2Image = findViewById(R.id.dice_2_iv);
        dice3Image = findViewById(R.id.dice_3_iv);

        diceImages = new ArrayList<>();

        diceImages.add(dice1Image);
        diceImages.add(dice2Image);
        diceImages.add(dice3Image);

        dice = new ArrayList<>();

        score = 0;

        random = new Random();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener rollDice = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dice1 = throwDice();
            dice2 = throwDice();
            dice3 = throwDice();

            dice.clear();
            dice.add(dice1);
            dice.add(dice2);
            dice.add(dice3);

            for (int dieOfSet = 0; dieOfSet < 3; dieOfSet++) {
                String imageName = "die" + dice.get(dieOfSet) + ".png";

                try {
                    InputStream stream = getAssets().open(imageName);
                    Drawable drawable = Drawable.createFromStream(stream,null);
                    diceImages.get(dieOfSet).setImageDrawable(drawable);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String msg = "You rolled a " + dice1 + " and " + dice2 + " and " + dice3;
            rollResult.setText(msg);


        }
    };

    private int throwDice (){
        return random.nextInt(MAX_DICE_VALUE) + 1;
    }
}
