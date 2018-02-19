package com.toma.dicegame.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import static com.toma.dicegame.utils.Constants.DICE_QUANTITY;
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
    private String message;
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

            throwDices();
            dice.clear();
            addDicesToList();
            setImageToDiceValue();

            if (dice1 == dice2 && dice1 == dice3){
                int scoreDelta = dice1 * 100;
                message = "You rolled a triple " + dice1 + "! You scored " + scoreDelta + " points!";
                score += scoreDelta;
            } else if (dice1 == dice2 || dice1 == dice3 || dice2 == dice3){
                message = "You rollled doubles for 50 points!";
                score += 50;
            } else {
                message = "You didn't score this roll. Try again!";
            }

            rollResult.setText(message);
            totalScore.setText("Score: " + score);

        }
    };

    private void throwDices() {
        dice1 = throwDice();
        dice2 = throwDice();
        dice3 = throwDice();
    }

    private int throwDice (){
        return random.nextInt(MAX_DICE_VALUE) + 1;
    }

    private void addDicesToList() {
        dice.add(dice1);
        dice.add(dice2);
        dice.add(dice3);
    }

    @NonNull
    private String getNameOfDiceImage(int dieInSet) {
        return "die" + dice.get(dieInSet) + ".png";
    }

    private void setImageToDiceValue() {
        for (int dieInSet = 0; dieInSet < DICE_QUANTITY; dieInSet++) {
            String imageName = getNameOfDiceImage(dieInSet);

            try {
                InputStream stream = getAssets().open(imageName);
                Drawable drawable = Drawable.createFromStream(stream,null);
                diceImages.get(dieInSet).setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
