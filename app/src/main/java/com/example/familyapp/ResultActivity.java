package com.example.familyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mGrade, mFinalScore;
    Button mRetryButton, backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mGrade = (TextView)findViewById(R.id.grade);
        mFinalScore = (TextView)findViewById(R.id.outOf);
        mRetryButton = (Button)findViewById(R.id.retry);
        backButton=findViewById(R.id.back);

        Bundle bundle = getIntent().getExtras();
        int score = bundle.getInt("finalScore");

        mFinalScore.setText("You scored " + score + " out of " + QuizBook.questions.length);

        if (score == 9){
            mGrade.setText("Outstanding");
        }else if (score == 8){
            mGrade.setText("Good Work");
        }else if (score == 7) {
            mGrade.setText("Good Effort");
        }else {
            mGrade.setText("Go over your notes");
        }
        backButton.setOnClickListener(this);
       mRetryButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v==mRetryButton){
            startActivity(new Intent(ResultActivity.this, Wouldlie.class));
            ResultActivity.this.finish();
        }
        else if(v==backButton){
            startActivity(new Intent(ResultActivity.this, Home.class));
            ResultActivity.this.finish();
        }
    }
}
