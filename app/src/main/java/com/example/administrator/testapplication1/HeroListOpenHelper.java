package com.example.administrator.testapplication1;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
/**
 * Created by Administrator on 2016/12/25.
 */

public class HeroListOpenHelper extends SQLiteOpenHelper {

    private static final String CREATE_TABLE = "create table score_info(_id integer primary key autoincrement,hero_name text,score integer,level integer)";

    HeroListOpenHelper(Context context, int version) {
        super(context, "score_info", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
