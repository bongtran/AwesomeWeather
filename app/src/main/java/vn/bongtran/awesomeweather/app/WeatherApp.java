package vn.bongtran.awesomeweather.app;

import android.app.Application;
import android.content.Context;

import vn.bongtran.awesomeweather.database.DataManager;

/**
 * Created by Peter on 11/3/17.
 */

public class WeatherApp extends Application {
    private static Context appContext;
    public static String PACKAGE_NAME;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        DataManager.initDataManager(appContext);
        PACKAGE_NAME = getApplicationContext().getPackageName();
    }

    public static Context getAppContext() {
        return appContext;
    }
}
