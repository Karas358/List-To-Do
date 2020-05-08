package com.example.listtodo;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private List<Task> toDoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle;
        public CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            subtitle =  view.findViewById(R.id.subtitle);
            checkBox =  view.findViewById(R.id.checkbox1);
        }
    }
    public ListAdapter(List<Task> ToDoList) {
        this.toDoList = ToDoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Task task = toDoList.get(position);
        final TaskRepository taskRepository = new TaskRepository(holder.itemView.getContext());
        holder.title.setText(task.Title);
        holder.subtitle.setText(task.SubTitle);
        if(task.Completed){
            holder.checkBox.setChecked(true);
        }
        else {
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(task.Completed){
                    task.Completed = false;
                    taskRepository.UpdateTask(task);
                    Toast.makeText(v.getContext(),task.Title + " Incomplete ", Toast.LENGTH_LONG).show();
                }
                else{
                    task.Completed = true;
                    taskRepository.UpdateTask(task);
                    Toast.makeText(v.getContext(),task.Title + " Completed ", Toast.LENGTH_LONG).show();
                }
                new MainActivity.getTasks(v.getContext()).execute();
            }
        });
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }
}
