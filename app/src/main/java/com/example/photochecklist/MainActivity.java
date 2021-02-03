package com.example.photochecklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.photochecklist.models.TaskItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.parceler.Parcels;

import java.util.ArrayList;

import static com.example.photochecklist.ItemAdapter.DETAIL_ACTIVITY_CODE;

public class MainActivity extends AppCompatActivity {

    static final int ADD_REQUEST_ID = 10;

    private ArrayList<TaskItem> todoList;
    private RecyclerView itemsRecyclerView;
    private ItemAdapter itemAdapter;
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init the ArrayList (data source)
        todoList = new ArrayList<>();

        // add some fake items for testing
        TaskItem fakeItem = new TaskItem("My first item", "Some description of what this task is about...");
        todoList.add(fakeItem);
        TaskItem fakeItem2 = new TaskItem("Another item", "This is very important to complete!");
        fakeItem2.isComplete = true;
        todoList.add(fakeItem2);

        // find the RecyclerView
        itemsRecyclerView = findViewById(R.id.itemsRecyclerView);
        // construct the adapter from this data source
        itemAdapter = new ItemAdapter(todoList);
        // RecyclerView setup (layout manager, use adapter)
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsRecyclerView.setAdapter(itemAdapter);

        // find the action button and set up on click action
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddActivity.class);
            this.startActivityForResult(intent, ADD_REQUEST_ID);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;

        // ADD_REQUEST_ID is defined above
        if (requestCode == ADD_REQUEST_ID) {
            TaskItem newItem = Parcels.unwrap(data.getParcelableExtra("task_item"));
            todoList.add(0, newItem);
            itemAdapter.notifyItemInserted(0);
            itemsRecyclerView.scrollToPosition(0);
        }
        if (requestCode == DETAIL_ACTIVITY_CODE) {
            // get position of item
            TaskItem updatedItem = Parcels.unwrap(data.getParcelableExtra("updated_item"));
            int position = data.getIntExtra("position", 0);
            todoList.set(position, updatedItem);
            itemAdapter.notifyItemChanged(position);
        }
    }
}
