package com.sorinaidea.ghaichi.util.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

/**
 * Created by mr-code on 2/13/2018.
 */

public class RoundedTransformation implements Transformation {
    private final int radius;
    private final int margin;

    public RoundedTransformation(final int radius, final int margin) {
        this.radius = radius;
        this.margin = margin;
    }

    @Override
    public Bitmap transform(final Bitmap source) {


        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        if (source != output) {
            source.recycle();
        }


        canvas.drawCircle((source.getWidth() - margin)/2, (source.getHeight() - margin)/2, radius-2, paint);

        return output;
    }

    @Override
    public String key() {
        return "rounded";
    }

}