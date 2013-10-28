package com.example.RushHourApp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

    private int m_cellWidth = 0;
    private int m_cellHeight = 0;
    private char[][] m_board = new char[6][6];
    Paint mPaint = new Paint();
    ArrayList<MyShape> mShapes = new ArrayList<MyShape>();
    MyShape mMovingShape = null;
    Rect rect = new Rect();

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor( Color.WHITE );
        mPaint.setStyle( Paint.Style.STROKE );

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
        m_cellWidth = xNew / 3;
        m_cellHeight = yNew / 3;
    }

    protected void onDraw( Canvas canvas ) {

        for ( MyShape shape : mShapes ) {
            mPaint.setColor( shape.color );
            canvas.drawRect( shape.rect, mPaint );
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