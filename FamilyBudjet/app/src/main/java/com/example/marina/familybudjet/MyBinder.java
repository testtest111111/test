package com.example.marina.familybudjet;

import android.os.Binder;

/**
 * Created by Marina on 01.12.2015.
 */
public class MyBinder extends Binder{
    private MyService service;

    public MyBinder(MyService service) {
        this.service = service;
    }

    public MyService getService() {
        return service;
    }

}
