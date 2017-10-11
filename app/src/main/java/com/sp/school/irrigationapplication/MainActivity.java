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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "MainActivity";
    private Button mSendData;

    FirebaseDatabase FDB;
    DatabaseReference DBRef;
    DatabaseReference userRef;
    TextView TV;
    EditText EDT;
    Button Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //DatabaseReference myRef = database.getReference("message");
        //mSendDate = (Button) findViewById(R.id.sendData);
        FDB = FirebaseDatabase.getInstance();
        DBRef = FDB.getReference("Temp");
        //userRef = FirebaseDatabase.getInstance().getReference();
        //userRef.push().setValue(1);

        TV = (TextView)findViewById(R.id.textFromDB);
        EDT = (EditText)findViewById(R.id.editText);
        Save = (Button)findViewById(R.id.sendData);
        //Reading
        DBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TV.setText(dataSnapshot.getValue().toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        
        //Writing
        Save.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
               if(EDT.getText().toString().isEmpty()){
                   Toast.makeText(getApplicationContext(), "Please Write", Toast.LENGTH_SHORT).show();
               }else{
                   //this is a test
                   // This changes the Value of the Child Within Temp without changing the nameValue
                   DBRef.setValue(EDT.getText().toString());
                   //This changes the value of the child within TEMP and the NameValue
                   //DBRef.push().setValue(EDT.getText().toString());
                   //DBRef.updateChildren("Temp",EDT.getText().toString());

                   //What does this do?
                   //userRef.child("Temp").push().setValue(EDT.getText().toString());
                   EDT.setText("");
               }
           }
        });


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }
    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    /*
    public void createAccount() {
        mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Toast.makeText(EmailPasswordActivity.this, R.string.auth_failed,
                            Toast.LENGTH_SHORT).show();
                }

                // ...
            }
        });
    }
    */

    /*
    public void signIn(){
        mAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                    Toast.makeText(EmailPasswordActivity.this, R.string.auth_failed,
                            Toast.LENGTH_SHORT).show();
                }

                // ...
            }
        });
    }
    */


}
