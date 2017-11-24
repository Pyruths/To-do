package com.example.qwerty.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {
    private RecyclerView toDoList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toDoList = findViewById(R.id.toDoList);

        mLayoutManager = new LinearLayoutManager(this);
        toDoList.setLayoutManager(mLayoutManager);

        mAdapter = new TodoItemAdapter();
        toDoList.setAdapter(mAdapter);



    }
}

