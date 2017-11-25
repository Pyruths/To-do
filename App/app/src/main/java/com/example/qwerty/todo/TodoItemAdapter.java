package com.example.qwerty.todo;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.qwerty.todo.DataBase.Task;

import org.w3c.dom.Text;

/**
 * Created by Qwerty on 24/11/2017.
 */

public class TodoItemAdapter extends RecyclerView.Adapter<TodoItemAdapter.ViewHolder>{
    private Task[] tasks;

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCard;
        TextView mText;
        ViewHolder(FrameLayout v){
            super(v);
            mText = v.findViewById(R.id.text);
            mCard = v.findViewById(R.id.card);

        }
    }

    TodoItemAdapter(Task[] tasks){
        this.tasks = tasks;

    }
    @Override
    public TodoItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_item,parent,false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(TodoItemAdapter.ViewHolder holder, int position) {
        final int taskID = tasks[position].getId();
        holder.mText.setText(tasks[position].getText());

        holder.mCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(),newTask.class);
                intent.putExtra(newTask.TASK_STRING,taskID);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.length;
    }
}