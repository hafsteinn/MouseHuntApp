package com.example.RushHourApp;

import android.content.ContentResolver;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.*;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

public class DrawView extends View {

    private PuzzleAdapter puzzleAdapter;

    private int cellWidth = 0;
    private int cellHeight = 0;
	private int relativeX = 0;
	private int relativeY = 0;

	private RushHour rushHour;
    Paint paint = new Paint();




    Car movingCar = null;
	ShapeDrawable shape = new ShapeDrawable(new OvalShape());
    Rect rect = new Rect();


    //texture related stuff
    Paint regularCarPaint = new Paint();
    Paint regularCarPaintHorizontal = new Paint();
    Paint escapeCarPaint = new Paint();
    Bitmap escapeCarBitmap;
    Bitmap regularCarBitmap;
    Bitmap regularCarBitmapHorizontal;
    Matrix escapeCarMatrix = new Matrix();
    Matrix regularCarMatrix = new Matrix();
    Matrix regularCarMatixHorizontal = new Matrix();

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor( Color.BLACK );
        paint.setStyle( Paint.Style.STROKE );

        //Create Bitmap for escape Car
        escapeCarBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.mouse_horizontal);
        //Create Bitmap for regular car
        regularCarBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mousetrap_vertical_ready);
        regularCarBitmapHorizontal = BitmapFactory.decodeResource(context.getResources(), R.drawable.mousetrap_horizontal_ready);

        puzzleAdapter = new PuzzleAdapter(context);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        rushHour = new RushHour(width);

        addPuzzlesToDB(context);


	    //rushHour.setState(puzzleAdapter.queryPuzzle(1).getString(3));
    }

    private void addPuzzlesToDB(Context context)
    {
        String layout = "";

        for(int i = 1; i <= 40;i++ )
        {
            try
            {
                layout = ReadXML.read(context.getAssets().open("challenge_classic40.xml"), i);
            }
            catch(IOException iox)
            {
                System.out.println("Error opening xml-file");
            }


            puzzleAdapter.insertPuzzle(i,"Puzzle" + " " + i, layout, false);

        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        cellWidth = xNew / 6;
        cellHeight = yNew / 6;
    }


    protected void onDraw( Canvas canvas ) {

	    for ( int r=5; r>=0; --r ) {
		    for ( int c=0; c<6; ++c ) {
			    rect.set( c * cellWidth, r * cellHeight,
					    c * cellWidth + cellWidth, r * cellHeight + cellHeight );
			    canvas.drawRect( rect, paint );
			    rect.inset( (int)(rect.width() * 0.1), (int)(rect.height() * 0.1) );
			    shape.setBounds( rect );

		    }
	    }

	    for ( Car c : rushHour.getCars() ) {
		    //carPaint.setColor( c.getColor() );
		    if(c.getRect() != null)
		    {
                if(!c.isVertical())
                {

                    BitmapShader regularCarShaderHorizontal = new BitmapShader(regularCarBitmapHorizontal,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
                    regularCarPaintHorizontal.setShader(regularCarShaderHorizontal);
                    regularCarBitmapHorizontal=Bitmap.createScaledBitmap(regularCarBitmapHorizontal, c.getRect().width(), c.getRect().height(), true);
                    regularCarMatixHorizontal.setTranslate(c.getRect().centerX() - c.getRect().width()/2, c.getRect().centerY() - c.getRect().height()/2);
                    regularCarPaintHorizontal.getShader().setLocalMatrix(regularCarMatixHorizontal);

                    canvas.drawRect( c.getRect(), regularCarPaintHorizontal );
                }
                else
                {
                    BitmapShader regularCarShader = new BitmapShader(regularCarBitmap,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
                    regularCarPaint.setShader(regularCarShader);
                    regularCarBitmap=Bitmap.createScaledBitmap(regularCarBitmap, c.getRect().width(), c.getRect().height(), true);
                    regularCarMatrix.setTranslate(c.getRect().centerX() - c.getRect().width()/2, c.getRect().centerY() - c.getRect().height()/2);
                    regularCarPaint.getShader().setLocalMatrix(regularCarMatrix);

                    canvas.drawRect( c.getRect(), regularCarPaint );
                }
		    }
	    }

	    // draw the escape car
	    if(rushHour.getEscapeCar() != null)
	    {
		    if(rushHour.getEscapeCar().getRect() != null)
            {
                BitmapShader escapeCarShader = new BitmapShader(escapeCarBitmap,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
                escapeCarPaint.setShader(escapeCarShader);
                escapeCarBitmap=Bitmap.createScaledBitmap(escapeCarBitmap, rushHour.getEscapeCar().getRect().width(), rushHour.getEscapeCar().getRect().height(), true);
                escapeCarMatrix.setTranslate(rushHour.getEscapeCar().getRect().centerX() - rushHour.getEscapeCar().getRect().width()/2, rushHour.getEscapeCar().getRect().centerY() - rushHour.getEscapeCar().getRect().height()/2);
                escapeCarPaint.getShader().setLocalMatrix(escapeCarMatrix);
			    canvas.drawRect(rushHour.getEscapeCar().getRect(), escapeCarPaint);
            }
	    }
    }

    public boolean onTouchEvent( MotionEvent event ) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch ( event.getAction() ) {
            case MotionEvent.ACTION_DOWN:
                movingCar = findShape( x, y );
	            if(movingCar != null)
	            {
	                relativeX = x - movingCar.getRect().left;
		            relativeY = y - movingCar.getRect().top;
	            }
                break;
            case MotionEvent.ACTION_UP:
                if ( movingCar != null ) {
                    movingCar = null;
                    // emit an custom event ....
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if ( movingCar != null ) {
                   // x = Math.min( x, getWidth() - movingCar.getRect().width() );
	                if(movingCar.isVertical())
                        movingCar.getRect().offsetTo(movingCar.getRect().left , y - relativeY);
	                else
	                	movingCar.getRect().offsetTo(x - relativeX, movingCar.getRect().top);
                    invalidate();
                }
                break;
        }
        return true;
    }

    private Car findShape( int x, int y ) {
        for ( Car c : rushHour.getCars() ) {
            if ( c.getRect().contains(x, y) ) {
                return c;
            }
        }
	    if(rushHour.getEscapeCar().getRect().contains(x,y))
		    return rushHour.getEscapeCar();

        return null;
    }
}