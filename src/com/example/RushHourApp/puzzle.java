package com.example.RushHourApp;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: hafsteinn11
 * Date: 1.11.2013
 * Time: 22:53
 * Class returns a list of puzzles
 */
public class puzzle {


    String layout;
    boolean isFinished;
    int id;


    public puzzle(String str, boolean fin, int id)
    {
        this.layout = str;
        this.isFinished = fin;
        this.id = id;

    }






}
