package com.example.administrator.testapplication1;

/**
 * Created by Administrator on 2016/12/25.
 */
//用户类
public class Hero {

    private String name;

    private int score;

    private int level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public Hero(String name, int score,int level) {
        super();
        this.name = name;
        this.score = score;
        this.level = level;
    }


    @Override
    public String toString() {
        return "姓名: "+name+"  成绩: "+score+"  难度: "+level;
    }
}
