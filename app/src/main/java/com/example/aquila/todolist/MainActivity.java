package com.example.aquila.todolist;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DbHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView lsTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);

        lsTask = (ListView) findViewById(R.id.lsTask);

        loadTaskList();
    }

    private void loadTaskList() {
        ArrayList<String> taskList = dbHelper.getTaskList();
        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<String>(this, R.layout.row, R.id.task_tittle, taskList);
            lsTask.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu);

        //Gnati warna menu
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white, PorterDuff.Mode.SRC_IN));

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_Task;
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this);
                    .setTitle("Add New Task");
                    .setMessage("What do you want to do next ?")
                    .setView(taskEditText)
                    .setPositiveButton("Add"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String task = String.valueOf(taskEditText.getText());
                    dbHelper.insertNewTask(task);
                    loadTaskList();
                }
            }
                    .setNegativeButton("Cancel", null)
                    .create();
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void DeleteTask (View view){
        View parent = (View)View.getParent();
        TextView taskTextView = (TextView)findViewById(R.id.task_tittle);
        String task  = String.valueOf(taskTextView.getText());
        dbHelper.deleteTask(task);
        loadTaskList();
    }
}