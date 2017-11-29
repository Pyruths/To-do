package com.example.qwerty.todo;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qwerty.todo.DataBase.AsyncTasks.DeleteTasks;
import com.example.qwerty.todo.DataBase.AsyncTasks.GetSubTasks;
import com.example.qwerty.todo.DataBase.AsyncTasks.GetTasks;
import com.example.qwerty.todo.DataBase.Task;
import com.example.qwerty.todo.DataBase.TaskDataBase;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass to handle the RecyclerView
 * Use the {@link TaskListView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskListView extends Fragment {
    public static final String TAG = "TaskListViewFragment";
    // TODO Remove these if they are redundant
    private RecyclerView mRecyclerView;
    private TodoItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Task[] mTasks;
    private static final String PARENT = "parent";
    private Integer parentID;



    public TaskListView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param parentID This is the id of the parent view to show, -1 for all.
     * @return A new instance of fragment TaskListView.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskListView newInstance(Integer parentID) {
        TaskListView fragment = new TaskListView();
        // Set Arguments
        Bundle args = new Bundle();
        if (parentID != null){
            args.putInt(PARENT,parentID);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parentID = getArguments().getInt(PARENT, -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.task_list, container, false);
        rootView.setTag(TAG);

        mRecyclerView = rootView.findViewById(R.id.taskList);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TodoItemAdapter(getActivity().getApplicationContext(),parentID);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PARENT,parentID);
    }

    public void update(){
        mAdapter.update();
    }

}
