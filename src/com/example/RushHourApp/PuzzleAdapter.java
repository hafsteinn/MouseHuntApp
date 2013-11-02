package com.example.RushHourApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yngvi
 * Date: 29.10.13
 * Time: 16:26
 * To change this template use File | Settings | File Templates.
 */
public class PuzzleAdapter {

    SQLiteDatabase db;
    DBHelper dbHelper;
    Context  context;

    PuzzleAdapter( Context c ) {

        context = c;
    }

    public PuzzleAdapter openToRead() {
        dbHelper = new DBHelper( context );
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public PuzzleAdapter openToWrite() {
        dbHelper = new DBHelper( context );
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public long insertPuzzle( int sid, String name, String layout, boolean isFinished ) {
        String[] cols = DBHelper.TablePuzzlesCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], ((Integer)sid).toString() );
        contentValues.put( cols[2], name );
        contentValues.put( cols[3], layout);
        contentValues.put( cols[4], isFinished ? "1" : "0" );
        openToWrite();
        long value = db.insert(DBHelper.TablePuzzles, null, contentValues );
        close();
        return value;
    }

    public long updatePuzzle( int sid, String name, boolean isFinished ) {
        String[] cols = DBHelper.TablePuzzlesCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], ((Integer)sid).toString() );
        contentValues.put( cols[2], name );
        contentValues.put( cols[4], isFinished ? "1" : "0" );
        openToWrite();
        long value = db.update(DBHelper.TablePuzzles, contentValues, cols[1] + "=" + sid, null );
        close();
        return value;
    }

    public Cursor queryPuzzles() {
        openToRead();
        Cursor cursor = db.query( DBHelper.TablePuzzles,
                DBHelper.TablePuzzlesCols, null, null, null, null, null );
        return cursor;
    }

    public Cursor queryPuzzle( int sid ) {
        openToRead();
        String[] cols = DBHelper.TablePuzzlesCols;
        Cursor cursor = db.query( DBHelper.TablePuzzles,
                cols, cols[1] + "=" + sid , null, null, null, null );
        return cursor;
    }



}