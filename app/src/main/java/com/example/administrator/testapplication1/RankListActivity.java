package com.example.administrator.testapplication1;

import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.view.View.OnClickListener;

//英雄榜界面

public class RankListActivity extends AppCompatActivity {

    private ListView lv;
    private Button clearBtn;

    private ArrayList<Hero> scores;
    private HeroList heroList;
    private ArrayAdapter adapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_list);
        heroList = new HeroList(this);
        scores = heroList.getList();

        lv = (ListView)findViewById(R.id.ranklistview);
        clearBtn = (Button)findViewById(R.id.clear);
        addListener();
    }

    private void addListener(){
        adapter = new ArrayAdapter<Hero>(this, android.R.layout.simple_list_item_1, scores);
        lv.setAdapter(adapter);
        clearBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                heroList.deleteAllUser();
                scores.clear();
                adapter.notifyDataSetChanged();
            }
        });


    }

}
