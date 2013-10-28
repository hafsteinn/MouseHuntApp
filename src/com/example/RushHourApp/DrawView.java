package com.example.RushHourApp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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
    Paint paint = new Paint();
	Paint carPaint = new Paint();
    ArrayList<MyShape> mShapes = new ArrayList<MyShape>();
    MyShape mMovingShape = null;
	ShapeDrawable shape = new ShapeDrawable(new OvalShape());
    Rect rect = new Rect();

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor( Color.BLACK );
        paint.setStyle( Paint.Style.STROKE );

        mShapes.add(new MyShape(new Rect(0, 0, 100, 100), Color.RED));
        mShapes.add( new MyShape( new Rect( 200, 300, 300, 350), Color.BLUE ) );
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
			    rect.inset( (int)(rect.width() * 0.5), (int)(rect.height() * 0.1) );
			    shape.setBounds( rect );

		    }
	    }

	    for ( MyShape shape : mShapes ) {
		    carPaint.setColor( shape.color );
		    canvas.drawRect( shape.rect, carPaint );
	    }
    }

    public boolean onTouchEvent( MotionEvent event ) {

        int x = (int) event.getX();
        int y = (int) event.getY();


        switch ( event.getAction() ) {
            case MotionEvent.ACTION_DOWN:
                mMovingShape = findShape( x, y );
                break;
            case MotionEvent.ACTION_UP:
                if ( mMovingShape != null ) {
                    mMovingShape = null;
                    // emit an custom event ....
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if ( mMovingShape != null ) {
                    x = Math.min( x, getWidth() - mMovingShape.rect.width() );
                    mMovingShape.rect.offsetTo( x, y );
                    invalidate();
                }
                break;
        }
        return true;
    }

    private MyShape findShape( int x, int y ) {
        for ( MyShape shape : mShapes ) {
            if ( shape.rect.contains( x, y ) ) {
                return shape;
            }
        }
        return null;
    }
}