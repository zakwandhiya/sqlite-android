package com.papb.chat_sqlite;

import android.content.Intent;
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

public class EditProfileActivity extends AppCompatActivity {

    private EditText txtUsername, txtPass , txtRepass;
    private Button btnUpdate;
    private TextView btnDelete;
    private ProgressBar progressBar;

    private UserHelper userHelper;

    private ArrayList<UserModel> userModels;
    private UserModel currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        txtUsername = findViewById(R.id.profile_username);
        txtPass = findViewById(R.id.profile_password);
        txtRepass = findViewById(R.id.profile_repassword);
        btnUpdate = findViewById(R.id.profile_button_update);
        btnDelete = findViewById(R.id.profile_button_to_delete_account);

        userHelper = new UserHelper(this);

        progressBar = findViewById(R.id.profile_progresbar);
        progressBar.setVisibility(View.INVISIBLE);

        getAllData();


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userHelper.open();
                userHelper.delete(getIntent().getIntExtra("USERID" , 1));
                userHelper.close();
                Intent regIntent = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(regIntent);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUsername.getText().toString().isEmpty() || txtPass.getText().toString().isEmpty() || txtRepass.getText().toString().isEmpty()){
                    Toast.makeText(EditProfileActivity.this, "Isi semua field terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else if(!txtPass.getText().toString().equals(txtRepass.getText().toString())){
                    Toast.makeText(EditProfileActivity.this, "password dan repassword tidak sama", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    update();
                }
            }
        });
    }




    private void getAllData() {
        userHelper.open();
        userModels = userHelper.getAllData();
        userHelper.close();


        boolean b = false;
        for (UserModel singleUser : userModels) {
            if(singleUser.getId() == getIntent().getIntExtra("USERID" , 1)){
                currentUser = singleUser;
                txtUsername.setText(singleUser.getUsername());
                txtPass.setText(singleUser.getPassword());
                txtRepass.setText(singleUser.getPassword());
            }
        }

    }

    private void update() {
        userHelper.open();
        currentUser.setUsername(txtUsername.getText().toString());
        currentUser.setPassword(txtPass.getText().toString());
        userHelper.update(currentUser);
        userHelper.close();


        Intent regIntent = new Intent(EditProfileActivity.this, MainActivity.class);
        startActivity(regIntent);
        finish();
    }
}


