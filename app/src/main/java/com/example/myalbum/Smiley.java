package com.example.myalbum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Smiley extends View {

    private static final String COLOR_HEX = "#000000"; // RRGGBB - Black
    private final Paint mPaint;
    private float xPosition;
    private float yPosition;
    private float radius;
    private float strokeWidth = 10;
    private float defaultScale = 0.90f;
    private float eyeRadius = 20;
    private float eyeYPosition;
    private float leftEyeXPosition;
    private float rightEyeXPosition;
    private  boolean faceMood;



    public Smiley(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public Smiley(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);


        xPosition = getMeasuredWidth() / 2;
        yPosition = getMeasuredHeight() / 2;
        radius = xPosition < yPosition ? xPosition : yPosition;
        radius *= defaultScale;
        canvas.drawCircle(xPosition, yPosition, radius, mPaint); //face

        mPaint.setColor(Color.BLUE);

        // find eye y position
        eyeYPosition = (float) (yPosition / 1.2);

        // find eye x position
        leftEyeXPosition = xPosition < yPosition ? xPosition / 2 : (float) (xPosition / 1.3);

        // find right eye x position
        rightEyeXPosition = xPosition < yPosition ? xPosition + xPosition / 2 : xPosition + xPosition / 4;

        // left eye
        canvas.drawCircle(leftEyeXPosition, eyeYPosition, eyeRadius, mPaint);

        // right eye
        canvas.drawCircle(rightEyeXPosition, eyeYPosition, eyeRadius, mPaint);

        // draw mouth.
        RectF oval = new RectF(leftEyeXPosition, yPosition + yPosition / 8, rightEyeXPosition, (float) (yPosition + yPosition / 2.5)); // left top right bottom


        if( faceMood == false){
            canvas.drawArc(oval, 10, 150, false, mPaint);

        }else{
            canvas.drawArc(oval, 200, 140, false, mPaint); // sad face.

        }
        faceMood = false;
    }

    public void sadFace(){
        faceMood = true;
    }


}
