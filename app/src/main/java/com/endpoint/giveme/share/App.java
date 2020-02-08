package com.endpoint.giveme.share;

import android.app.Application;
import android.content.Context;

import com.endpoint.giveme.language.Language_Helper;

public class App extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(Language_Helper.updateResources(base,Language_Helper.getLanguage(base)));
    }
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.setDefault(this, "DEFAULT", "fonts/DroidSerif-Bold.ttf");
        TypefaceUtil.setDefault(this, "MONOSPACE", "fonts/DroidSerif-Bold.ttf");
        TypefaceUtil.setDefault(this, "SERIF", "fonts/DroidSerif-Bold.ttf");
        TypefaceUtil.setDefault(this, "SANS_SERIF", "fonts/DroidSerif-Bold.ttf");

    }

}
