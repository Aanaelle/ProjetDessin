package com.example.projetdessin;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.shapes.Shape;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class ZoneDessin extends View implements View.OnTouchListener
{
    private ArrayList<Forme> formes;
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;

    private String type = "";
    private boolean isFill = false;

    public ZoneDessin(Context context) {
        super(context);
        this.formes = new ArrayList<Forme>();
        this.setOnTouchListener(this);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        for(Forme forme : formes) {
            int xF = forme.getX();
            int yF = forme.getY();
            int widthF = forme.getWidth();
            int heightF = forme.getHeight();
            switch (forme.getType()) {
                case "carre":
                    if (forme.getIsFill()) {
                        paint.setStyle(Paint.Style.FILL);
                    } else {
                        paint.setStyle(Paint.Style.STROKE);
                    }
                    canvas.drawRect(xF, yF, xF + widthF, +yF +heightF, paint);
                    break;
                case "cercle":
                    if (forme.getIsFill()) {
                        paint.setStyle(Paint.Style.FILL);
                    } else {
                        paint.setStyle(Paint.Style.STROKE);
                    }
                    canvas.drawCircle(xF, yF, widthF, paint);
                    break;
                case "ligne":
                    if (xF < widthF && yF < heightF) {
                        canvas.drawLine(xF, yF, widthF, heightF, paint);
                    } else if (xF > widthF && yF < heightF) {
                        canvas.drawLine(widthF, yF, xF, heightF, paint);
                    } else if (xF < widthF && yF > heightF) {
                        canvas.drawLine(xF, heightF, widthF, yF, paint);
                    } else if (xF > widthF && yF > heightF) {
                        canvas.drawLine(widthF, heightF, xF, yF, paint);
                    }

                    break;
            }
        }
    }

    public void effacer() {
        this.formes.clear();
        invalidate();

    }

    public void setCouleur(Color color) {
        this.color = color;
    }

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
            else if(this.type == "ligne") {
                width = (int) motionEvent.getX();
                height = (int) motionEvent.getY();
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
            else if(this.type == "ligne") {
                width = (int) motionEvent.getX();
                height = (int) motionEvent.getY();
            }
            this.formes.add(new Forme(this.type, x, y, width, height, this.isFill));
            invalidate();
        }


        return true;
    }


    public void setType(String type, boolean b) {
        this.type = type;
        this.isFill = b;
    }

    public void undo() {
        if(this.formes.size() > 0) {
            this.formes.remove(this.formes.size() - 1);
            invalidate();
        }
    }

}

class Forme
{
    private String type;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isFill;


    public Forme(String type, int x, int y, int width, int height, boolean isFill)
    {
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isFill = isFill;
        this.isFill = isFill;
    }
    {
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getType()
    {
        return this.type;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public boolean getIsFill()
    {
        return this.isFill;
    }

}
