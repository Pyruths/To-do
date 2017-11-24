package com.example.qwerty.todo;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Qwerty on 24/11/2017.
 */

public class TodoItemAdapter extends RecyclerView.Adapter<TodoItemAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mText;
        public ViewHolder(FrameLayout v){
            super(v);
            mText = v.findViewById(R.id.text);
        }
    }

    public TodoItemAdapter(){

    }
    @Override
    public TodoItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_item,parent,false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(TodoItemAdapter.ViewHolder holder, int position) {
        holder.mText.setText(R.string.ToDoText);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}