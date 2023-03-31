package com.example.projetdessin;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class DessinActivity extends AppCompatActivity
{
    private ZoneDessin zoneDessin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // = new ZoneDessin(this);

        setContentView(R.layout.activity_dessin);
        zoneDessin = new ZoneDessin(this);
        LinearLayout layout = findViewById(R.id.dessin_layout);
        layout.addView(zoneDessin);
    }

    public void quitter(View view)
    {
        finish();
    }

    public void effacer(View view)
    {
        zoneDessin.effacer();
    }

    public void choisirCouleur(View view)
    {
        ImageButton source = (ImageButton) view;
        int couleur = Color.BLACK;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            switch (source.getTag().toString()) {
                case "rouge":
                    couleur = Color.RED;
                    break;
                case "bleu":
                    couleur = Color.BLUE;
                    break;
                case "vert":
                    couleur = Color.GREEN;
                    break;
                case "jaune":
                    couleur = Color.YELLOW;
                    break;
                case "noir":
                    couleur = Color.BLACK;
                    break;
                default:
                    break;
            }
        }

        zoneDessin.setColor(couleur);

    }

    public void carre(View view)
    {
        zoneDessin.setType("carre", false);
    }

    public void carreFill(View view)
    {
        zoneDessin.setType("carre", true);
    }

    public void rond(View view)
    {
        zoneDessin.setType("cercle", false);
    }

    public void rondFill(View view)
    {
        zoneDessin.setType("cercle", true);
    }

    public void ligne(View view)
    {
        zoneDessin.setType("ligne", false);
    }

    public void undo(View view)
    {
        zoneDessin.undo();
    }


}