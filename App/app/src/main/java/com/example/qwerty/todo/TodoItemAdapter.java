package com.example.qwerty.todo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.qwerty.todo.DataBase.Task;


/**
 * Created by Qwerty on 24/11/2017.
 */

public class TodoItemAdapter extends RecyclerView.Adapter<TodoItemAdapter.ViewHolder>{
    private Task[] tasks;

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCard;
        TextView mText;
        CheckBox mSelect;
        TextView mDate;
        ViewHolder(FrameLayout v){
            super(v);
            mText = v.findViewById(R.id.text);
            mCard = v.findViewById(R.id.card);
            mSelect = v.findViewById(R.id.checkBox);
            mDate = v.findViewById(R.id.Date);
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
        final int fposition = position;
        holder.mText.setText(tasks[position].getText());
        holder.mDate.setText(tasks[position].getExpiration().toString());
        holder.mCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(),TaskView.class);
                intent.putExtra(TaskView.TASK_STRING,taskID);
                v.getContext().startActivity(intent);
            }
        });

        holder.mSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Task current = tasks[fposition];
                current.toggle(!current.isSelected());
                v.setBackgroundColor(current.isSelected()? Color.CYAN: Color.WHITE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.length;
    }
}