package com.popalay.yooder;

import com.arellomobile.mvp.MvpApplication;
import com.popalay.yooder.di.AppComponent;
import com.popalay.yooder.di.AppModule;
import com.popalay.yooder.di.DaggerAppComponent;
import com.vk.sdk.VKSdk;


public class Application extends MvpApplication {

    private static AppComponent graph;

    @Override
    public void onCreate() {
        super.onCreate();
        graph = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        VKSdk.initialize(this);
    }

    public static AppComponent getGraph() {
        return graph;
    }
}
