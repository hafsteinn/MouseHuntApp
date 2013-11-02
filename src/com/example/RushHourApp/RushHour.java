package com.example.RushHourApp;/*
 * User: Ólafur Daði Jónsson
 * Date: 28.10.2013
 * Time: 09:11
 */

import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;

public class RushHour
{
	private ArrayList<Car> cars;
	private Car escapeCar;
	private int width;

	public RushHour(int width)
	{
		this.width = width/6;
	}

	public RushHour(ArrayList<Car> cars, Car escapeCar)
	{
		this.cars = cars;
		this.escapeCar = escapeCar;
	}

	public ArrayList<Car> getCars()
	{
		return cars;
	}

	public void setCars(ArrayList<Car> cars)
	{
		this.cars = cars;
	}

	public Car getEscapeCar()
	{
		return escapeCar;
	}

	public void setEscapeCar(Car escapeCar)
	{
		this.escapeCar = escapeCar;
	}

	@Override
	public String toString()
	{
        StringBuilder sb = new StringBuilder();

		sb.append('(');
		if(escapeCar.isVertical())
			sb.append('V');
		else
			sb.append('H');

		sb.append(' ');
		sb.append(escapeCar.getX() + ' ' + escapeCar.getY() + ' ' + escapeCar.getLength());
		sb.append("), ");

		for(Car c : cars)
		{
			sb.append('(');

			if(c.isVertical())
				sb.append('V');
			else
				sb.append('H');

			sb.append(' ');
			sb.append(c.getX() + ' ' + c.getY() + ' ' + escapeCar.getLength());
			sb.append("), ");
		}

		//System.out.println(sb.toString());

		return sb.toString();
	}

	public void setState(String state)
	{
		//TODO: make a reset() function which reads the xml and adds all the cars for the given board
		//precondition: all the cars have been added to the board

		String[] s = state.split(",");

		if(escapeCar == null)
			escapeCar = new Car();

		String escapeCarSettings = s[0];

		if(escapeCarSettings.charAt(1) == 'V')
			escapeCar.setVertical(true);
		else
			escapeCar.setVertical(false);

		escapeCar.setX(escapeCarSettings.charAt(3) - 48);
		escapeCar.setY((int)escapeCarSettings.charAt(5) - 48);
		escapeCar.setLength(escapeCarSettings.charAt(7) - 48);

		if(cars == null)
			cars = new ArrayList<Car>();

		for(int i = 1 ; i < s.length ; i++)
		{
			String carSettings = s[i];
			Car car = new Car();

			if(carSettings.charAt(2) == 'V')
				car.setVertical(true);
			else
				car.setVertical(false);

			car.setX(carSettings.charAt(4) - 48);
			car.setY(carSettings.charAt(6) - 48);
			car.setLength(carSettings.charAt(8) - 48);

			cars.add(car);
		}
		setDimensions();
	}

	private void setDimensions()
	{
		escapeCar.setRect(new Rect(escapeCar.getX()*width, escapeCar.getY()*width ,escapeCar.getX()* width + escapeCar.getLength()*width,
				escapeCar.getY()*width + width));
		escapeCar.setColor(Color.RED);
		escapeCar.getRect().inset((int)(escapeCar.getRect().width()*0.01), (int)(escapeCar.getRect().height()*0.01));

		for(Car c : cars)
		{
			c.setColor(Color.BLUE);
			if(c.isVertical())
			{
				c.setRect(new Rect(c.getX()*width, c.getY()*width, c.getX()*width + width,
						c.getY()*width + c.getLength()*width));
			}
			else
			{
				c.setRect(new Rect(c.getX()*width, c.getY()*width, c.getX()*width + c.getLength()*width, c.getY()*width + width));
			}
			c.getRect().inset((int)(c.getRect().width()*0.01), (int)(c.getRect().height()*0.01));
		}
	}
}
