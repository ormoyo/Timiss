package com.ormoyo.timiss.tasks;

import java.util.Calendar;

public class Task
{
    private final String name;

    private final Calendar startTime;
    private final Calendar endTime;

    private boolean completed;

    public Task(String name, Calendar startDate, Calendar endDate)
    {
        this.name = name;
        this.startTime = startDate;
        this.endTime = endDate;
    }

    public String getName()
    {
        return name;
    }

    public Calendar getStartTime()
    {
        return startTime;
    }

    public Calendar getEndTime()
    {
        return endTime;
    }

    public boolean isCompleted()
    {
        return completed;
    }

    public void setCompleted(boolean completed)
    {
        this.completed = completed;
    }
}
