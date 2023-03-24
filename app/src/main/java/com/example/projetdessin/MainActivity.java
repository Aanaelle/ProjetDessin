package com.example.projetdessin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void nouveauDessin(View view)
    {
        Intent intent = new Intent(this, DessinActivity.class);
        startActivity(intent);
    }

    public void reprendreDessin(View view)
    {
        Intent intent = new Intent(this, DessinActivity.class);
        startActivity(intent);
    }

    public void quitter(View view)
    {
        finish();
    }

}