package com.example.vladkomkow.chimichals;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by vladkomkow on 02.05.17.
 */

public class Surface extends SurfaceView implements SurfaceHolder.Callback{
    private DrawThread drawThread;
    public Surface(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder());
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("Surface", "Surface changed");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.requestStop();
        boolean retry = true;
        while(retry){
            try{
                drawThread.join();
                retry = false;
            } catch (InterruptedException e){}
        }
    }
    @Override
    public boolean onTouchEvent (MotionEvent event){
        boolean drag = false;
        float x1 = drawThread.x;
        float y1 = drawThread.y;
        if(event.getAction() == MotionEvent.ACTION_DOWN && (event.getX()>x1-50 && event.getX()<x1+50) && (event.getY()>y1-50 && event.getY()<y1+50))
        {
            drawThread.setXY(event.getX(), event.getY());
            Log.d("motion", "ЕСТЬ контакт");
            drag = true;

        }
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            if(drag) drawThread.setXY(event.getX(), event.getY());
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
           drawThread.setXY(x1,y1);
            drag = false;
        }
        return true;
    }
}

