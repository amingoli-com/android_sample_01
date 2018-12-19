package com.example.ermile.android_sample_01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class nav_header extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header);

        TextView textView = findViewById(R.id.username);

        textView.setText("سلام محمد امین");

        

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "START", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "DESTROY", Toast.LENGTH_SHORT).show();
    }
}
