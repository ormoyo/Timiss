package com.ormoyo.timiss.tasks;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TaskManager
{
    private final Context context;
    private Map<String, Task> tasks;

    public TaskManager(Context context)
    {
        this.context = context;
        this.tasks = new HashMap<>();
    }

    public void addTask(Task task)
    {
        this.tasks.put(task.getName(), task);
        this.save();
    }

    public Task getTask(String name)
    {
        return this.tasks.get(name);
    }

    public Collection<Task> getTasks()
    {
        return Collections.unmodifiableCollection(this.tasks.values());
    }

    private final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
    public void save()
    {
        SharedPreferences.Editor editor = context.getSharedPreferences("TASKS", Context.MODE_PRIVATE).edit();
        for (Task task : tasks.values())
        {
            editor.putString(task.getName(),
                    formatter.format(task.getStartTime().getTime()) + "-" +
                    formatter.format(task.getEndTime().getTime()));
            editor.apply();
        }
    }

    public void load() throws ParseException
    {
        SharedPreferences preferences = context.getSharedPreferences("TASKS", Context.MODE_PRIVATE);
        this.tasks = new HashMap<>(preferences.getAll().size());

        for (Map.Entry<String, ?> task : preferences.getAll().entrySet())
        {
            if (task.getValue() instanceof String str)
            {
                Calendar startTime = Calendar.getInstance();
                Calendar endTime = Calendar.getInstance();

                String[] arr = str.split("-");
                for (int i = 0; i < arr.length; i++)
                {
                    Date date = formatter.parse(arr[i]);
                    switch (i)
                    {
                        case 0:
                            startTime.setTime(date);
                            break;
                        case 1:
                            endTime.setTime(date);
                            break;
                        default:
                            throw new ParseException("Invalid task format", -1);
                    }
                }
            }
        }
    }
}
