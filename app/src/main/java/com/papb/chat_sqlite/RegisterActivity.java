package com.papb.chat_sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.papb.chat_sqlite.dbhelper.UserHelper;
import com.papb.chat_sqlite.model.UserModel;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtUsername, txtPass , txtRepass;
    private Button btnRegister;
    private TextView btnLogin;
    private ProgressBar progressBar;

    private UserHelper userHelper;
    private ArrayList<UserModel> userModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        txtUsername = findViewById(R.id.register_username);
        txtPass = findViewById(R.id.register_password);
        txtRepass = findViewById(R.id.register_repassword);
        btnRegister = findViewById(R.id.register_button_register);
        btnLogin = findViewById(R.id.register_button_to_login);

        progressBar = findViewById(R.id.register_progresbar);
        progressBar.setVisibility(View.INVISIBLE);


        userHelper = new UserHelper(this);
        getAllData();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(regIntent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUsername.getText().toString().isEmpty() || txtPass.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Isi semua field terlebih dahulu", Toast.LENGTH_SHORT).show();
                }else if(!txtPass.getText().toString().equals(txtRepass.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "password dan repassword tidak sama", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    register(txtUsername.getText().toString(), txtPass.getText().toString());
                }
            }
        });
    }

    private void register (String username, String password){
        boolean b = false;
        for (UserModel singleUser : userModels) {
            if(singleUser.getUsername().equals(username)){
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(RegisterActivity.this, "Username sudah digunakan", Toast.LENGTH_SHORT).show();
                b = true;
            }
        }

        if(!b){
            insertData(username , password);
            getAllData();
            for (UserModel currentUser : userModels) {
                if(currentUser.getUsername().equals(username) && currentUser.getPassword().equals(password)){
                    Intent regIntent = new Intent(RegisterActivity.this, MainActivity.class);
                    regIntent.putExtra("USERID" , currentUser.getId());
                    startActivity(regIntent);
                    finish();
                }
            }
        }
    }

    private void getAllData() {
        userHelper.open();
        userModels = userHelper.getAllData();
        userHelper.close();
    }

    private void insertData(String username , String password) {
        userHelper.open();
        UserModel tmpUser = new UserModel(username , password);
        userHelper.insert(tmpUser);
        userHelper.close();
    }
}
