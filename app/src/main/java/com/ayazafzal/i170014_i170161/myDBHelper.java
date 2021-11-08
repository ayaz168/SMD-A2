package com.ayazafzal.i170014_i170161;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class myDBHelper extends SQLiteOpenHelper {
    String CREATE_USERS_TABLE="CREATE TABLE "+
            myDBContracts.Users.TABLENAME+"("+
            myDBContracts.Users._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            myDBContracts.Users._EMAIL+ " TEXT NOT NULL, "+
            myDBContracts.Users._PASSWORD+ " TEXT NOT NULL, "+
            myDBContracts.Users._FIRSTNAME+ " TEXT NOT NULL, "+
            myDBContracts.Users._LASTNAME+ " TEXT NOT NULL, "+
            myDBContracts.Users._GENDER+ " TEXT NOT NULL, "+
            myDBContracts.Users._BIO+ " TEXT NOT NULL, "+
            myDBContracts.Users._STATUS+ " TEXT NOT NULL, "+
            myDBContracts.Users._PHONE+ " TEXT NOT NULL, "+
            myDBContracts.Users._IMAGE+ " TEXT);";
    String DELETE_USERS_TABLE="DROP TABLE IF EXISTS "+ com.ayazafzal.i170014_i170161.myDBContracts.Users.TABLENAME;
    String CREATE_FRIENDS_TABLE="CREATE TABLE "+
            myDBContracts.Friends.TABLENAME+"("+
            myDBContracts.Friends._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            myDBContracts.Friends._ID1+ " TEXT NOT NULL, "+
            myDBContracts.Friends._ID2+ " TEXT NOT NULL);";
    String DELETE_FRIENDS_TABLE="DROP TABLE IF EXISTS "+ com.ayazafzal.i170014_i170161.myDBContracts.Friends.TABLENAME;
    String CREATE_MESSAGES_TABLE="CREATE TABLE "+
            myDBContracts.Messages.TABLENAME+"("+
            myDBContracts.Messages._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            myDBContracts.Messages._SENDER+ " TEXT NOT NULL, "+
            myDBContracts.Messages._RECEIVER+ " TEXT NOT NULL, "+
            myDBContracts.Messages._HOUR+ " TEXT NOT NULL, "+
            myDBContracts.Messages._MINUTE+ " TEXT NOT NULL, "+
            myDBContracts.Messages._CALL+ " TEXT, "+
            myDBContracts.Messages._TEXTMESSAGE+ " TEXT, "+
            myDBContracts.Messages._IMAGEMESSAGE+ " TEXT, "+
            myDBContracts.Messages._SCREENSHOT+ " TEXT);";
    String DELETE_MESSAGES_TABLE="DROP TABLE IF EXISTS "+ myDBContracts.Messages.TABLENAME;

    public myDBHelper(@Nullable Context context) {
        super(context, com.ayazafzal.i170014_i170161.myDBContracts.DB_NAME, null, com.ayazafzal.i170014_i170161.myDBContracts.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(this.CREATE_USERS_TABLE);
        sqLiteDatabase.execSQL(this.CREATE_FRIENDS_TABLE);
        sqLiteDatabase.execSQL(this.CREATE_MESSAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(this.DELETE_USERS_TABLE);
        sqLiteDatabase.execSQL(this.DELETE_FRIENDS_TABLE);
        sqLiteDatabase.execSQL(this.DELETE_MESSAGES_TABLE);
        onCreate(sqLiteDatabase);
    }
}
