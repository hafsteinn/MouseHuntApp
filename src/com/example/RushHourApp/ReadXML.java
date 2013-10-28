package com.example.RushHourApp;/*
 * User: Ólafur Daði Jónsson
 * Date: 28.10.2013
 * Time: 13:23
 */

import android.content.res.AssetManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class ReadXML
{

	public static String read(InputStream stream)
	{
		StringBuilder sb = new StringBuilder();
		try
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document doc = builder.parse(stream);

			doc.getDocumentElement().normalize();

			//NodeList nList = doc.getElementsByTagName("puzzle");

			Element element = doc.getElementById("1");

			sb.append(element.getElementsByTagName("setup").item(0).getTextContent());

		}
		catch(Exception ex)
		{

		}

		return sb.toString();

	}
}
