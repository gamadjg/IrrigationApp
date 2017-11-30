package com.sp.school.irrigationapplication;

import java.util.ArrayList;

/**
 * Created by David on 11/16/2017.
 */

public class Datapoints {
    ArrayList graphValues = new ArrayList();
    ArrayList graphDates = new ArrayList();
    int gValue;
    int gDate;
    // Empty Constructor
    public Datapoints(){}

    //Constructor to link values
    public Datapoints( int FBDate, int FBValue){
        graphValues.add(FBValue);
        graphDates.add(FBDate);
    }

    public int getValue ( int i){
        return graphValues.indexOf(i);
    }

    public int getDate(int i){
        return graphDates.indexOf(i);
    }

    public void addValue(int FBValue){
        graphValues.add(FBValue);
    }

    public void addDate(int FBDate){
        graphValues.add(FBDate);
    }
}
