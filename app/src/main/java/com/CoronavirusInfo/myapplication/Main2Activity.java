package com.CoronavirusInfo.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
    }
    public void go1(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    public void go2(View view){
        Intent intent = new Intent(this,Main3Activity.class);
        startActivity(intent);

    }

}

