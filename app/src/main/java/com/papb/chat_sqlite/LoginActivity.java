package com.papb.chat_sqlite;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.papb.chat_sqlite.dbhelper.UserHelper;
import com.papb.chat_sqlite.model.UserModel;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsername, txtPass ;
    private Button btnLogin;
    private TextView btnRegister;
    private ProgressBar progressBar;

    private UserHelper userHelper;
    private ArrayList<UserModel> userModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        txtUsername = findViewById(R.id.login_username);
        txtPass = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_button_login);
        btnRegister = findViewById(R.id.login_button_to_register);

        progressBar = findViewById(R.id.login_progresbar);
        progressBar.setVisibility(View.INVISIBLE);

        userHelper = new UserHelper(this);
        getAllData();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regIntent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUsername.getText().toString().isEmpty() || txtPass.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Isi semua field terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    login(txtUsername.getText().toString(), txtPass.getText().toString());
                }
            }
        });
    }

    private void login (String username, String password){
        Log.d("=====================", "username input "+ username);
        Log.d("=====================", "password input "+ password);
        boolean b = false;
        for (UserModel singleUser : userModels) {

            Log.d("=====================", "username database "+ singleUser.getUsername());
            Log.d("=====================", "password database "+ singleUser.getPassword());
            if(singleUser.getUsername().equals(username) && singleUser.getPassword().equals(password)){
                b = true;
                Intent regIntent = new Intent(LoginActivity.this, MainActivity.class);
                regIntent.putExtra("USERID" , singleUser.getId());
                startActivity(regIntent);
                finish();
            }
        }

        if(!b){
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(LoginActivity.this, "Username dan password tidak cocok", Toast.LENGTH_SHORT).show();
        }
    }

    private void getAllData() {
        userHelper.open();
        userModels = userHelper.getAllData();
        userHelper.close();
    }
}
