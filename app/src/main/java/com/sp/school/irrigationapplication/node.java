package com.sp.school.irrigationapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class node extends AppCompatActivity {
    Spinner sp1;
    Button submitBtn;
    LineGraphSeries<DataPoint> series;
    LineGraphSeries<DataPoint> series1;
    LineGraphSeries sTempSeries;
    LineGraphSeries sMoistSeries;

    FirebaseDatabase FDB;
    DatabaseReference DBRef;
    TextView TV;
    List<String> graphItems;
    GraphView graph;
    ArrayList graphValues = new ArrayList();
    ArrayList graphDates = new ArrayList();
    int i = 0;
    String keys, anotherString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
        FDB = FirebaseDatabase.getInstance();// Database instance
        DBRef = FDB.getReference("lora/node3"); // reference to instance of the database
        TV = (TextView) findViewById(R.id.childView);
        graph = (GraphView) findViewById(R.id.nGraph);
        series = new LineGraphSeries<DataPoint>();

        //sTempSeries = new LineGraphSeries("TEMP", Color.GREEN, 3, new GraphViewData[] { new GraphViewData(0, 0) );

        GraphView graph = (GraphView) findViewById(R.id.nGraph);
        series = new LineGraphSeries<DataPoint>();
        //series1 = new LineGraphSeries<DataPoint>("Temp", Color.GREEN);


        //LineGraphSeries line1Series = new LineGraphSeries("LINE ONE", new LineGraphSeries(Color.GREEN, 3), new GraphViewData[] { new GraphViewData(0, 0) }); // put an complete array
        //LineGraphSeries line1Series = new LineGraphSeries("LINE ONE", Color.GREEN, 3, new GraphViewData[] { new GraphViewData(0, 0) }); // put an complete array


        DBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dbdata : dataSnapshot.getChildren()){
                    //Pulls the value 'date' from node (currently just node3) (used for x-axis)
                    //graphDates.add(dbdata.child("date").getValue());

                    //Pulls value 'soil_temp' from node (currently just node3)
                    //graphValues.add(dbdata.child("soil_temp").getValue());

                    // append node values to different lines on the graph

                }
                //TV.append(graphValues.toString() +"\n");

                //OrganizeData(graphDates);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        /*
        GraphView graph = (GraphView) findViewById(R.id.nGraph);
        series = new LineGraphSeries<DataPoint>();
        for(int i=0; i<500; i++){
            x = x + 0.1;
            y = 0.5;
            series.appendData(new DataPoint(x,y), true, 500);
        }
        graph.addSeries(series);
        */
        //GraphView graph = (GraphView) findViewById(R.id.nGraph);
        //series = new LineGraphSeries<DataPoint>();


        /*
        DBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;

                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    //Originals -- Working -- prints date/values in order as new lines of text
                    //String date = (String) messageSnapshot.child("Date").getValue();
                    //String value = (String) messageSnapshot.child("Value").getValue();
                    int value = (int) messageSnapshot.child("Value").getValue();
                    graphValues.add(value);
                    //TV.append(date +":"+value +"\n");

                    //series.appendData(new DataPoint(value, .5), true, 500);
                }

                //graph.addSeries(series);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        */
    }

    // R
    /*
    private void OrganizeData(ArrayList graphDates){
        for(int i = 0; i< graphDates.size(); i++){
            graphDates.indexOf(i)
        }
    }
    */

    public void addListenerOnSpinnerItemSelection() {
        sp1 = (Spinner) findViewById(R.id.spinner1);
    }

    public void addListenerOnButton() {
        sp1 = (Spinner) findViewById(R.id.spinner1);
        submitBtn = (Button) findViewById(R.id.nodeSubmit);
        submitBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(node.this,
                        "Node Selected : "+ String.valueOf(sp1.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}