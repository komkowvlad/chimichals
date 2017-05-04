package com.example.vladkomkow.chimichals;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import static android.view.MotionEvent.ACTION_UP;


public class DrawThread extends Thread {


    private static final String TAG = "MyActivity";
    private boolean runFlag = false;
    public boolean drag = false;
    private SurfaceHolder surfaceHolder;
    float x, y;
    public void setXY(float x1, float y1) {
        x = x1;
        y = y1;
    }
    private Paint backgroundPaint = new Paint();
    {
        backgroundPaint.setColor(Color.WHITE);
        backgroundPaint.setStyle(Paint.Style.FILL);
    }

    public DrawThread(SurfaceHolder surfaceHolder)
    {
        this.surfaceHolder = surfaceHolder;
    }
    public void setRunning(boolean run){
        runFlag = run;
    }
    public void requestStop() {
        runFlag = false;
    }
    public void run() {
        Canvas canvas;
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            try {
                x = canvas.getWidth()/2;
                y = canvas.getHeight()/2;
                canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
                canvas.drawCircle(x, y, 50, paint);
                Log.d("Surface", "Surface changed");
                canvas.drawText("NH5", x, y, paint);
            } finally {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
    }


