package com.sp.school.irrigationapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {
    //Button AddDataBtn = (Button) findViewById(R.id.MAddDataBtn);
    Button AddDataBtn;
    Button NodePageBtn;
    Button CalendarPageBtn;
    //Button NodePageBtn = (Button) findViewById(R.id.NodeSelBtn);
    //Button CalendarPageBtn = (Button) findViewById(R.id.CalendarBtn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        AddDataBtn = (Button)findViewById(R.id.MAddDataBtn);
        AddDataBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(HomePage.this, MainActivity.class));
            }
        });
        NodePageBtn = (Button)findViewById(R.id.NodeSelBtn);
        NodePageBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(HomePage.this, node.class));
            }
        });
        CalendarPageBtn = (Button)findViewById(R.id.CalendarBtn);
        CalendarPageBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(HomePage.this, CalendarPage.class));
            }
        });
        /*
        NodePageBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(HomePage.this, node.class));
            }
        });
        CalendarPageBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(HomePage.this, MainActivity.class));
            }
        });
        */
    }

}
