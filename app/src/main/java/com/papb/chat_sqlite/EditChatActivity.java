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

import com.papb.chat_sqlite.dbhelper.ChatHelper;
import com.papb.chat_sqlite.model.ChatModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditChatActivity extends AppCompatActivity {

    private EditText txtChat;
    private Button btnUpdate;
    private TextView btnDelete;
    private ProgressBar progressBar;


    private ChatHelper chatHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chat);


        txtChat = findViewById(R.id.chat_text);
        txtChat.setText(getIntent().getStringExtra("EDIT_MESSAGE"));
        btnUpdate = findViewById(R.id.chat_button_update);
        btnDelete = findViewById(R.id.chat_button_to_delete_message);

        progressBar = findViewById(R.id.chat_progresbar);
        progressBar.setVisibility(View.INVISIBLE);


        chatHelper = new ChatHelper(this);


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtChat.getText().toString().isEmpty()){
                    Toast.makeText(EditChatActivity.this, "Isi semua field terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    update(txtChat.getText().toString());
                }
            }
        });
    }

    private void update (String tmpMessage){

        chatHelper.open();
        ChatModel chatModel = new ChatModel( getIntent().getIntExtra("EDIT_ID" , 1),getIntent().getIntExtra("EDIT_USER_ID" , 1), tmpMessage, getIntent().getStringExtra("EDIT_CREATED_AT" ));

        txtChat.setText("");
        chatHelper.update(chatModel);
        chatHelper.close();


        Intent regIntent = new Intent(EditChatActivity.this ,  MainActivity.class);
        regIntent.putExtra("USERID" , getIntent().getIntExtra("EDIT_USER_ID" , 1));
        startActivity(regIntent);
    }

    private void delete (){

        chatHelper.open();

        chatHelper.delete(getIntent().getIntExtra("EDIT_ID" , 1));
        chatHelper.close();


        Intent regIntent = new Intent(EditChatActivity.this ,  MainActivity.class);
        regIntent.putExtra("USERID" , getIntent().getIntExtra("EDIT_USER_ID" , 1));
        startActivity(regIntent);
    }
}
