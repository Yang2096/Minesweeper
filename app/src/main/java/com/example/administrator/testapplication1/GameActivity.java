package com.example.administrator.testapplication1;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;
import android.view.View;
import java.util.Timer;
import java.util.TimerTask;




public class GameActivity extends AppCompatActivity {

    private GridView gameview;
    private Handler handler;
    private Timer timer;
    private LeiAdapter adapter;
    private TextView showTime;
    private int nowTime;
    private int level;
    private boolean isGaming;
    private ImageView emjimage;
    private TextView lastBoom;
    private HeroList heroList;
    private static final int MESSAGE_UPDATE_TIME = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        heroList = new HeroList(this);
        Intent intent1=getIntent();

        level=Integer.parseInt(intent1.getStringExtra("level_data"));

        showTime = (TextView)findViewById(R.id.time);

        handler = new Handler(){

            public void handleMessage(Message msg) {
                if(msg.what == MESSAGE_UPDATE_TIME){
                    showTime.setText(Integer.toString(nowTime));
                }
            }
        };

        timer = new Timer();

        emjimage =(ImageView) findViewById(R.id.EmjView);

        gameview = (GridView) findViewById(R.id.gameView);

        adapter = new LeiAdapter(level,this,gameview);

        gameview.setNumColumns(level);
        gameview.setAdapter(adapter);
        addListener();
        startGame();

    }

    private void startGame(){
        timer.cancel();
        timer = new Timer();
        isGaming = true;
        nowTime = 0;
        adapter = new LeiAdapter(level, GameActivity.this, gameview);
        gameview.setAdapter(adapter);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                nowTime++;
                handler.sendEmptyMessage(MESSAGE_UPDATE_TIME);
            }
        }, 0, 1000);
        lastBoom = (TextView) findViewById(R.id.boomView);
        lastBoom.setText(Integer.toString(adapter.lastboom()));
        Toast.makeText(GameActivity.this, "游戏开始!", 0).show();

    }

    private void stopGame(){
        timer.cancel();
        isGaming = false;
    }

    private void addListener(){


        /**长按格子添加旗子*/
        gameview.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(!isGaming){
                    return true;
                }
                Block item = adapter.getItem(position);
                if(item.isFlag())
                    adapter.addFlag(-1);
                else
                    adapter.addFlag(1);

                if(adapter.lastboom()<0){
                    adapter.addFlag(-1);
                    return true;
                }

                if(item.isShow()){        //该单元格是显示状态，则长按不生效
                    return true;
                }
                item.setFlag(!item.isFlag());   //两次标记等于没标记

                lastBoom.setText(Integer.toString(adapter.lastboom()));
                adapter.notifyDataSetChanged();

                return true;
            }

        });

        gameview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!isGaming){
                    return ;
                }
                Block entity = adapter.getItem(position);
                if(entity.isFlag()){    //该单元格被标记，则点击不生效
                    return;
                }
                if(!entity.isShow()){
                    if(entity.isLei()){    //点击的单元格是雷，则游戏结束
                        entity.setShow(true);
                        stopGame();
                        Toast.makeText(GameActivity.this, "游戏失败!", 0).show();
                        adapter.showLei(position);
                        emjimage.setImageDrawable(getResources().getDrawable(R.drawable.no));
                        return;
                    }
                    //点击的是空白单元格，则显示周围的空白单元格
                    if(!entity.isLei()){
                        adapter.showEmpty(position);
                        lastBoom.setText(Integer.toString(adapter.lastboom()));
                    }
                    entity.setShow(true);
                    //判断游戏是否胜利
                    if(adapter.isWin()){
                        stopGame();
                        adapter.showLei(0);
                        emjimage.setImageDrawable(getResources().getDrawable(R.drawable.win));
                        View dialogView = getLayoutInflater().inflate(R.layout.dialog_win_name, null);
                        final EditText et = (EditText) dialogView.findViewById(R.id.editText1);
                        new AlertDialog.Builder(GameActivity.this).setTitle("恭喜壮士过关").setPositiveButton("递交成绩", new Dialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String userName = et.getText().toString().trim();
                                if(userName == null || "".equals(userName)){
                                    Toast.makeText(GameActivity.this, "大侠神龙见首不见尾,此次成绩将不保存", 0).show();
                                }else{
                                    Hero hero1= new Hero(userName, nowTime,level);
                                    heroList.addHero(hero1);
                                    finish();
                                }

                            }
                        }).setNegativeButton("取消", new Dialog.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    finish();
                            }
                        }).setView(dialogView).create().show();

                    }
                    adapter.notifyDataSetChanged();

                }
            }

        });
    }


}
