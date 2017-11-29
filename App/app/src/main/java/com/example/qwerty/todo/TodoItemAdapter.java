package com.example.qwerty.todo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.qwerty.todo.DataBase.AsyncTasks.DeleteTasks;
import com.example.qwerty.todo.DataBase.AsyncTasks.GetSubTasks;
import com.example.qwerty.todo.DataBase.AsyncTasks.GetTasks;
import com.example.qwerty.todo.DataBase.Task;
import com.example.qwerty.todo.DataBase.TaskDataBase;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * Created by Qwerty on 24/11/2017.
 * To be used in conjunction with a Recycler View
 * This allows for viewing and selection of a list of mTasks.
 */

public class TodoItemAdapter extends RecyclerView.Adapter<TodoItemAdapter.ViewHolder>{
    private Task[] mTasks;
    private ActionMode mActionMode;
    private Context appContext;
    private ArrayList<Task> mSelectedTasks;
    private Integer parentID;


    class ViewHolder extends RecyclerView.ViewHolder{
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

    TodoItemAdapter(Context appContext, Integer parentID){
        mSelectedTasks = new ArrayList<>();
        this.parentID = parentID;
        this.appContext = appContext;
        inflateTasks();

    }
    @Override
    public TodoItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_item,parent,false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(TodoItemAdapter.ViewHolder holder, int position) {
        final int taskID = mTasks[position].getId();
        final int fposition = position;
        Task t = mTasks[position];
        holder.mText.setText(t.getText());
        holder.mDate.setText(t.getExpiration().toString());

        // Set the card selection.
        holder.mCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(),TaskView.class);
                intent.putExtra(TaskView.TASK_STRING,taskID);
                v.getContext().startActivity(intent);
            }
        });
        // Set the Checkbox
        holder.mSelect.setChecked(t.isSelected());
        holder.mSelect.setBackgroundColor(t.isSelected()? Color.CYAN: Color.WHITE);
        holder.mSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Task current = mTasks[fposition];
                current.toggle(!current.isSelected());

                if (current.isSelected()){
                    mSelectedTasks.add(current);
                    if (mActionMode == null){
                        mActionMode = ((AppCompatActivity)v.getContext()).startActionMode(mActionCallback);
                    }
                }
                else
                {
                    mSelectedTasks.remove(current);
                    if (mSelectedTasks.size() == 0){
                        mActionMode.finish();
                    }
                }
                v.setBackgroundColor(current.isSelected()? Color.CYAN: Color.WHITE);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mTasks.length;
    }

    /**
     * Deletes all selected tasks via the user
     */
    private void deleteAll(){
        ArrayList<Task> h = new ArrayList<>();
        for (Task t : mTasks){
            if(t.isSelected()){
                t.toggle(false);
                h.add(t);
            }
        }

        TaskDataBase db = TaskDataBase.getDatabase(appContext);
        // Turn it into an array that is processable by the Async Task
        new DeleteTasks(db).execute(h.toArray(new Task[h.size()]));
    }

    private void inflateTasks(){
        TaskDataBase db = TaskDataBase.getDatabase(appContext);
        mTasks = null;
        if (parentID == null || parentID < 0){//get all
            try{
                mTasks = new GetTasks(db).execute().get();
            } catch (InterruptedException | ExecutionException e){
                e.printStackTrace();
            }
        }
        else
        {
            try{
                mTasks = new GetSubTasks(db).execute(parentID).get();
            } catch (InterruptedException | ExecutionException e){
                e.printStackTrace();
            }
        }
        notifyDataSetChanged();
    }

    public void update(){
        inflateTasks();
    }

    private ActionMode.Callback mActionCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.task_bar,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.delete:
                    deleteAll();
                    update();
                    actionMode.finish(); // finish
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            // Deselect everything
            mActionMode = null;
        }
    };


}