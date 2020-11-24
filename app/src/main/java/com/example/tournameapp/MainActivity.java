package com.example.tournameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String user = "aaaa";
        String pass = "123";

//        Intent intent = new Intent(this,LoginActivity.class);
//        intent.putExtra("Username",user);
//        intent.putExtra("Password",pass);
//        startActivity(intent);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

        User user1 = new User(0,"Dovie","Chitiz","dovic","123");
        String userID = myRef.push().getKey();
        myRef.child(userID).setValue(user1);

        User user2 = new User(1,"Alon","Perlmuter","alonp","123");
        userID = myRef.push().getKey();
        myRef.child(userID).setValue(user2);


    }
}
