package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.todolist.domain.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToDoListManager {


    private DatabaseHelper dbHelper;
    public ToDoListManager(Context context)
    {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    @SuppressLint("SimpleDateFormat")
    public List<ToDoItem> getItems() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " +DatabaseHelper.TABLE_NAME,
                null
        );

        List<ToDoItem> items = new ArrayList<>();


        if(cursor.moveToFirst()) //if db has at least 1 row of data
        {
            while(!cursor.isAfterLast())
            {
                @SuppressLint("Range") String dateString = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATE));
                Date newDate = null;

                try {
                    newDate = new SimpleDateFormat(DatabaseHelper.FORMAT).parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                @SuppressLint({"Range", "SimpleDateFormat"}) ToDoItem item = new ToDoItem(
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.DESCRIPTION)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COMPLETED)) != 0,
                        cursor.getLong(cursor.getColumnIndex(DatabaseHelper.ID)),
                        newDate);

                items.add(item);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return items;
    }

    public void addItem(ToDoItem item)
    {
        //New Database code
        //creates a new content value item that can be passed to database
        ContentValues newItem = new ContentValues();
        newItem.put(DatabaseHelper.DESCRIPTION, item.getDescription());
        newItem.put(DatabaseHelper.COMPLETED, item.isComplete());

        //formatted date to dateString to send to the database
        @SuppressLint("SimpleDateFormat")
        String dateString = new SimpleDateFormat(DatabaseHelper.FORMAT).format(item.getDate());
        newItem.put(DatabaseHelper.DATE, dateString);

        //gets a writable copy of the sqlite database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //inserts the new item into the database
        db.insert(DatabaseHelper.TABLE_NAME, null, newItem);
    }

    public void removeItem(ToDoItem item)
    {
        //Delete Item
        //Where _id=""

        SQLiteDatabase db = dbHelper.getWritableDatabase(); //setup writable Database
        String[] args = new String[] {String.valueOf(item.getId())}; //set up arguments (id)

        //Delete from database
        db.delete(
                DatabaseHelper.TABLE_NAME,
                DatabaseHelper.ID + "=?",
                args
        );
    }

    public void updateItem(ToDoItem item)
    {
        //UPDATE item
        //SET Description = "", completed = ""
        //WHERE _id = ""

        ContentValues updateItem = new ContentValues();

        updateItem.put(DatabaseHelper.DESCRIPTION, item.getDescription());
        updateItem.put(DatabaseHelper.COMPLETED, item.isComplete());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] args = new String[] {String.valueOf(item.getId())};

        db.update(
                DatabaseHelper.TABLE_NAME,
                updateItem,
                DatabaseHelper.ID + "=?",
                args
        );

    }
}
