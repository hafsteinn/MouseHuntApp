package com.example.RushHourApp;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: yngvi
 * Date: 29.10.13
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
public class PuzzleListActivity extends ListActivity {

    private PuzzleAdapter mPuzzlesAdapter = new PuzzleAdapter( this );
    private SimpleCursorAdapter mCursorAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cursor cursor = mPuzzlesAdapter.queryPuzzles();
        String[] cols = DBHelper.TablePuzzlesCols;
        String from[] = { cols[1], cols[2], cols[3]};
        int to[] = { R.id.s_sid, R.id.s_name,R.id.s_isFinished };
        startManagingCursor( cursor );

        mCursorAdapter = new SimpleCursorAdapter(this, R.layout.puzzlelist, cursor, from, to );

        mCursorAdapter.setViewBinder( new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int i) {
                if ( i==3 ) {
                    ((ImageView) view).setImageResource(
                            (cursor.getInt(i) == 0) ? R.drawable.emo_im_sad : R.drawable.emo_im_cool );
                    return true;
                }

                return false;
            }
        });

        setListAdapter(mCursorAdapter);
    }

    protected void onRestart() {
        super.onRestart();
        mCursorAdapter.getCursor().requery();
    }

    protected void onDestroy() {
        super.onDestroy();
        mPuzzlesAdapter.close();
    }

    protected void onListItemClick( ListView l, View v, int position, long id  ) {
        // ...
    }

}