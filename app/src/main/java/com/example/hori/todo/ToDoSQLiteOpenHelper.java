package com.example.hori.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hori on 2015/01/01.
 */
public class ToDoSQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

        public static final String TODO_TABLE = "todo";
        static final String DB = "todo.db";
        static final int DB_VERSION =1;
        static final String CREATE_TABLE = "create table "
                + TODO_TABLE
                + "(_id integer primary key autoincrement, formula text)";
        static final String DROP_TABLE = "drop table" + TODO_TABLE;

        public ToDoSQLiteOpenHelper(Context context) {
            super(context, DB, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
            sqLiteDatabase.execSQL(DROP_TABLE);
            onCreate(sqLiteDatabase);
        }



}
