package com.example.tournameapp.App;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tournameapp.R;
import com.example.tournameapp.Utils.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // String user = "aaaa";
        String pass = "123";

//        Intent intent = new Intent(this,LoginActivity.class);
//        intent.putExtra("Username",user);
//        intent.putExtra("Password",pass);
//        startActivity(intent);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");


    }
}
