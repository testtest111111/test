package com.example.marina.familybudjet;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ServiceConnection {
    private Intent intent;
    private MyService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, MyService.class);
        startService(intent);
        bindService(intent,this,0);
    }
    public void thfff(){

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        service = ((MyBinder) binder).getService();
        service.setMa(this);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    public void updateBudget(final String nameDo, final int delta, final int balance){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv = (TextView) findViewById(R.id.textView);
                tv.setText(nameDo + delta + "\n" + "Balance is " + balance);
            }
        });
    }

    public void gameOver(final int days){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),
                        "Game over! Вам хватило денег на " + days + " дней", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void start(View view) {
        EditText et = (EditText) findViewById(R.id.etSalary);
        service.setAvSalary(Integer.parseInt(et.getText().toString()));

        EditText et1 = (EditText) findViewById(R.id.etExpense);
        service.setAvExpense(Integer.parseInt(et1.getText().toString()));

        service.startGame();
    }

    public void stop(View view) {
        service.stopGame();
    }
}
