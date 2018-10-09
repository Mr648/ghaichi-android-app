package com.sorinaidea.ghaichi.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


public class CustomPieProgress extends AppCompatImageView {

    private Paint            circlePaint;
    private Paint            textPaint;

    private static final int STROKE_WIDTH = 8;

    private int              percent      = 100;


    public CustomPieProgress(Context context) {
        super(context);
        initialize(context);
    }


    public CustomPieProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }


    public CustomPieProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }


    public void setProgress(int value) {
        percent = value;
        postInvalidate();
    }


    private void initialize(Context context) {
        circlePaint = new Paint();
        circlePaint.setColor(Color.parseColor("#ffffff"));
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Style.FILL_AND_STROKE);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Align.CENTER);
        textPaint.setTextSize(24);
        textPaint.setStyle(Style.FILL_AND_STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rect = new RectF();
        rect.left = 0 + STROKE_WIDTH;
        rect.right = getWidth() - STROKE_WIDTH;
        rect.top = 0 + STROKE_WIDTH;
        rect.bottom = getHeight() - STROKE_WIDTH;

        int sweepAngle = percent * 360 / 100;

//        int red = (int) ((100 - percent) * 2.5f);
//        int green = (int) (percent * 2.5f);
//        circlePaint.setColor(Color.rgb(red, green, 0));

        canvas.drawArc(rect, -90, sweepAngle, false, circlePaint);
        canvas.drawText(percent + "%", getWidth() / 2, getHeight() / 2, textPaint);
    }
}

