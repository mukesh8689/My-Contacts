package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mycontacts.Data.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    List<Contact> contactlist;
    ArrayList<Contact> items;
    public DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        db=new DatabaseHandler(this);
        items=new ArrayList<>();
        contactlist=new ArrayList<>();
        contactlist=db.getallcontacts();

        for(Contact c:contactlist)
        {
            items.add(new Contact(c.getId(),c.getName(),c.getPhone()));
        }

        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        MyAdapter myAdapter = new MyAdapter(Main2Activity.this,items);
        myrv.setLayoutManager(new LinearLayoutManager(Main2Activity.this));
        myrv.setAdapter(myAdapter);

    }
}
