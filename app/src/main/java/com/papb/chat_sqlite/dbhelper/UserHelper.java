package com.papb.chat_sqlite.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.papb.chat_sqlite.model.UserModel;
import com.papb.chat_sqlite.model.ChatModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.papb.chat_sqlite.dbhelper.DatabaseContract.UserColumns.USERNAME;
import static com.papb.chat_sqlite.dbhelper.DatabaseContract.UserColumns.PASSWORD;
import static com.papb.chat_sqlite.dbhelper.DatabaseContract.TABLE_USER;


public class UserHelper {

    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public UserHelper(Context context) {
        this.context = context;
    }

    public UserHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }


    public ArrayList<UserModel> getAllData() {
        Cursor cursor = database.query(TABLE_USER, null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        ArrayList<UserModel> arrayList = new ArrayList<>();
        UserModel userModel;
        if (cursor.getCount() > 0) {
            do {
                userModel = new UserModel();
                userModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                userModel.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(USERNAME)));
                userModel.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD)));
                arrayList.add(userModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(UserModel userModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(USERNAME, userModel.getUsername());
        initialValues.put(PASSWORD, userModel.getPassword());
        return database.insert(TABLE_USER, null, initialValues);
    }


    public int update(UserModel userModel) {
        ContentValues args = new ContentValues();
        args.put(USERNAME, userModel.getUsername());
        args.put(PASSWORD, userModel.getPassword());
        return database.update(TABLE_USER, args, _ID + "= '" + userModel.getId() + "'", null);
    }


    public int delete(int id) {
        return database.delete(TABLE_USER, _ID + " = '" + id + "'", null);
    }

}
