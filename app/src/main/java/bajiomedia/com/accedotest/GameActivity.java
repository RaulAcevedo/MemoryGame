package bajiomedia.com.accedotest;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

import bajiomedia.com.accedotest.adapter.GameCardAdapter;
import bajiomedia.com.accedotest.model.ScoreDatabaseHelper;
import bajiomedia.com.accedotest.model.ScoreDetail;

public class GameActivity extends AppCompatActivity {

    private int currentScore;
    private TextView scoreTextView;
    private GameCardAdapter.CardDescriptor overCard,matchCard;
    private View cardOver,cardMatch,winPanel;
    private GridView gameGrid ;
    private ArrayList<GameCardAdapter.CardDescriptor> solved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        scoreTextView = (TextView)findViewById(R.id.score_text);
        gameGrid =  (GridView)findViewById(R.id.game_card_holder);
        winPanel = findViewById(R.id.winPanel);

        ((Button)findViewById(R.id.highscoresButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHighScores();
            }
        });
        startGame();
    }

    private void startGame(){
        currentScore = 0;
        solved = new ArrayList<>();

        scoreTextView.setText("" + currentScore);
        overCard = null;
        matchCard = null;

        winPanel.setVisibility(View.GONE);
        gameGrid.setAdapter(new GameCardAdapter(this));
    }


    private void onHighScores(){
        Intent intent = new Intent(this,ScoreActivity.class);
        startActivity(intent);
    }

    public void onPlayAgain(View pTarget){
        hideSoftKeyboard();
        startGame();
    }

    public void onSaveScore(View pTarget){
        EditText editT = (EditText)findViewById(R.id.nameInput);
        if(editT.getText().toString().length() >0)
        {
         try{
             ScoreDetail scoreD = new ScoreDetail();
             scoreD.setName(editT.getText().toString());
             scoreD.setScore(currentScore);
             ScoreDatabaseHelper dbHelper = OpenHelperManager.getHelper(this,ScoreDatabaseHelper.class);
             Dao<ScoreDetail, Long> credentialDao = dbHelper.getDao();
             credentialDao.create(scoreD);
             Toast.makeText(this,getResources().getString(R.string.score_saved),Toast.LENGTH_SHORT).show();
             startGame();
             editT.setText("");
             hideSoftKeyboard();
         }catch(SQLException e){
             e.printStackTrace();
         }

        }else{
            Toast.makeText(this,getResources().getString(R.string.name_error),Toast.LENGTH_SHORT).show();
        }

    }


    public void cardClick(View pTarget){
        GameCardAdapter.CardDescriptor descript;
        if(matchCard != null && overCard != null) return;
        if(pTarget.getTag() != null)
        {
            if(solved.indexOf(pTarget.getTag()) != -1)return;
            descript = (GameCardAdapter.CardDescriptor)pTarget.getTag();
            if(overCard == descript){
                return;
            }

            if(!descript.isFlip()){
                flipCard(pTarget);
                descript.setFlip(true);
            }
            if(overCard == null)
            {
                overCard = descript;
                cardOver = pTarget;
            }else{
                cardMatch = pTarget;
                matchCard = descript;
                if (overCard.getCode() == matchCard.getCode())
                {
                    Toast.makeText(this,"Good Job!",Toast.LENGTH_SHORT).show();
                    flipBack(true);
                    currentScore += 2;
                    solved.add(overCard);
                    solved.add(matchCard);
                    if (solved.size() >= gameGrid.getAdapter().getCount())
                    {
                        winGame();
                    }
                }else {
                    Toast.makeText(this,"Oops!",Toast.LENGTH_SHORT).show();
                    flipBack(false);
                    currentScore -= 1;
                }
                scoreTextView.setText("" + currentScore);
            }
        }

    }

    private void winGame(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setDuration(250);
                fadeIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        winPanel.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                winPanel.startAnimation(fadeIn);
            }
        }, 2500);

    }

    private void flipCard(View pView){
        ImageView symbolImage = (ImageView)pView.findViewById(R.id.card_symbol);
        ImageView backImage = (ImageView)pView.findViewById(R.id.card_back);
        AnimatorSet inAnim,outAnim;
        inAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.card_flip_left_in);
        outAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.card_flip_left_out);
        outAnim.setTarget(backImage);
        inAnim.setTarget(symbolImage);
        outAnim.start();
        inAnim.start();
    }

    private void flipBackCard(View pView){
        AnimatorSet inAnim,outAnim;
        ImageView symbolImage = (ImageView)pView.findViewById(R.id.card_symbol);
        ImageView backImage = (ImageView)pView.findViewById(R.id.card_back);
        inAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.card_flip_left_in);
        outAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.card_flip_left_out);
        outAnim.setTarget(symbolImage);
        inAnim.setTarget(backImage);
        outAnim.start();
        inAnim.start();

    }

    private void flipBack(boolean removeCards){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                flipBackCard(cardOver);
                flipBackCard(cardMatch);
                overCard.setFlip(false);
                matchCard.setFlip(false);
                overCard = null;
                matchCard = null;
            }
        }, 1500);


        if(removeCards){
          handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation fadeOut = new AlphaAnimation(1, 0);
                    fadeOut.setDuration(250);
                    fadeOut.setFillAfter(true);
                    cardOver.startAnimation(fadeOut);
                    cardMatch.startAnimation(fadeOut);
                }
            },2100);
        }
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }



}
