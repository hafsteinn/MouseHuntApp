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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

public class DrawView extends View {

  //  private PuzzleAdapter puzzleAdapter;
	private ArrayList<Car> cars;
	private Car escapeCar;

    private int cellWidth = 0;
    private int cellHeight = 0;
	private int relativeX = 0;
	private int relativeY = 0;
	private int touchDownX = 0;
	private int touchDownY = 0;

	// private RushHour rushHour;
    Paint paint = new Paint();
	Paint carPaint = new Paint();

    Car movingCar = null;
	ShapeDrawable shape = new ShapeDrawable(new OvalShape());
    Rect rect = new Rect();
	private OnMoveEventHandler moveEventHandler = null;

	/*
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
    */

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor( Color.BLACK );
        paint.setStyle( Paint.Style.STROKE );

	    /*
        //Create Bitmap for escape Car
        escapeCarBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.mouse_horizontal);
        //Create Bitmap for regular car
        regularCarBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mousetrap_vertical_ready);
        regularCarBitmapHorizontal = BitmapFactory.decodeResource(context.getResources(), R.drawable.mousetrap_horizontal_ready);
        */

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

    protected void onDraw( Canvas canvas )
    {

	    for ( int r=5; r>=0; --r ) {
		    for ( int c=0; c<6; ++c ) {
			    rect.set( c * cellWidth, r * cellHeight,
					    c * cellWidth + cellWidth, r * cellHeight + cellHeight );
			    canvas.drawRect( rect, paint );
			    rect.inset( (int)(rect.width() * 0.1), (int)(rect.height() * 0.1) );
			    shape.setBounds( rect );

		    }
	    }


	    for ( Car c : cars)  {
		    carPaint.setColor(Color.BLUE);
		    if(c.getRect() != null)
		    {
			    canvas.drawRect( c.getRect(), carPaint );
		    }
	    }
	    // draw the escape car
	    carPaint.setColor(Color.RED);
	    if(escapeCar != null)
	    {
		    if(escapeCar.getRect() != null)
			    canvas.drawRect(escapeCar.getRect(), carPaint);
	    }

	    /*
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
	    }   */
    }

	public boolean onTouchEvent( MotionEvent event ) {

		int x = (int) event.getX();
		int y = (int) event.getY();

		switch ( event.getAction() ) {
			case MotionEvent.ACTION_DOWN:
				movingCar = findShape( x, y );
				if(movingCar != null)
				{
					touchDownX = x;
					touchDownY = y;
					relativeX = x - movingCar.getRect().left;
					relativeY = y - movingCar.getRect().top;
				}
				break;
			case MotionEvent.ACTION_UP:
				if ( movingCar != null ) {
					movingCar = null;
					// emit an custom event ....
					if(escapeCar.getX() == 4 && moveEventHandler != null)
					{
						moveEventHandler.victory();
					}
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if ( movingCar != null ) {
					// x = Math.min( x, getWidth() - movingCar.getRect().width() );
					if(movingCar.isVertical())
					{
						int currentCell = movingCar.getRect().top / cellHeight;
						int cellNo = (y - relativeY)/cellHeight;
						boolean moveUp = cellNo < currentCell;

		                if(cellNo != currentCell)
		                    cellNo = moveUp ? currentCell - 1 : currentCell + 1;

		                if(cellNo < 0)
			                cellNo = 0;
                        else if(cellNo + movingCar.getLength() > 5)
			                cellNo = 6 - movingCar.getLength();

		                if(!isCollisionY(movingCar, moveUp))
		                {
			                movingCar.getRect().offsetTo(movingCar.getRect().left , cellNo*cellHeight);
			                movingCar.setY(cellNo);
		                }
	                }
	                else
	                {
		                int currentCell = movingCar.getRect().left / cellWidth;
		                int cellNo = (x - relativeX)/cellWidth; //get the cell index to where we are moving
		                boolean moveRight = cellNo > currentCell;

						if(cellNo != currentCell)
							cellNo = moveRight ? currentCell + 1 : currentCell - 1;

						if(cellNo < 0)
							cellNo = 0;
						else if(cellNo + movingCar.getLength() > 5)
							cellNo = 6 - movingCar.getLength();

						Log.w("TAG", currentCell + " --- " + cellNo);

						if(!isCollisionX(movingCar,moveRight))
						{
							movingCar.getRect().offsetTo(cellNo*cellWidth, movingCar.getRect().top);
							movingCar.setX(cellNo);
						}
					}

					invalidate();

				}
				break;
		}
		return true;
	}

	private Car findShape( int x, int y ) {
		for ( Car c : cars ) {
			if ( c.getRect().contains(x, y) ) {
				return c;
			}
		}
		if(escapeCar.getRect().contains(x,y))
			return escapeCar;

		return null;
	}

	private boolean isCollisionX(Car collisionCar, boolean moveRight)
	{
		for(Car c : cars)
		{
			int verticalLength = c.isVertical() ?  (c.getLength() - 1) : 0;
			int horizontalLength = !c.isVertical() ? (c.getLength() - 1) : 0;
			if(collisionCar.equals(c))
				continue;

			if(collisionCar.getY() >= c.getY() && collisionCar.getY() <= c.getY() + verticalLength) //check y-boundaries
			{
				if(moveRight)
				{
					if(collisionCar.getX() + (collisionCar.getLength()) == c.getX())
						return true;
				}
				else
				{
					if(collisionCar.getX() - 1 == c.getX() + horizontalLength)
						return true;
				}
			}
		}

		return false;
	}

	private boolean isCollisionY(Car collisionCar, boolean moveUp)
	{
		for (Car c : cars)
		{
			int verticalLength = c.isVertical() ?  (c.getLength() - 1) : 0;
			int horizontalLength = !c.isVertical() ? (c.getLength() - 1) : 0;

			if(collisionCar.equals(c))
				continue;

			if(collisionCar.getX() >= c.getX() && collisionCar.getX() <= c.getX() + horizontalLength) //check y-boundaries
			{
				if(moveUp)
				{
					if(collisionCar.getY() - 1 == c.getY() + verticalLength)
						return true;
				}
				else
				{
					if(collisionCar.getY() + collisionCar.getLength() == c.getY())
						return true;
				}
			}
		}

		Car escapeCar = this.escapeCar;

		int verticalLength = escapeCar.isVertical() ?  (escapeCar.getLength() - 1) : 0;
		int horizontalLength = !escapeCar.isVertical() ? (escapeCar.getLength() - 1) : 0;


		if(collisionCar.getX() >= escapeCar.getX() && collisionCar.getX() <= escapeCar.getX() + horizontalLength) //check y-boundaries
		{
			if(moveUp)
			{
				if(collisionCar.getY() - 1 == escapeCar.getY() + verticalLength)
					return true;
			}
			else
			{
				if(collisionCar.getY() + collisionCar.getLength() == escapeCar.getY())
					return true;
			}
		}
		return false;
	}

	private boolean checkWinner()
	{
		if(escapeCar.getX() == 4)
			return true;
		return false;
	}

	public void setCars(ArrayList<Car> cars)
	{
		this.cars = cars;
	}

	public void setEscapeCar(Car escapeCar)
	{
		this.escapeCar = escapeCar;
	}

	public void setMoveEventHandler(OnMoveEventHandler moveEventHandler)
	{
		this.moveEventHandler = moveEventHandler;
	}
}