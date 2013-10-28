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


	/**
	 * Called when the activity is first created.
	 */
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
        startActivity(intent);
    }


     /*
    public void buttonPressed( View view )
    {
        Button buttonView = (Button) view;
        switch ( view.getId() ) {
            case R.id.buttonPLAY:
                    //what to do if play button is pressed ?
                break;
            case R.id.buttonPuzzles:
                    //what to do if puzzles button is pressed ?
                break;
            case R.id.buttonOptions:
                    //what to do if Options button is pressed ?
                break;
            case R.id.buttonAbout:
                    startAboutActivity(view);
               // showabout();
                break;

        }

    }
    */
}
