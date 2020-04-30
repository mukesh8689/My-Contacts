package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycontacts.Data.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

EditText idd,name,phone;
Button b1,b2,b3;
    public DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idd=findViewById(R.id.idd);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        b1=findViewById(R.id.save);
        b2=findViewById(R.id.count);
        b3=findViewById(R.id.go);
        db=new DatabaseHandler(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!idd.getText().toString().isEmpty()&&!name.getText().toString().isEmpty()&&!phone.getText().toString().isEmpty())
                {
                    savetocontactdb(view);
                }

                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });

       b3.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(MainActivity.this,Main2Activity.class);
               startActivity(intent);
           }
       });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int c=db.getContactsCount();
                Toast.makeText(MainActivity.this,String.valueOf(c),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void savetocontactdb(View view) {

        Contact contact=new Contact();
        String id=idd.getText().toString();
        String nam=name.getText().toString();
        String ph=phone.getText().toString();

        contact.setId(id);
        contact.setName(nam);
        contact.setPhone(ph);
        db.addcontact(contact);
        Toast.makeText(MainActivity.this,"item saved",Toast.LENGTH_SHORT).show();


    }
}
