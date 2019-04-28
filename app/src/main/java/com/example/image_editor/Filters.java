package com.example.image_editor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Filters extends AppCompatActivity {

    private Bitmap bitmap;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        this.path = intent.getStringExtra("Image");

        configScalingButton();
        configColorFiltersButton();
    }

    private void configScalingButton(){
        Button scallingButton = (Button) findViewById(R.id.open_scaling);
        scallingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Filters.this, Scaling.class);
                intent.putExtra("Image", path);
                startActivity(intent);
            }
        });

    }

    private void configColorFiltersButton(){
        Button colorFiltersButton = (Button) findViewById(R.id.open_color_filters);
        colorFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Filters.this, Color_Filters.class);
                intent.putExtra("Image", path);
                startActivity(intent);
            }
        });

    }
}