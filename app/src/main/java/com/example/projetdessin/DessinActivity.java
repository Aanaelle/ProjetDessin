package com.example.projetdessin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class DessinActivity extends AppCompatActivity
{

    private DessinView dessinView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dessin);
        dessinView = new DessinView(this);
        dessinView.setOnTouchListener(dessinView);

        View view = findViewById(R.id.view);

        //view.draw(dessinView.getCanvas());


    }

    public void quitter(View view)
    {
        finish();
    }

    public void effacer(View view)
    {
        dessinView.effacer();
    }

    public void choisirCouleur(View view)
    {
        /*//récupere la couleur du bouton
        Button button = view.findViewById(view.getId());

            ColorDrawable buttonColor = (ColorDrawable) button.getBackground();
            //dessinView.setCouleur(Color.valueOf(buttonColor.getColor()));
            int intColor = ((ColorDrawable) button.getBackground()).getColor();*/

    }

    public void carre(View view)
    {
        dessinView.setType("carre", false);
        System.out.println("carre");
    }

    public void carreFill(View view)
    {
        dessinView.setType("carre", true);
    }

    public void rond(View view)
    {
        dessinView.setType("cercle", false);
    }

    public void rondFill(View view)
    {
        dessinView.setType("cercle", true);
    }

    public void ligne(View view)
    {
        dessinView.setType("triangle", false);
    }


}

class DessinView extends View implements View.OnTouchListener {

    private ArrayList<Shape> shapes;
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;

    private String type = "";
    private boolean isFill = false;

    public DessinView(Context context) {
        super(context);
        this.shapes = new ArrayList<Shape>();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        switch (this.type)
        {
            case "carre":
                if(this.isFill)
                {
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(x, y, x + width, y + height, paint);
                }
                else
                {
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawRect(x, y, x + width, y + height, paint);
                }
                break;
            case "cercle":
                if(this.isFill)
                {
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(x, y, width, paint);
                }
                else
                {
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawCircle(x, y, width, paint);
                }
                break;
            case "ligne":
                canvas.drawLine(x, y, width, height, paint);

                break;
        }

    }

    public void effacer() {

    }

    public void setCouleur(Color color) {
        this.color = color;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            x = (int) motionEvent.getX();
            y = (int) motionEvent.getY();
            invalidate();
        } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {

            if(x < motionEvent.getX() && y < motionEvent.getY()) {
                width = (int) motionEvent.getX() - x;
                height = (int) motionEvent.getY() - y;
            } else if(x > motionEvent.getX() && y < motionEvent.getY()) {
                width = x - (int) motionEvent.getX();
                height = (int) motionEvent.getY() - y;
            } else if(x < motionEvent.getX() && y > motionEvent.getY()) {
                width = (int) motionEvent.getX() - x;
                height = y - (int) motionEvent.getY();
            } else if(x > motionEvent.getX() && y > motionEvent.getY()) {
                width = x - (int) motionEvent.getX();
                height = y - (int) motionEvent.getY();
            }
            invalidate();
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if(x < motionEvent.getX() && y < motionEvent.getY()) {
                width = (int) motionEvent.getX() - x;
                height = (int) motionEvent.getY() - y;
            } else if(x > motionEvent.getX() && y < motionEvent.getY()) {
                width = x - (int) motionEvent.getX();
                height = (int) motionEvent.getY() - y;
            } else if(x < motionEvent.getX() && y > motionEvent.getY()) {
                width = (int) motionEvent.getX() - x;
                height = y - (int) motionEvent.getY();
            } else if(x > motionEvent.getX() && y > motionEvent.getY()) {
                width = x - (int) motionEvent.getX();
                height = y - (int) motionEvent.getY();
            }
            invalidate();
        }


        return true;
    }


    public void setType(String type, boolean b) {
        this.type = type;
        this.isFill = b;
    }

    public Canvas getCanvas()
    {
        return new Canvas();
    }
}