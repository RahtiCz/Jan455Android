package com.example.jan455app;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private boolean isGameOn = false;
    private TextView timer, txtPlayer1, txtPlayer2;
    private Button btnStart, btnP1Plus, btnP1Minus, btnP2Plus, btnP2Minus;
    private CountDownTimer gameTimer;
    private ImageView remainingTime;
    private MediaPlayer soundPlayer;
    private long gameTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_game, container, false);

        btnStart = (Button) v.findViewById(R.id.btnGameStart);
        timer = (TextView) v.findViewById(R.id.gameTime) ;
        txtPlayer1 = (TextView) v.findViewById(R.id.txtPlayer1) ;
        txtPlayer2 = (TextView) v.findViewById(R.id.txtPlayer2) ;
        btnP1Minus = (Button) v.findViewById(R.id.buttonPlayer1Minus);
        btnP1Plus = (Button) v.findViewById(R.id.buttonPlayer1Plus);
        btnP2Plus = (Button) v.findViewById(R.id.buttonPlayer2Plus);
        btnP2Minus = (Button) v.findViewById(R.id.buttonPlayer2Minus);
        remainingTime = (ImageView) v.findViewById(R.id.remainTime);

        btnP1Plus.setOnClickListener(this::onGameButtonClick);
        btnP1Minus.setOnClickListener(this::onGameButtonClick);
        btnP2Minus.setOnClickListener(this::onGameButtonClick);
        btnP2Plus.setOnClickListener(this::onGameButtonClick);

        btnStart.setOnClickListener(this::onGameStartClick);

        if(savedInstanceState != null)
        {
            gameTime = savedInstanceState.getLong("GameTime");
            txtPlayer1.setText(savedInstanceState.getString("Player1Live"));
            txtPlayer2.setText(savedInstanceState.getString("Player2Live"));
            if(gameTime < 60000)
            {
                isGameOn = true;
                startTimer(gameTime);
            }
        }
        else
        {
            gameTime = 60000;
        }

        // Inflate the layout for this fragment
        return v;
    }

    public void onGameButtonClick(View v) {
        int value = 20;

        if(isGameOn) {
            switch (v.getId()) {

                case R.id.buttonPlayer1Minus:
                    value = Integer.parseInt(txtPlayer1.getText().toString())-1;

                    txtPlayer1.setText(Integer.toString(value));
                    break;
                case R.id.buttonPlayer2Minus:
                    value = Integer.parseInt(txtPlayer2.getText().toString())-1;
                    txtPlayer2.setText(Integer.toString(value));
                    break;
                case R.id.buttonPlayer1Plus:
                    value = Integer.parseInt(txtPlayer1.getText().toString())+1;
                     txtPlayer1.setText(Integer.toString(value));
                    break;
                case R.id.buttonPlayer2Plus:
                    value = Integer.parseInt(txtPlayer2.getText().toString()) + 1;
                    txtPlayer2.setText(Integer.toString(value));
                    break;
                default:
                    break;
            }

            if(value < 1){
                gameFinish();
                timer.setText(v.getId()==R.id.buttonPlayer1Minus ? "P2 WIN!" : "P1 WIN!");
            }
        }
    }

    public void onGameStartClick(View v) {
        //if running then not run
        if(!isGameOn) {
            timer.setText("35:00");
            txtPlayer1.setText("20");
            txtPlayer2.setText("20");
            isGameOn = true;
            startTimer(60000);
        }
    }

    private void startTimer(long initTime)
    {
        gameTimer = new CountDownTimer(initTime, 1000) {
            //ukladat si milisekundy + stav running do stavu at muze pokracovat po otoceni displaye
            public void onTick(long millisUntilFinished) {
                timer.setText(String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60));
                //Log.d("TEST", "onTick: " + String.valueOf(millisUntilFinished));
                if(millisUntilFinished> 30000 && millisUntilFinished< 31000)
                {
                    //reminder on 5 minutes to end
                    remainingTime.setVisibility(View.VISIBLE);
                    soundPlayer = MediaPlayer.create(getActivity(), R.raw.five_min_remain);
                    soundPlayer.start();
                }
                gameTime = millisUntilFinished;
            }
            public void onFinish() {
                //play sound on finish
                gameFinish();
                timer.setText("DRAW");
            }
        }.start();
    }

    private void gameFinish()
    {
        soundPlayer = MediaPlayer.create(getActivity(), R.raw.game_over);
        soundPlayer.start();
        remainingTime.setVisibility(View.INVISIBLE);
        isGameOn = false;
        gameTimer.cancel();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Player1Live", txtPlayer1.getText().toString());
        outState.putString("Player2Live", txtPlayer2.getText().toString());
        outState.putLong("GameTime",gameTime );
    }

    @Override
    public void onPause() {
        super.onPause();
        if(gameTimer != null)
            gameTimer.cancel();
    }
}