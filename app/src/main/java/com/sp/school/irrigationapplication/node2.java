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
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class node2 extends AppCompatActivity {
    Spinner sp1;
    Button submitBtn;
    LineGraphSeries<DataPoint> series;
    LineGraphSeries<DataPoint> moistSeries;
    LineGraphSeries<DataPoint> maxWeather;
    LineGraphSeries<DataPoint> minWeather;


    FirebaseDatabase FDB;
    DatabaseReference DBRef;
    TextView TV;
    GraphView graph;
    ArrayList <String> graphValues = new ArrayList<>();
    ArrayList <String> graphTimes = new ArrayList();
    ArrayList <String> graphMoist = new ArrayList();
    int maxWeatherVal = 0;
    int minWeatherVal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
        FDB = FirebaseDatabase.getInstance();// Database instance
        DBRef = FDB.getReference("lora/node2"); // reference to instance of the database
        TV = (TextView) findViewById(R.id.childView);
        graph = (GraphView) findViewById(R.id.nGraph);
        series = new LineGraphSeries<DataPoint>();
        moistSeries = new LineGraphSeries<DataPoint>();
        maxWeather = new LineGraphSeries<DataPoint>();
        minWeather = new LineGraphSeries<DataPoint>();


        DBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get Values, put them in an Arraylist
                for (DataSnapshot dbdata : dataSnapshot.getChildren()) {
                    //Pulls the value 'date' from node (currently just node3) (used for x-axis)
                    graphTimes.add(dbdata.child("time").getValue().toString());
                    graphValues.add(dbdata.child("soil_temp").getValue().toString());
                    graphMoist.add(dbdata.child("soil_moist").getValue().toString());
                    maxWeatherVal = maxWeatherVal + Integer.parseInt(dbdata.child("weather_high").getValue().toString());
                    minWeatherVal = minWeatherVal + Integer.parseInt(dbdata.child("weather_low").getValue().toString());
                }
                //Create Arrays equal to size of the arraylist values pulled
                double[] dubValList = new double[graphTimes.size()];
                double[] dubTimeList = new double[graphTimes.size()];
                double[] dubMoistList = new double[graphTimes.size()];
                maxWeatherVal = maxWeatherVal/graphValues.size();
                minWeatherVal = minWeatherVal/graphValues.size();
                // Move graphValues list to dubValList
                for(int i=0;i<graphValues.size();i++){
                    dubValList[i] = Double.parseDouble(graphValues.get(i));
                    dubMoistList[i] = Double.parseDouble(graphMoist.get(i));
                }
                // Move graphTimes into dubTimeList
                /*
                for(int i=0;i<graphTimes.size();i++){
                    //dubTimeList[i] = Double.parseDouble(graphTimes.get(i).replace(':', '.'));
                    dubTimeList[i] = i;
                }
                */
                for(int i=0; i<dubTimeList.length;i++){
                    dubTimeList[i] = i;
                    series.appendData(new DataPoint(dubTimeList[i],dubValList[i]), false,dubTimeList.length);
                    moistSeries.appendData(new DataPoint(dubTimeList[i],dubMoistList[i]), false,dubTimeList.length);
                    maxWeather.appendData(new DataPoint(dubTimeList[i],maxWeatherVal), false,dubTimeList.length);
                    minWeather.appendData(new DataPoint(dubTimeList[i],minWeatherVal), false,dubTimeList.length);
                }
                series.setDrawBackground(false);
                maxWeather.setDrawBackground(true);
                minWeather.setDrawBackground(true);
                minWeather.setBackgroundColor(Color.WHITE);

                graph.addSeries(series);
                graph.addSeries(moistSeries);
                graph.addSeries(maxWeather);
                graph.addSeries(minWeather);

                graph.getViewport().setMaxX(dubTimeList.length);
                graph.getViewport().setMinX(1.0);
                graph.getViewport().setMaxY(50.0);
                graph.getViewport().setMinY(1.0);
                graph.getViewport().setXAxisBoundsManual(true);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void addListenerOnSpinnerItemSelection() {
        sp1 = (Spinner) findViewById(R.id.spinner1);
    }

    public void addListenerOnButton() {
        sp1 = (Spinner) findViewById(R.id.spinner1);
        submitBtn = (Button) findViewById(R.id.nodeSubmit);
        submitBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(node2.this,
                        "Node Selected : "+ String.valueOf(sp1.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();

                if(Integer.parseInt(String.valueOf(sp1.getSelectedItemId())) == 0){
                    Intent i=new Intent(node2.this,node.class);
                    startActivity(i);
                }
                if(Integer.parseInt(String.valueOf(sp1.getSelectedItemId())) == 1){
                    Intent i=new Intent(node2.this,node2.class);
                    startActivity(i);
                }
            }
        });
    }
}