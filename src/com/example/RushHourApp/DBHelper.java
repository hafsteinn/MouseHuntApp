package com.example.RushHourApp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yngvi
 * Date: 29.10.13
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "PUZZLES_DB";
    public static final int DB_VERSION = 13;


    public static final String TablePuzzles = "puzzles";
    public static final String[] TablePuzzlesCols = { "_id", "sid", "name", "layout","isFinished" };

    private static final String sqlCreateTablePuzzles =
            "CREATE TABLE puzzles (" +
                    "_id  INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "sid  INTEGER NOT NULL," +
                    "name TEXT," +
                    "layout TEXT," +
                    "isFinished INTEGER" +
                    ");";

    private static final String sqlDropTablePuzzles =
            "DROP TABLE IF EXISTS puzzles;";

    public DBHelper(Context context ) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( sqlCreateTablePuzzles );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV) {
        sqLiteDatabase.execSQL( sqlDropTablePuzzles );
        onCreate( sqLiteDatabase );
    }





}