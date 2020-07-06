package com.example.alquran;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    Button btnMushaf, btnPerayat, btnDetail, btnPencarian, btnBookmarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btnMushaf = findViewById(R.id.btn1);
        btnPerayat = findViewById(R.id.btn2);
        //btnDetail = findViewById(R.id.btn3);
        //btnPencarian = findViewById(R.id.btn4);
        //btnBookmarks = findViewById(R.id.btn5);

        btnPerayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuranPerAyat.class);
                startActivity(intent);
            }
        });
    }
}