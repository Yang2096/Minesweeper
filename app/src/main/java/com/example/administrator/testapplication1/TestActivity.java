package com.example.administrator.testapplication1;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
    //进入游戏界面
    public void game_start(View view1 ){

        EditText gamelevel=(EditText) findViewById(R.id.gameLevel);
        String  message = gamelevel.getText().toString();
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(message);
        if(!m.matches() ){
            Toast.makeText(TestActivity.this,"请输入5-15的数字", Toast.LENGTH_SHORT).show();
        }else {
            if(message.length()>9){
                Toast.makeText(TestActivity.this, "为保证游戏体验，请降低游戏难度", 0).show();
            }else{
                int level = Integer.parseInt(message);
                if (level < 5) {
                    Toast.makeText(TestActivity.this, "此难度仅适用5岁以下幼儿", 0).show();
                } else if (level > 15) {
                    Toast.makeText(TestActivity.this, "为保证游戏体验，请降低游戏难度", 0).show();
                } else {
                    Intent intent = new Intent(this, GameActivity.class);
                    intent.putExtra("level_data", message);
                    startActivity(intent);
                }
            }

        }
    }

    //进入英雄榜界面
    public void showRankList(View view) {
        Intent intent = new Intent(this, RankListActivity.class);
        startActivity(intent);
    }

    //查看制作人员信息
    public void showStuff(View view) {

        new AlertDialog.Builder(this)
                .setTitle("制作人员")
                .setMessage("yang2096@outlook.com")
                .setPositiveButton("朕知道了", null)
                .show();
    }
}
