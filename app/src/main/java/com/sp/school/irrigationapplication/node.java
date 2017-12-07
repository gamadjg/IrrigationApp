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
    ArrayList <String> graphValues = new ArrayList<>();
    ArrayList <String> graphTimes = new ArrayList();
    int i = 0;
    String keys, anotherString;
    String sval;


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

        DBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get Values, put them in an Arraylist
                for (DataSnapshot dbdata : dataSnapshot.getChildren()) {
                    //Pulls the value 'date' from node (currently just node3) (used for x-axis)
                    graphTimes.add(dbdata.child("time").getValue().toString());
                    graphValues.add(dbdata.child("soil_temp").getValue().toString());
                }
                //Create Arrays equal to size of the arraylist values pulled
                double[] dubValList = new double[graphValues.size()];
                double[] dubTimeList = new double[graphTimes.size()];

                // Move graphValues list to dubValList
                for(int i=0;i<graphValues.size();i++){
                    dubValList[i] = Double.parseDouble(graphValues.get(i));
                }

                // Move graphTimes into dubTimeList
                for(int i=0;i<graphTimes.size();i++){
                    dubTimeList[i] = Double.parseDouble(graphTimes.get(i).replace(':', '.'));
                    //TV.append(dubDateList[i]);
                }
                //series = new LineGraphSeries<DataPoint>(new DataPoint[]{new DataPoint(0,0)});

                for(int i=0; i<dubTimeList.length;i++){
                    //series = new LineGraphSeries<DataPoint>(new DataPoint[]{new DataPoint(dubTimeList[i],dubValList[i])});
                    series.appendData(new DataPoint(dubTimeList[i],dubValList[i]), false,dubTimeList.length );
                }

                //series.appendData(new DataPoint(.5,.5), true, 500);
                graph.addSeries(series);
                //OrganizeData(graphDates);

                graph.getViewport().setMaxX(dubTimeList.length);
                graph.getViewport().setMinX(1.0);
                graph.getViewport().setMaxY(30.0);
                graph.getViewport().setMinY(1.0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //series.appendData(new DataPoint(.5,.5), true, 500);

        //graph.addSeries(series);


        //GraphView graph = (GraphView) findViewById(R.id.nGraph);
        //series = new LineGraphSeries<DataPoint>(new DataPoint[]);
        /*
        double x = 0;
        double y = 0;
        series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0,0)
        });

        for(int i=0; i<6; i++){
            x = x + 2;
            y = y + 1;
/*
            series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(x,y)
            });
*/
            //series.appendData(new DataPoint(x,y), false, 7);


        /*
        series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0,1),
                new DataPoint(1,5),
                new DataPoint(2,3)
        });
        */
        //graph.addSeries(series);

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