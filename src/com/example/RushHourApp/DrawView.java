package com.example.RushHourApp;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

public class DrawView extends View {

    private class MyShape {

        MyShape( Rect r, int c ) {
            rect = r;
            color = c;
        }
        Rect rect;
        int  color;
    }

    private int cellWidth = 0;
    private int cellHeight = 0;
	private RushHour rushHour;
    Paint paint = new Paint();
	Paint carPaint = new Paint();
    Cars movingCar = null;
	ShapeDrawable shape = new ShapeDrawable(new OvalShape());
    Rect rect = new Rect();

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor( Color.BLACK );
        paint.setStyle( Paint.Style.STROKE );

	    String setting = "";
	    try
	    {
		    setting = ReadXML.read(context.getAssets().open("challenge_classic40.xml"));
	    }
	    catch(IOException iox)
	    {
		    System.out.println("Error opening xml-file");
	    }

	    System.out.println(setting);

	    ArrayList<Cars> cars = new ArrayList<Cars>();

	    /*Cars escapeCar = new Cars(new Rect(120, 2*120, 3*120, 3*120),false, "", 2 , Color.RED);
	    cars.add(new Cars(new Rect(0, 120, 120, 4*120), true, "", 3, Color.GREEN));
	    cars.add(new Cars(new Rect(0, 0, 2*120, 120), false, "", 2, Color.BLUE));
		cars.add(new Cars(new Rect(3*120, 120, 4*120, 4*120), true, "", 3, Color.CYAN));  */

	    rushHour = new RushHour();

	    rushHour.setState(setting);
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

	    for ( Cars c : rushHour.getCars() ) {
		    carPaint.setColor( c.getColor() );
		    if(c.getRect() != null)
		        canvas.drawRect( c.getRect(), carPaint );
	    }

	    // draw the escape car
	    carPaint.setColor(rushHour.getEscapeCar().getColor());
	    if(rushHour.getEscapeCar() != null)
	    {
		    if(rushHour.getEscapeCar().getRect() != null)
			    canvas.drawRect(rushHour.getEscapeCar().getRect(), carPaint);
	    }
    }

    public boolean onTouchEvent( MotionEvent event ) {

        int x = (int) event.getX();
        int y = (int) event.getY();


        switch ( event.getAction() ) {
            case MotionEvent.ACTION_DOWN:
                movingCar = findShape( x, y );
                break;
            case MotionEvent.ACTION_UP:
                if ( movingCar != null ) {
                    movingCar = null;
                    // emit an custom event ....
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if ( movingCar != null ) {
                    x = Math.min( x, getWidth() - movingCar.getRect().width() );
                    movingCar.getRect().offsetTo(x, y);
                    invalidate();
                }
                break;
        }
        return true;
    }

    private Cars findShape( int x, int y ) {
        for ( Cars c : rushHour.getCars() ) {
            if ( c.getRect().contains(x, y) ) {
                return c;
            }
        }
	    if(rushHour.getEscapeCar().getRect().contains(x,y))
		    return rushHour.getEscapeCar();

        return null;
    }
}