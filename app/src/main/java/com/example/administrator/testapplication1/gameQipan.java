package com.example.administrator.testapplication1;

/**
 * Created by Administrator on 2016/12/24.
 */
import java.util.Random;

// 游戏棋盘
public class gameQipan {
    private Block[][] map;
    private int level;
    private int leiCount;
    private int flagCount;
    gameQipan(int level){
        this.level=level;
        leiCount=level*level/10;
        map = new Block[level+2][level+2];

        for(int i=1;i<=level;i++){
            for(int j=1;j<=level;j++){
                map[i][j]=new Block();
            }
        }

        Random rand = new Random(System.currentTimeMillis());
        //随机产生雷
        for(int x = 0;x<leiCount;x++){
            int i = rand.nextInt(level)+1;
            int j = rand.nextInt(level)+1;
            if(map[i][j].isLei()){
                x--;
                continue;
            }else{
                map[i][j].setLei();
            }
        }
        //统计各点周围的雷
        for(int i=1;i<=level;i++){
            for(int j=1;j<=level;j++){
                map[i][j].setLeiCount(countLei(i,j));
            }
        }
        //syso();  //不会扫雷可以对照这个点
    }
    private int countLei(int x,int y){
        int sum=0;
        for(int i=-1;i<2;i++){
            for(int j=-1;j<2;j++){
                if(x+i<1 || x+i>level || y+j<1 || y+j>level)
                    continue;
                else{
                    if(map[x+i][y+j].isLei())
                        sum++;
                }
            }
        }
        return sum;
    }
    public Block getItem(int position){
        Block entity = null;
        entity = map[position/level+1][position%level+1];
        return entity;
    }
    public boolean isWin(){
        int cnt=0;
        for(int i = 1;i<=level;i++){
            for(int j = 1;j<=level;j++){
                if(!map[i][j].isShow()){
                    cnt++;
                }
            }
        }
        return cnt == leiCount;
    }
    //显示雷
    public void showLei(int position){
        for(int i = 1;i<=level;i++){
            for(int j =1;j<=level;j++){
                if(map[i][j].isLei() && !map[i][j].isShow()){
                    map[i][j].setShow(true);
                    map[i][j].setAutoShow(true);
                }
            }
        }
        if(position!=0){
            map[position/level+1][position%level+1].setAutoShow(false);
        }

    }

    //显示无雷区域

    public void showEmpty(int position){
        if(position>=level*level || position<0){
            return;
        }
        int i = position/level+1;
        int j = position%level+1;

        if(map[i][j].countLei() != 0 || map[i][j].isShow()){
            map[i][j].setShow(true);
            return;
        }
        map[i][j].setShow(true);
        for(int ii = i-1;ii<=i+1;ii++){
            for(int jj = j-1;jj<=j+1;jj++){
                if(ii<=0 || jj<=0 || ii>=level+1 || jj>=level+1){
                    continue;
                }
                showEmpty((ii-1)*level+(jj-1));
            }
        }
    }
    //旗子计数
    public void addFlag(int d){
        flagCount+=d;
    }

    private void syso() {
        for(int i = 1;i<=level;i++){
            for(int j = 1;j<=level;j++){
                if(map[i][j].isLei()){
                    System.out.print("*");
                }else if(map[i][j].countLei() == 0){
                    System.out.print("-");
                }else{
                    System.out.print(map[i][j].countLei());
                }
            }
            System.out.println();
        }
    }
    //没有插旗的雷数
    public int lastboom(){
        return leiCount-flagCount;
    }
}
