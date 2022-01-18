package com.example.database_practice2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static String dbName = "groupTBL.db";
    public static int VERSION = 1;



    public DataBaseHelper(Context context) {
        //DataBaseHelper 생성자

        super(context, dbName, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //테이블 생성


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //테이블 업그레이드

        //int oldVersion : 이전 데이터베이스 버전
        //int newVersion : 새 데이터베이스 버전

        if (newVersion > 1) {
            db.execSQL("DROP TABLE IF EXISTS groupTBL");
        }
    }

}