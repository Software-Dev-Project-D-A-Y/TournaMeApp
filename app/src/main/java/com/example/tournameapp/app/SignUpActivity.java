package com.example.tournameapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tournameapp.R;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Player;

public class SignUpActivity extends AppCompatActivity {

    private TextView signAsTxt;
    private String signAs;

    private TextView firstNameSignUpTxt;
    private TextView lastNameSignUpTxt;
    private TextView usernameSignUpTxt;
    private TextView ageSignUpTxt;
    private TextView passwordSignUpTxt;
    private TextView confirmPassSignUpTxt;
    private TextView emailSignUpTxt;
    private Button signUpBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();
        signAs = intent.getExtras().getString("signAs");

        firstNameSignUpTxt = (TextView) findViewById(R.id.firstNameSignUpTxt);
        lastNameSignUpTxt = (TextView) findViewById(R.id.lastNameSignUpTxt);
        usernameSignUpTxt = (TextView) findViewById(R.id.usernameSignUpTxt);
        passwordSignUpTxt= (TextView) findViewById(R.id.passwordSignUpTxt);
        confirmPassSignUpTxt = (TextView) findViewById(R.id.confirmPassSignUpTxt);
        ageSignUpTxt = (TextView) findViewById(R.id.ageSignUpTxt);
        emailSignUpTxt = (TextView) findViewById(R.id.emailSignUpTxt);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);

        signAsTxt = (TextView) findViewById(R.id.signAsTxt);
        signAsTxt.setText("Signing as "+signAs);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameSignUpTxt.getText().toString();
                String lastName = lastNameSignUpTxt.getText().toString();
                String username = usernameSignUpTxt.getText().toString();
                String password = passwordSignUpTxt.getText().toString();
                String confirmPassword = confirmPassSignUpTxt.getText().toString();
                int age =Integer.parseInt( ageSignUpTxt.getText().toString());
                String email = emailSignUpTxt.getText().toString();


              //  Validadtion CHECK




                switch (signAs) {
                    case "Manager":
                        Manager newManager = new Manager(firstName,lastName,age,email,username,password);

                        // Insertion to database
                        Log.d("Manager","Insert to db");

                        break;

                    case "Player":
                        Player newPlayer = new Player(firstName,lastName,age,email,username,password);

                        // Insertion to database
                        Log.d("Player","Insert to db");

                        break;
                    default:
                        break;
                }
                Intent intent = new Intent(v.getContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
