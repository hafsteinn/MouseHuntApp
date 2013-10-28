package com.example.RushHourApp;/*
 * User: Ólafur Daði Jónsson
 * Date: 28.10.2013
 * Time: 09:11
 */

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Iterator;

public class RushHour
{
	private ArrayList<Cars> cars;
	private Cars escapeCar;

	public RushHour()
	{
	}

	public RushHour(ArrayList<Cars> cars, Cars escapeCar)
	{
		this.cars = cars;
		this.escapeCar = escapeCar;
	}

	public ArrayList<Cars> getCars()
	{
		return cars;
	}

	public void setCars(ArrayList<Cars> cars)
	{
		this.cars = cars;
	}

	public Cars getEscapeCar()
	{
		return escapeCar;
	}

	public void setEscapeCar(Cars escapeCar)
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

		for(Cars c : cars)
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

		System.out.println(sb.toString());

		return sb.toString();
	}

	public void setState(String state)
	{
		//TODO: make a reset() function which reads the xml and adds all the cars for the given board
		//precondition: all the cars have been added to the board

		String[] s = state.split(",");

		String escapeCarSettings = s[0];

		if(escapeCarSettings.charAt(1) == 'V')
			escapeCar.setVertical(true);
		else
			escapeCar.setVertical(false);

		escapeCar.setX(escapeCarSettings.charAt(3));
		escapeCar.setY(escapeCarSettings.charAt(5));
		escapeCar.setLength(escapeCarSettings.charAt(7));

		Iterator<Cars> it = cars.iterator();

		for(int i = 1 ; i < s.length ; i++)
		{
			String carSettings = s[i];
			Cars car = it.next();

			if(carSettings.charAt(2) == 'V')
				car.setVertical(true);
			else
				car.setVertical(false);

			car.setX(carSettings.charAt(4));
			car.setY(carSettings.charAt(6));
			car.setLength(carSettings.charAt(8));
		}
	}
}
