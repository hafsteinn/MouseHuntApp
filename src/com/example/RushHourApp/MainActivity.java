package com.example.RushHourApp;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

public class MainActivity extends Activity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
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

    //TODO: fall til að keyra puzzle list.
}
