package com.example.tournameapp.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.tournameapp.R;
import com.example.tournameapp.interfaces.OnLoginListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.presenters.LoginPresenter;

/**
 * Activity that presents the login form
 *
 * @author Alon Perlmuter
 * @author Yishay Garame
 * @author Dovie Chitiz
 */
public class LoginActivity extends AppCompatActivity implements OnLoginListener {

    private TextView usernameTxt;
    private TextView passwordTxt;
    private CheckBox rememberMeCb;
    private Button signInBtn;
    private Button managerSignUpBtn;
    private Button playerSignUpBtn;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // init widgets
        usernameTxt = (TextView) findViewById(R.id.usernameTxt);
        passwordTxt = (TextView) findViewById(R.id.passwordTxt);
        rememberMeCb = (CheckBox) findViewById(R.id.rememberMeCb);
        signInBtn = (Button) findViewById(R.id.signInBtn);
        managerSignUpBtn = (Button) findViewById(R.id.signUpBtn);
        playerSignUpBtn = (Button) findViewById(R.id.playerSignUpBtn);

        // init presenter and check if user is already logged
        loginPresenter = new LoginPresenter(this);
        loginPresenter.checkLoggedUser();

        // handle clicks events
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = usernameTxt.getText().toString();
                String password = passwordTxt.getText().toString();
                boolean rememberMe = rememberMeCb.isChecked();
                loginPresenter.logIn(userName, password, rememberMe);

            }
        });

        managerSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignUpActivity.class);
                intent.putExtra("signAs","Manager");
                startActivity(intent);
            }
        });

        playerSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignUpActivity.class);
                intent.putExtra("signAs","Player");
                startActivity(intent);
            }
        });
    }

    /**
     * @return SharedPreferences object of this context
     */
    @Override
    public SharedPreferences getSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("rememberMe",MODE_PRIVATE);
        return sharedPreferences;
    }

    /**
     * set error message on username text field
     * @param message message to show
     */
    @Override
    public void onUserNameError(String message) {
        usernameTxt.setError(message);
    }

    /**
     * set error message on password text field
     * @param message message to show
     */
    @Override
    public void onPasswordError(String message) {
        passwordTxt.setError(message);
    }

    /**
     * go to manager dashboard
     * @param manager manager that logs in
     */
    @Override
    public void onLogin(Manager manager) {
        Intent intent = new Intent(this, ManagerDashboardActivity.class);
        intent.putExtra("loggedUser",manager.getUserName());
        startActivity(intent);
    }


    /**
     * go to player dashboard
     * @param player player that logs in
     */
    @Override
    public void onLogin(Player player) {
        Intent intent = new Intent(this, PlayerDashboardActivity.class);
        intent.putExtra("loggedUser",player.getUserName());
        startActivity(intent);
    }


}
