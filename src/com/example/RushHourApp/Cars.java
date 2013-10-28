package com.example.RushHourApp;/*
 * User: Ólafur Daði Jónsson
 * Date: 28.10.2013
 * Time: 09:11
 */

import android.graphics.Rect;

public class Cars
{
	private Rect rect;
	private boolean vertical;
	private String backgroundURL;
	private int x;
	private int y;
	private int length;

	public Cars()
	{

	}

	public Cars(Rect rect, boolean vertical, String backgroundURL, int length)
	{
		this.rect = rect;
		this.vertical = vertical;
		this.backgroundURL = backgroundURL;
		this.length = length;
	}

	public Rect getRect()
	{
		return rect;
	}

	public void setRect(Rect rect)
	{
		this.rect = rect;
	}

	public boolean isVertical()
	{
		return vertical;
	}

	public void setVertical(boolean vertical)
	{
		this.vertical = vertical;
	}

	public String getBackgroundURL()
	{
		return backgroundURL;
	}

	public void setBackgroundURL(String backgroundURL)
	{
		this.backgroundURL = backgroundURL;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
}
