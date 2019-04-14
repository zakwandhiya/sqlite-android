package com.papb.chat_sqlite.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.papb.chat_sqlite.model.ChatModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.papb.chat_sqlite.dbhelper.DatabaseContract.TABLE_CHAT;
import static com.papb.chat_sqlite.dbhelper.DatabaseContract.ChatColumns.USERID;
import static com.papb.chat_sqlite.dbhelper.DatabaseContract.ChatColumns.MESSAGE;
import static com.papb.chat_sqlite.dbhelper.DatabaseContract.ChatColumns.CREATEDAT;


public class ChatHelper {

    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public ChatHelper(Context context) {
        this.context = context;
    }

    public ChatHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }


    public ArrayList<ChatModel> getAllData() {
        Cursor cursor = database.query(TABLE_CHAT, null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        ArrayList<ChatModel> arrayList = new ArrayList<>();
        ChatModel chatModel;
        if (cursor.getCount() > 0) {
            do {
                chatModel = new ChatModel();
                chatModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                chatModel.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(USERID)));
                chatModel.setMessage(cursor.getString(cursor.getColumnIndexOrThrow(MESSAGE)));
                chatModel.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(CREATEDAT)));
                arrayList.add(chatModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(ChatModel chatModel) {
        Log.d("=====================", "id user "+ chatModel.getUserId());
        Log.d("=====================", "message user "+ chatModel.getMessage());
        Log.d("=====================", "created at"+ chatModel.getCreatedAt());
        ContentValues initialValues = new ContentValues();
        initialValues.put(USERID, chatModel.getUserId());
        initialValues.put(MESSAGE, chatModel.getMessage());
        initialValues.put(CREATEDAT, chatModel.getCreatedAt());
        return database.insert(TABLE_CHAT, null, initialValues);
    }


    public int update(ChatModel chatModel) {
        ContentValues args = new ContentValues();
        args.put(USERID, chatModel.getUserId());
        args.put(MESSAGE, chatModel.getMessage());
        args.put(CREATEDAT, chatModel.getCreatedAt());
        return database.update(TABLE_CHAT, args, _ID + "= '" + chatModel.getId() + "'", null);
    }


    public int delete(int id) {
        return database.delete(TABLE_CHAT, _ID + " = '" + id + "'", null);
    }

}
