package com.example.tournameapp.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tournameapp.R;
import com.example.tournameapp.interfaces.OnSignUpListener;
import com.example.tournameapp.presenters.SignUpPresenter;

/**
 * Activity that presents the sign-up form
 *
 * @author Alon Perlmuter
 * @author Yishay Garame
 * @author Dovie Chitiz
 */
public class SignUpActivity extends AppCompatActivity implements OnSignUpListener {

    // widgets
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

    // presenter
    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // init presenter
        signUpPresenter = new SignUpPresenter(this);

        Intent intent = getIntent();
        signAs = intent.getExtras().getString("signAs");

        // init widgets
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
                String email = emailSignUpTxt.getText().toString();

                int age = -1;
                try {
                    age = Integer.parseInt( ageSignUpTxt.getText().toString());
                } catch (Exception e1) {
                    age = -1;
                }

                // passing data to the presenter
                switch (signAs) {
                    case "Manager":
                        signUpPresenter.signUpManager(firstName,lastName,age,email,username,password,confirmPassword);
                        break;

                    case "Player":
                        signUpPresenter.signUpPlayer(firstName,lastName,age,email,username,password,confirmPassword);
                        break;
                    default:
                        break;
                }
            }
        });
    }


    /**
     * set error message on first name text field
     * @param message message to show
     */
    @Override
    public void onFirstNameError(String message) {
        firstNameSignUpTxt.setError(message);
    }

    /**
     * set error message on last name text field
     * @param message message to show
     */
    @Override
    public void onLastNameError(String message) {
        lastNameSignUpTxt.setError(message);
    }


    /**
     * set error message on age text field
     * @param message message to show
     */
    @Override
    public void onAgeError(String message) {
        ageSignUpTxt.setError(message);
    }


    /**
     * set error message on username text field
     * @param message message to show
     */
    @Override
    public void onUsernameError(String message) {
        usernameSignUpTxt.setError(message);
    }


    /**
     * set error message on password text field
     * @param message message to show
     */
    @Override
    public void onPasswordError(String message) {
        passwordSignUpTxt.setError(message);
    }


    /**
     * set error message on confirm-password text field
     * @param message message to show
     */
    @Override
    public void onMatchedPasswordsError(String message) {
        confirmPassSignUpTxt.setError(message);
    }


    /**
     * set error message on email text field
     * @param message message to show
     */
    @Override
    public void onEmailError(String message) {
        emailSignUpTxt.setError(message);
    }


    /**
     * returning to LoginActivity when sign-up succeed
     */
    @Override
    public void onSignUp() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
