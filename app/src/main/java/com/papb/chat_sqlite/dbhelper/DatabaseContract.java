package com.papb.chat_sqlite.dbhelper;

import android.provider.BaseColumns;


public class DatabaseContract {

    static String TABLE_USER = "table_user";
    static String TABLE_CHAT = "table_chat";

    static final class UserColumns implements BaseColumns {

        static String USERNAME = "username";
        static String PASSWORD = "password";

    }

    static final class ChatColumns implements BaseColumns {

        static String USERID = "userid";
        static String MESSAGE = "username";
        static String CREATEDAT = "createdat";

    }
}