package com.example.todolist.domain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper { //singleton pattern required in multi-threaded environment

    private static final String DATABASE_NAME = "todo1.db";
    private static final int DATABASE_VERSION = 1;
    public static final String DESCRIPTION = "description";
    public static final String COMPLETED = "completed";
    public static final String ID ="_id";
    public static final String DATE = "date";
    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    //Principle of Least Privilege
        public static final String TABLE_NAME = "items";
    /*
    * Every abstraction of code (class/function) should operate using least amount of privilege
    * necessary to complete its job
    * */

    private static DatabaseHelper instance = null;

    //constructor is private due to singleton pattern
    //To access Constructor, must go through getInstance() function
    public static DatabaseHelper getInstance(Context context)
    {
        /*Singleton Pattern
            design pattern that restricts the instantiation of a class to one object. Useful for when
            exactly one object is needed to coordinate actions across the system
        */
        if (instance ==null)
        {
            instance = new DatabaseHelper(context);
        }

        return instance;
    }

    private DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createQuery = "CREATE TABLE " + TABLE_NAME +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " description TEXT NOT NULL, " +
                " completed INTEGER NOT NULL DEFAULT 0, " +
                " date TEXT NOT NULL)";

        sqLiteDatabase.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
