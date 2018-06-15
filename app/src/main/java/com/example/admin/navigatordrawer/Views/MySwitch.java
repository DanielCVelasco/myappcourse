package com.example.admin.navigatordrawer.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MySwitch extends View {


    public MySwitch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width= getResources().getDisplayMetrics().widthPixels;
        int height= getResources().getDisplayMetrics().heightPixels;

        Log.d("App", "width " + width + " height " + height);

        Paint paint= new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2);

        /*int[] values= new float{20, 40, 60, 80, 100}

        for (int i= 0; i < values.length; i++){
            int columnWidth= width / values.length;

            canvas.drawRect(20,20,width - 20,height - 20, paint);
        }*/


        canvas.save();
    }
}
