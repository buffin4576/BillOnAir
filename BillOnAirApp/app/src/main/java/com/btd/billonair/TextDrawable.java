package com.btd.billonair;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

/**
 * Created by Buffin on 16/08/2016.
 */
public class TextDrawable extends Drawable {

    private final String entrate;
    private final String uscite;
    private final String totale;
    private final Paint paint;
    private final Paint paint2;
    private final Paint paint3;

    public TextDrawable(double entrate, double uscite) {

        this.entrate = "In: "+ String.format("%.2f", entrate);
        this.uscite = "Out: "+ String.format("%.2f", uscite);
        this.totale = "Tot: "+ String.format("%.2f", (entrate-uscite));

        this.paint = new Paint();
        paint.setColor(Color.rgb(20,200,50));
        paint.setTextSize(26);
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
        //paint.setShadowLayer(6f, 0, 0, Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);

        this.paint2 = new Paint();
        paint2.setColor(Color.RED);
        paint2.setTextSize(26);
        paint2.setAntiAlias(true);
        paint2.setFakeBoldText(true);
        //paint.setShadowLayer(6f, 0, 0, Color.BLACK);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setTextAlign(Paint.Align.LEFT);

        this.paint3 = new Paint();
        paint3.setColor(Color.BLACK);
        paint3.setTextSize(26);
        paint3.setAntiAlias(true);
        paint3.setFakeBoldText(true);
        //paint.setShadowLayer(6f, 0, 0, Color.BLACK);
        paint3.setStyle(Paint.Style.FILL);
        paint3.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawText(entrate, 0, 28, paint);
        canvas.drawText(uscite, 0, 28*2, paint2);
        canvas.drawText(totale, 0, 28*3, paint3);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}