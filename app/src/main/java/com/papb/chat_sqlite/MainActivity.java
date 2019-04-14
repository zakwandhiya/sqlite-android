package com.papb.chat_sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.papb.chat_sqlite.dbhelper.ChatHelper;
import com.papb.chat_sqlite.model.ChatModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText message , searchEditText;
    private ImageView tambah;

    private ChatAdapter chatAdapter;
    private ChatHelper chatHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.list_of_message);

        message = (EditText) findViewById(R.id.edit_text);
        tambah = (ImageView) findViewById(R.id.send_button);
        searchEditText = (EditText) findViewById(R.id.search_edit_text);

        chatHelper = new ChatHelper(this);
        chatAdapter = new ChatAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllData();

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (message.getText().toString().isEmpty()) {


                } else {
                    insertData();
                    getAllData();
                }
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {

                    chatHelper.open();
                    // Ambil semua data mahasiswa di database
                    ArrayList<ChatModel> mahasiswaModels = chatHelper.getAllData();
                    chatHelper.close();
                    ArrayList<ChatModel> tmpChat2 =  new ArrayList<ChatModel>();

                    for(ChatModel singleChat : mahasiswaModels){
                        if (singleChat.getMessage().contains(s))
                        tmpChat2.add(singleChat);
                    }
                    chatAdapter.addItem(tmpChat2);
                } else {
                    chatAdapter.addItem(chatHelper.getAllData());
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_profile_button) {

            Intent regIntent = new Intent(MainActivity.this , EditProfileActivity.class);
            regIntent.putExtra("USERID" , getIntent().getIntExtra("USERID" , 1));
            startActivity(regIntent);
        } else if (id == R.id.logout_button){

            Intent regIntent = new Intent(MainActivity.this , LoginActivity.class);
            startActivity(regIntent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    private void insertData() {

        chatHelper.open();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        ChatModel chatModel = new ChatModel( getIntent().getIntExtra("USERID" , 1), message.getText().toString(), dateFormat.format(date).toString());
        message.setText("");
        chatHelper.insert(chatModel);
        chatHelper.close();
    }

    private void getAllData() {
        chatHelper.open();
        // Ambil semua data mahasiswa di database
        ArrayList<ChatModel> mahasiswaModels = chatHelper.getAllData();
        chatHelper.close();
        chatAdapter.addItem(mahasiswaModels);
        recyclerView.setAdapter(chatAdapter);
    }
}
