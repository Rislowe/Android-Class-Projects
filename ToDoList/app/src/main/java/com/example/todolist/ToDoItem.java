package com.example.todolist;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ToDoItem {

    private final String description;
    private boolean isComplete;
    private final long id;
    private final Date date;

    public ToDoItem(String description, boolean completion)
    {
        this(description, completion, -1, Calendar.getInstance().getTime());
    }

    public ToDoItem(String description, boolean isComplete, long id, Date date)
    {
        this.description = description;
        this.isComplete = isComplete;
        this.id = id;
        this.date = date;
    }

    public String getDescription()
    {
        return description;
    }

    public boolean isComplete()
    {
        return isComplete;
    }

    public void toggleComplete()
    {
        isComplete = !isComplete;
    }

    public long getId() {return id;}

    public Date getDate()
    {
        return date;
    }

    @Override
    public String toString()
    {
        return getDescription();
    }
}
