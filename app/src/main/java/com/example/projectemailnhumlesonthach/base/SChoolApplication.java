package com.example.projectemailnhumlesonthach.base;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatDelegate;

public class SChoolApplication extends Application implements AppLifeCycleHandler.AppLifeCycleCallback {
    private Typeface svnNexa;
    private Typeface italicVersion;
    private Typeface mainItalic;
    private Typeface mainBoldItalic;
    public static Context context;
    private static SChoolApplication mInstance;
    private boolean isDatabaseUpdate;
    private boolean isInBackground = false;

    public boolean isDatabaseUpdate() {
        return isDatabaseUpdate;
    }

    public void setDatabaseUpdate(boolean databaseUpdate) {
        isDatabaseUpdate = databaseUpdate;
    }

    public void onCreate() {
        super.onCreate();

        AppLifeCycleHandler appLifeCycleHandler = new AppLifeCycleHandler(this);
        registerActivityLifecycleCallbacks(appLifeCycleHandler);
        registerComponentCallbacks(appLifeCycleHandler);

        mInstance = this;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        TypefaceUtil.overrideFont(this, "SERIF", "svn_nexa_regular.ttf");

        svnNexa = Typeface.createFromAsset(getAssets(), "svn_nexa_regular.ttf");
        italicVersion = Typeface.createFromAsset(getAssets(), "roboto_light_italic.ttf");
        mainItalic = Typeface.createFromAsset(getAssets(), "svn_nexa_regular_italic.ttf");
        mainBoldItalic = Typeface.createFromAsset(getAssets(), "svn_nexa_bold_italic.ttf");

        context = getApplicationContext();
    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }

    public static synchronized SChoolApplication getInstance() {
        return mInstance;
    }

    public Typeface getSvnNexa() {
        return svnNexa;
    }

    public Typeface getItalicVersion() {
        return italicVersion;
    }

    public Typeface getMainItalic() {
        return mainItalic;
    }

    public Typeface getMainBoldItalic() {
        return mainBoldItalic;
    }

    @Override
    public void onAppBackground() {

    }

    @Override
    public void onAppForeground() {

    }
}


