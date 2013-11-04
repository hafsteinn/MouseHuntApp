package com.example.RushHourApp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class BoardActivity extends Activity {

	private DrawView drawView;

	private PuzzleAdapter puzzleAdapter;
	private Cursor cursor;

	private Button prev_button;
	private Button next_button;
	private TextView textView;

	private int boardID = 1;

	private RushHour rushHour;
	Car movingCar = null;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

	    setContentView(R.layout.boardview);

	    drawView = (DrawView)findViewById(R.id.drawview);
	    prev_button = (Button)findViewById(R.id.prevPuzzle);
	    next_button = (Button)findViewById(R.id.nextPuzzle);
	    textView = (TextView)findViewById(R.id.boardName);

	    puzzleAdapter = new PuzzleAdapter(getApplicationContext());

	    boardID = getIntent().getIntExtra("BoardID",0);

	    System.out.println("BOARDID" + boardID);


	    setUp(boardID);

	    drawView.setMoveEventHandler(new OnMoveEventHandler()
	    {
		    @Override
		    public void victory()
		    {

			    puzzleAdapter.updatePuzzle(boardID,cursor.getString(2),true);

			    LayoutInflater inflater = getLayoutInflater();
			    View layout = inflater.inflate(R.layout.toast_layout,
					    (ViewGroup) findViewById(R.id.toast_layout_root));

			    TextView text = (TextView) layout.findViewById(R.id.text);
			    text.setText("Victory! Onto the next puzzle!");

			    Toast toast = new Toast(getApplicationContext());
			    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			    toast.setDuration(Toast.LENGTH_SHORT);
			    toast.setView(layout);
			    toast.show();

			    if(boardID != 40)
			        setUp(++boardID);

		    }
	    });


	   /* String setting = "";
	    try
	    {
		    setting = ReadXML.read(drawView.getContext().getAssets().open("challenge_classic40.xml"),2);
	    }
	    catch(IOException iox)
	    {
		    System.out.println("Error opening xml-file");
	    } */

    }

	public void onPrevClick(View view)
	{
		setUp(--boardID);
	}

	public void onNextClick(View view)
	{
		setUp(++boardID);
	}

	public void setUp(int boardID)
	{

		if(boardID == 1)
			prev_button.setVisibility(View.INVISIBLE);

		else
			prev_button.setVisibility(View.VISIBLE);

		if(boardID == 40)
			next_button.setVisibility(View.INVISIBLE);

		else
			next_button.setVisibility(View.VISIBLE);



		DisplayMetrics metrics = drawView.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		rushHour = new RushHour(width);


		cursor = puzzleAdapter.queryPuzzle(boardID);
		cursor.moveToFirst();
		String value = cursor.getString(3);

		textView.setText(cursor.getString(2));

		rushHour.setState(value);
		drawView.setCars(rushHour.getCars());
		drawView.setEscapeCar(rushHour.getEscapeCar());
		drawView.invalidate();
	}
}
