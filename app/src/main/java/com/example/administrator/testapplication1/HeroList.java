package com.example.administrator.testapplication1;

/**
 * Created by Administrator on 2016/12/25.
 */
//用户列表
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
public class HeroList {

    private Context context;
    private HeroListOpenHelper helper;

    private static final String TABLE_NAME = "score_info";
    private static final String ID = "_id";
    private static final String HERO_NAME = "hero_name";
    private static final String HERO_SCORE = "score";
    private static final String LEVEL = "level";

    HeroList(Context context){
        this.context=context;
        helper = new HeroListOpenHelper(context,1);
    }
    //添加用户
    public long addHero(Hero hero1){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HERO_NAME, hero1.getName());
        values.put(HERO_SCORE, hero1.getScore());
        values.put(LEVEL,hero1.getLevel());
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }
    //查询前十名的用户
    public ArrayList<Hero> getList(){
        ArrayList<Hero> scores = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME,  new String[]{ID,HERO_NAME,HERO_SCORE,LEVEL}, null, null, null, null, HERO_SCORE+","+ID+" desc", 10+"");
        if(c != null){
            scores = new ArrayList<Hero>();
            while(c.moveToNext()){
                scores.add(new Hero(c.getString(c.getColumnIndex(HERO_NAME)), c.getInt(c.getColumnIndex(HERO_SCORE)),c.getInt(c.getColumnIndex(LEVEL))));
            }
            c.close();
        }
        db.close();
        return scores;
    }
    //删除所有用户
    public int deleteAllUser(){
        SQLiteDatabase db = helper.getReadableDatabase();
        int count = db.delete(TABLE_NAME, null, null);
        db.close();
        return count;
    }

}
