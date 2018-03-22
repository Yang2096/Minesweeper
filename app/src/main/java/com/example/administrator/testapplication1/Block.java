package com.example.administrator.testapplication1;

/**
 * Created by Administrator on 2016/12/24.
 */
//棋盘上的方块

public class Block {
    private int stata;
    private int leiCount;
    private boolean isShow; //是否显示
    private boolean isFlag;
    private boolean isAutoShow;  //是否是自动显示的(非手点)
    Block(){
        stata=0;
        leiCount=0;
        isFlag=false;
        isShow=false;
        isAutoShow=false;
    }
    public boolean isLei(){
        return stata == 1;
    }
    public boolean isFlag(){
        return isFlag;
    }
    public boolean isShow(){
        return isShow;
    }
    public int countLei(){
        return leiCount;
    }
    public boolean isAutoShow() {
        return isAutoShow;
    }
    public void setLei(){
        stata = 1;
    }
    public void setFlag(boolean i){
        isFlag=i;
    }
    public void setLeiCount(int count){
        leiCount=count;
    }
    public void setShow(boolean i){
        isShow=i;
    }
    public void setAutoShow(boolean i){
        isAutoShow = i;
    }

}
