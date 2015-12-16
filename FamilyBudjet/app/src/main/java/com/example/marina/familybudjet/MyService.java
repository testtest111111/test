package com.example.marina.familybudjet;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

import java.util.Random;

public class MyService extends Service {
    private int days;
    private int avSalary;
    private int avExpense;
    private int balance;
    private boolean inGame;

    public void setAvSalary(int avSalary) {
        this.avSalary = avSalary;
    }

    public void setAvExpense(int avExpense) {
        this.avExpense = avExpense;
    }

    private MainActivity ma;

    public void setMa(MainActivity ma) {
        this.ma = ma;
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder(this);
    }

    public void startGame(){
        inGame =true;
        Max max = new Max();
        max.start();

        Marina marina = new Marina();
        marina.start();
    }

    public void stopGame(){
        inGame = false;
    }

    private class Max extends Thread{
        @Override
        public void run() {
            while (inGame){
                int salary =(int)(Math.random()*(avSalary*1.5-avSalary/2)+avSalary/2);
                synchronized(MyService.class){
                    balance+= salary;
                }
                ma.updateBudget("Max deposited ", salary, balance);
                SystemClock.sleep(10000);
            }
        }
    }

    private class Marina extends Thread{
        @Override
        public void run() {
            while (inGame){
                SystemClock.sleep(1000);
                int expense = (int)(Math.random()*(avExpense*1.5-avExpense/2)+avExpense/2);

                synchronized (MyService.class) {
                    balance -= expense;
                }
                if(balance<0){
                    inGame =false;
                    ma.gameOver(days);
                }

                ma.updateBudget("Marina spent ", expense, balance);
                days++;
            }

        }
    }
}
