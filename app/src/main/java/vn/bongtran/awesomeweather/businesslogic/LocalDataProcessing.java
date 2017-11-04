package vn.bongtran.awesomeweather.businesslogic;

import vn.bongtran.awesomeweather.database.DataManager;
import vn.bongtran.awesomeweather.model.WeatherInfo;

/**
 * Created by Peter on 11/4/17.
 */

public class LocalDataProcessing {
    public static void saveInfo(final WeatherInfo info,final String name) {
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                DataManager.sharedInstance().insertWeatherInfo(info, name);
//            }
//        };
//
//        thread.start();
        DataManager.sharedInstance().insertWeatherInfo(info, name);
    }
}
