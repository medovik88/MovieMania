package moviemania.any.com.moviemania.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.util.Log;
import android.view.View;


import moviemania.any.com.moviemania.model.FloatPoint;
import moviemania.any.com.moviemania.model.Movie;

/**
 * Created by Юра on 01.02.2016.
 */
public class Hexagon extends View
{
    private Movie currentMovie;
    private Paint paint;
    private Path borders;
    private Path lines;
    private Path hexagonMovie;

    private Point[] bordersPoints = new Point[6];
    private FloatPoint[] hexagonMoviePoints = new FloatPoint[6];

    private int centerX;
    private int centerY;

    private int margin;

    private int rateActing, ratePlot, rateMontage, rateSoundTrack, rateSpecialEffects, rateTouchEffect;

    private PathMeasure pathMeasure;

    float length;





    public Hexagon(Context context, Movie currentMovie)
    {
        super(context);
        paint = new Paint();
        borders = new Path();
        lines = new Path();
        hexagonMovie = new Path();
        this.currentMovie = currentMovie;
        rateActing = currentMovie.getRate().getActing();
        ratePlot = currentMovie.getRate().getPlot();
        rateMontage = currentMovie.getRate().getMontage();
        rateSoundTrack = currentMovie.getRate().getSoundTrack();
        rateSpecialEffects = currentMovie.getRate().getSpecialEffects();
        rateTouchEffect = currentMovie.getRate().getTouchEffect();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        centerX = getWidth()/2;
        centerY = getHeight()/2;
        margin = 10;

        canvas.drawARGB(80, 102, 204, 255);

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        bordersPoints[0] = new Point(centerX, 0 + margin);
        borders.moveTo(centerX, centerY);
        borders.lineTo(bordersPoints[0].x, bordersPoints[0].y);

        pathMeasure = new PathMeasure(borders, false);
        length = pathMeasure.getLength();

        fillBordersPoints();
        drawMovieRateOnHexagon();
        drawBorders(canvas);
        drawMovie(canvas);




    }

    private void fillBordersPoints(){

//        bordersPoints[0] = new Point(centerX, 0 + margin);
//        bordersPoints[1] = new Point(getWidth()- margin, margin + ((getHeight()- 2 * margin)/(2*2)));
//        bordersPoints[2] = new Point(getWidth()- margin, (getHeight() - ((getHeight() - 2 * margin)/4)));
//        bordersPoints[3] = new Point(centerX, (getHeight() - margin));
//        bordersPoints[4] = new Point(0 + margin, getHeight() -  ((getHeight() - 2 * margin)/(2*2)));
//        bordersPoints[5] = new Point(0 + margin,(margin + ((getHeight() - 2 * margin)/(2*2))));

        bordersPoints[0] = new Point(centerX, 0 + margin);
        bordersPoints[1] = new Point((int)(centerX + findMarginX(length)), (int)(centerY - findMarginY(length)));
        bordersPoints[2] = new Point((int)(centerX + findMarginX(length)), (int)(centerY + findMarginY(length)));
        bordersPoints[3] = new Point(centerX, (getHeight() - margin));
        bordersPoints[4] = new Point((int)(centerX - findMarginX(length)), (int)(centerY + findMarginY(length)));
        bordersPoints[5] = new Point((int)(centerX - findMarginX(length)), (int)(centerY - findMarginY(length)));

    }

    private void drawMovieRateOnHexagon(){

        float d1 = ((getHeight()- 2 * margin) * rateActing /(2*10));
        float d2 = (length/10)* ratePlot;
        float d3 = (length/10)* rateMontage;
        float d4 = ((getHeight()- 2 * margin) * (rateSoundTrack ) /(2*10));
        float d5 = (length/10)* rateSpecialEffects;
        float d6 = (length/10)* rateTouchEffect;

        // float d1 = (length/10)* rateActing;
//        float d2 = ((getHeight()- 2 * margin) * (ratePlot ) /(2*10));
       // float d3 = ((getHeight()- 2 * margin) * (rateMontage ) /(2*10));
        // float d4 = (length/10)* rateSoundTrack;
//        float d5 = ((getHeight()- 2 * margin) * (rateSpecialEffects ) /(2*10));
//        float d6 = ((getHeight()- 2 * margin) * (rateTouchEffect ) /(2*10));




        hexagonMoviePoints[0] = new FloatPoint(centerX, centerY - d1);
        hexagonMoviePoints[1] = new FloatPoint(centerX + findMarginX(d2), centerY - findMarginY(d2));
        hexagonMoviePoints[2] = new FloatPoint(centerX + findMarginX(d3), centerY + findMarginY(d3));
        hexagonMoviePoints[3] = new FloatPoint(centerX, centerY + d4);
        hexagonMoviePoints[4] = new FloatPoint(centerX - findMarginX(d5), centerY + findMarginY(d5));
        hexagonMoviePoints[5] = new FloatPoint(centerX - findMarginX(d6), centerY - findMarginY(d6));
    }

    private float findMarginX(float d){
        double marginX = Math.pow(d , 2);
        marginX -= Math.pow((0.5 * d), 2);
        marginX = Math.sqrt(marginX);
        return  (float)marginX ;
    }

    private float findMarginY(float d){
        float marginY =(float) 0.5 * d;
        return  marginY;
    }

    private void drawBorders(Canvas canvas){

        borders.lineTo(bordersPoints[0].x, bordersPoints[0].y);
        borders.lineTo(bordersPoints[1].x, bordersPoints[1].y);
        borders.lineTo(bordersPoints[2].x, bordersPoints[2].y);
        borders.lineTo(bordersPoints[3].x, bordersPoints[3].y);
        borders.lineTo(bordersPoints[4].x, bordersPoints[4].y);
        borders.lineTo(bordersPoints[5].x, bordersPoints[5].y);
        borders.lineTo(bordersPoints[0].x, bordersPoints[0].y);
        canvas.drawPath(borders, paint);

        lines.moveTo(centerX, centerY);
        lines.lineTo(bordersPoints[1].x, bordersPoints[1].y);
        lines.moveTo(centerX, centerY);
        lines.lineTo(bordersPoints[2].x, bordersPoints[2].y);
        lines.moveTo(centerX, centerY);
        lines.lineTo(bordersPoints[3].x, bordersPoints[3].y);
        lines.moveTo(centerX, centerY);
        lines.lineTo(bordersPoints[4].x, bordersPoints[4].y);
        lines.moveTo(centerX, centerY);
        lines.lineTo(bordersPoints[5].x, bordersPoints[5].y);
        canvas.drawPath(lines, paint);
    }

    private void drawMovie(Canvas canvas){

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        hexagonMovie.moveTo(hexagonMoviePoints[0].x, hexagonMoviePoints[0].y );
        hexagonMovie.lineTo(hexagonMoviePoints[1].x, hexagonMoviePoints[1].y);
        hexagonMovie.lineTo(hexagonMoviePoints[2].x, hexagonMoviePoints[2].y);
        hexagonMovie.lineTo(hexagonMoviePoints[3].x, hexagonMoviePoints[3].y);
        hexagonMovie.lineTo(hexagonMoviePoints[4].x, hexagonMoviePoints[4].y);
        hexagonMovie.lineTo(hexagonMoviePoints[5].x, hexagonMoviePoints[5].y);
        hexagonMovie.lineTo(hexagonMoviePoints[0].x, hexagonMoviePoints[0].y);
        canvas.drawPath(hexagonMovie, paint);
    }


}
