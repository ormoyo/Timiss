package com.ormoyo.timiss.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ormoyo.timiss.R;
import com.ormoyo.timiss.Timiss;
import com.ormoyo.timiss.tasks.Task;
import com.ormoyo.timiss.tasks.TodoEntry;

import java.util.Collections;

public class TodoListActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("TodoListActivity", "onCreate");

        setContentView(R.layout.activity_todo_list);
        RecyclerView list = findViewById(R.id.todoList);
        list.setAdapter(new TodoEntry.Adapter(Timiss.getInstance().getTaskManager().getTasks().toArray(new Task[0])));
        list.setLayoutManager(new LinearLayoutManager(this));

    }
}