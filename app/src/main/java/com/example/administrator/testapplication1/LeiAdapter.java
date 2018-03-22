package com.example.administrator.testapplication1;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.view.View;
import android.view.LayoutInflater;

/**
 * Created by Administrator on 2016/12/24.
 */

public class LeiAdapter extends BaseAdapter {
    private int level;
    private gameQipan gameQp; //棋盘类
    private GridView gameView;
    private Context context;

    LeiAdapter(int level, Context context,GridView gv){
        this.level=level;
        gameQp =new gameQipan(level);
        this.gameView=gv;
        this.context=context;
        System.out.print("-");
    }
    @Override
    public int getCount(){
        return level*level;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.block_item, null);
        }
        ((ImageView)convertView).setImageResource(getRes(getItem(position)));
        AbsListView.LayoutParams param = new AbsListView.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                gameView.getWidth()/level);
        convertView.setLayoutParams(param);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**根据格子状态调用不同的图片*/
    private int getRes(Block item){
        int id = 0;
        if(!item.isShow() && !item.isFlag()) {
            id = R.drawable.nothing;
        }else if(item.isLei()  && !item.isFlag() && item.isAutoShow()) {
            id = R.drawable.boom;       //显示雷
        }else if(item.isFlag() && !item.isShow()){
            id = R.drawable.flag;       //标记
        }else if(item.isFlag() && !item.isLei() ){
            id = R.drawable.checked;      //标记失败
        }else if(item.isFlag() && item.isLei() && item.isAutoShow()) {
            id = R.drawable.boomright;      //标记成功
        }else if(!item.isFlag() && item.isLei() && !item.isAutoShow()){
            id = R.drawable.boomwrong;      //点中了雷
        }else {
            id = context.getResources().getIdentifier("tu"+item.countLei(), "drawable", context.getPackageName());
        }
        return id;
    }


    @Override
    public Block getItem(int position) {
        return gameQp.getItem(position);
    }

    public boolean isWin(){
        return gameQp.isWin();
    }

    public void showLei(int position){
        gameQp.showLei(position);
        notifyDataSetChanged();

    }

    public void showEmpty(int position){
        gameQp.showEmpty(position);
        notifyDataSetChanged();
    }

    public void addFlag(int d){
        gameQp.addFlag(d);
    }
    public int lastboom(){
        return gameQp.lastboom();
    }

}
