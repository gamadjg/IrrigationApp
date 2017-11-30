package com.sp.school.irrigationapplication;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    FirebaseDatabase FDB;
    DatabaseReference DBRef;
    TextView TV;
    EditText EDT;
    Button Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FDB = FirebaseDatabase.getInstance();// Database instance
        DBRef = FDB.getReference(); // reference to instance of the database
        TV = (TextView)findViewById(R.id.textFromDB); // link textFromDB to var 'TV'
        EDT = (EditText)findViewById(R.id.editText); // Link editText to var 'EDT'
        Save = (Button)findViewById(R.id.sendData); // Link sendData button to var S'ave'

        //Reading


        //Writing
        Save.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
               if(EDT.getText().toString().isEmpty()){
                   Toast.makeText(getApplicationContext(), "Please Write", Toast.LENGTH_SHORT).show();
               }else{
                   String currentDandT = DateFormat.getDateTimeInstance().format(new Date());
                   //Update unique key with 2 children: TIMESTAMP and DATA
                   createPost(currentDandT, EDT.getText().toString());

                   //This changes the Value of the Child Within Temp without changing the nameValue
                   //DBRef.setValue(EDT.getText().toString());

                   //Adds both ob1, ob2 to a new unique child under Temp
                   //tempRef.push().setValue(curTandD, EDT.getText().toString());

                   EDT.setText(""); //Reset text within EditText box to empty
               }
           }
        });


    }



    //creates 2 children under Temp, named Date & Value. clears all other values though.
    private void createPost(String DT, String DataT) {
        //Create hashmap with 2 inputs, and assign to the map 'postValues'
        Map<String, Object> postValues = new HashMap<String, Object>();
        postValues.put("Date", DT);
        postValues.put("Value", DataT);

        //Reference Temp within the DB, pushing the mapped child values. WORKS
        DBRef.child("Temp").push().updateChildren(postValues);
    }


}
