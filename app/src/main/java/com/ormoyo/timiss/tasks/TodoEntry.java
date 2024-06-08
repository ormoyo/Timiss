package com.ormoyo.timiss.tasks;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ormoyo.timiss.R;

import java.util.Arrays;

public class TodoEntry extends RecyclerView.ViewHolder
{
    private final CompoundButton checkmark;
    private final TextView description;
    public TodoEntry(@NonNull View itemView)
    {
        super(itemView);

        this.checkmark = itemView.findViewById(R.id.checkmark);
        this.description = itemView.findViewById(R.id.title);
    }

    public static class Adapter extends RecyclerView.Adapter<TodoEntry>
    {
        private final Task[] tasks;
        public Adapter(Task... tasks)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
            {
                Log.d("TodoEntry", "Adapter " + String.join(", ", Arrays.stream(tasks).map(Task::getName).toList()));
            }
            this.tasks = tasks;
        }

        @NonNull
        @Override
        public TodoEntry onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.todo_entry, parent, false);
            Log.d("TodoEntry", "onCreateViewHolder");
            return new TodoEntry(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TodoEntry holder, int position)
        {
            holder.description.setText(tasks[position].getName());
            holder.checkmark.setChecked(tasks[position].isCompleted());

            holder.checkmark.setOnCheckedChangeListener((btn, checked) -> {
                tasks[position].setCompleted(!tasks[position].isCompleted());
                holder.checkmark.setChecked(tasks[position].isCompleted());
            });
            Log.d("TodoEntry", "onBindViewHolder");
        }

        @Override
        public int getItemCount()
        {
            return tasks.length;
        }
    }
}
