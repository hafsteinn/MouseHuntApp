package com.example.RushHourApp;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

import java.io.IOException;

public class MainActivity extends Activity
{

    private PuzzleAdapter mPuzzlesAdapter = new PuzzleAdapter( this );
    private SimpleCursorAdapter mCursorAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

        Cursor cursor = mPuzzlesAdapter.queryPuzzles();

        if(cursor.getCount() == 0)
            addPuzzlesToDB(this);



	}

	public void startAboutActivity(View view)
	{
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

    public void startBoardActivity(View view) {
        Intent intent = new Intent(this, BoardActivity.class);
	    intent.putExtra("BoardID", 1);
        startActivity(intent);
    }

    public void startPuzzleListActivity(View view) {
        Intent intent = new Intent(this, PuzzleListActivity.class);
        startActivity(intent);
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


            mPuzzlesAdapter.insertPuzzle(i,"PUZZLE" + " " + i,layout,false);

        }

    }


}
