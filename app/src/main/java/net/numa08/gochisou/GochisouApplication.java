package net.numa08.gochisou;

import android.app.Application;


public class GochisouApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        DaggerApplicationComponent
//                .builder()
//                .applicationModule(new ApplicationModule(this))
//                .build();
    }
}
