package com.example.projetdessin;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DessinActivity extends AppCompatActivity
{
    private ZoneDessin zoneDessin;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dessin);

        boolean nouveau = getIntent().getBooleanExtra("nouveau", true);
        if (nouveau)
        {
            this.zoneDessin = new ZoneDessin(this);
        } else
        {
            SharedPreferences preferences = getSharedPreferences("dessin", MODE_PRIVATE);
            String formesJsonString = preferences.getString("formes", null);
            if (formesJsonString != null)
            {
                ArrayList<Forme> formes = convertToFormes(formesJsonString);
                this.zoneDessin = new ZoneDessin(this, formes);

            }
        }

        this.layout = findViewById(R.id.dessin_layout);
        this.layout.addView(zoneDessin);

    }

    protected void onPause()
    {
        super.onPause();
        String formesJsonString = convertToJson(zoneDessin.getAlFormes());

        SharedPreferences preferences = getSharedPreferences("dessin", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("formes", formesJsonString);
        editor.apply();
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

    /*----------------------------------*/
    /*Méthodes pour la sauvegarde/chargement*/
    /*----------------------------------*/

    public String convertToJson(ArrayList<Forme> formes)
    {
        JSONArray formesArray = new JSONArray();

        for (Forme forme : formes)
        {
            JSONObject jsonForme = new JSONObject();
            try{
                jsonForme.put("type", forme.getType());
                jsonForme.put("x", forme.getX());
                jsonForme.put("y", forme.getY());
                jsonForme.put("largeur", forme.getWidth());
                jsonForme.put("hauteur", forme.getHeight());
                jsonForme.put("isFill", forme.getIsFill());
                jsonForme.put("couleur", forme.getColor());

                formesArray.put(jsonForme);

            } catch (Exception e) {e.printStackTrace();}
        }

        return formesArray.toString();
    }

    public ArrayList<Forme> convertToFormes(String formesJsonString)
    {
        ArrayList<Forme> formes = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(formesJsonString);

            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonForme = jsonArray.getJSONObject(i);
                String type = jsonForme.getString("type");
                int x = jsonForme.getInt("x");
                int y = jsonForme.getInt("y");
                int largeur = jsonForme.getInt("largeur");
                int hauteur = jsonForme.getInt("hauteur");
                boolean isFill = jsonForme.getBoolean("isFill");
                int couleur = jsonForme.getInt("couleur");

                Forme forme = new Forme(type, x, y, largeur, hauteur, isFill, couleur);
                formes.add(forme);
            }
        } catch (Exception e) {e.printStackTrace();}

        return formes;
    }

    /*----------------------------------*/
    /*Méthodes pour les outils de dessin*/
    /*----------------------------------*/

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

    /*---------------------------------*/
    /*Méthodes pour bundle save/restore*/
    /*---------------------------------*/

    public void onSaveInstanceState(Bundle bagOfData)
    {
        super.onSaveInstanceState(bagOfData);

        String formesArray = convertToJson(zoneDessin.getAlFormes());

        bagOfData.putString("formes", formesArray);
    }

    public void onRestoreInstanceState(Bundle bagOfData)
    {
        super.onRestoreInstanceState(bagOfData);

        if ( bagOfData != null )
        {
            String formesArray = bagOfData.getString("formes");

            if (formesArray != null)
            {
                ArrayList<Forme> formes = convertToFormes(formesArray);
                zoneDessin.setAlFormes(formes);
            }
        }
    }
}