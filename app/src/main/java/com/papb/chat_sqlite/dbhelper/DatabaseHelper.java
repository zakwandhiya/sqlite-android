package com.papb.chat_sqlite.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.papb.chat_sqlite.dbhelper.DatabaseContract.ChatColumns.USERID;
import static com.papb.chat_sqlite.dbhelper.DatabaseContract.UserColumns.USERNAME;
import static com.papb.chat_sqlite.dbhelper.DatabaseContract.UserColumns.PASSWORD;
import static com.papb.chat_sqlite.dbhelper.DatabaseContract.TABLE_USER;
import static com.papb.chat_sqlite.dbhelper.DatabaseContract.ChatColumns.MESSAGE;
import static com.papb.chat_sqlite.dbhelper.DatabaseContract.ChatColumns.CREATEDAT;
import static com.papb.chat_sqlite.dbhelper.DatabaseContract.TABLE_CHAT;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    public static String CREATE_TABLE_USER = "create table " + TABLE_USER +
            " (" + _ID + " integer primary key autoincrement, " +
            USERNAME + " text not null, " +
            PASSWORD + " text not null);";
    public static String CREATE_TABLE_CHAT = "create table " + TABLE_CHAT +
            " (" + _ID + " integer primary key autoincrement, " +
            USERID + " integer not null, " +
            MESSAGE + " text not null, " +
            CREATEDAT + " text not null);";
    private static String DATABASE_NAME = "dbchatapp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_CHAT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAT);
        onCreate(db);
    }


}
