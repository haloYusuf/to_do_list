package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.todolist.adapter.ListContentAdapter;
import com.example.todolist.helper.DBHelper;
import com.example.todolist.model.Content;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper helper;
    private ArrayList<Content> list = new ArrayList<>();
    private RecyclerView rvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvContent = findViewById(R.id.rv_content);
        rvContent.setHasFixedSize(true);
        helper = new DBHelper(this.getApplicationContext());

        list = getListContent();
        Log.e("Coba", list.get(1).getData());
        showRecycleList();

    }

    public ArrayList<Content> getListContent(){
        List<Content> dataContent = helper.getAllData();
        if (dataContent.isEmpty()){
            return new ArrayList<Content>();
        }else {
            ArrayList<Content> data = new ArrayList<>();
            data.addAll(dataContent);
            return data;
        }
    }

    private void showRecycleList(){
        rvContent.setLayoutManager(new LinearLayoutManager(this));

        ListContentAdapter listContentAdapter = new ListContentAdapter(list);
        rvContent.setAdapter(listContentAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Content a = new Content();
                a.setData(input.getText().toString());
                helper.addData(a);
                list = getListContent();
                showRecycleList();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
        return super.onOptionsItemSelected(item);
    }
}