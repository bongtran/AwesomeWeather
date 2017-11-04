package vn.bongtran.awesomeweather.database;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import vn.bongtran.awesomeweather.model.WeatherInfo;


/**
 * Created by Peter on 11/3/17.
 */
public class DataManager {
    static DataManager _instance;
    static Context _context;
    final static String _lock = "";

    DataManager() {
    }

    public static DataManager sharedInstance() {
        synchronized (_lock) {
            if (_instance == null)
                _instance = new DataManager();

            return _instance;
        }
    }

    public static void initDataManager(Context applicationContext) {
        _context = applicationContext;
    }

    public boolean insertWeatherInfo(WeatherInfo info, String name) {
        WeatherDataSource weatherDataSource = new WeatherDataSource(_context);
        weatherDataSource.open();
        boolean result = weatherDataSource.insertWeatherInfo(info, name);
        weatherDataSource.close();
        weatherDataSource = null;
        return result;
    }

    public WeatherInfo getInfo(String name) {
        WeatherInfo result = null;
        WeatherDataSource weatherDataSource = new WeatherDataSource(_context);
        weatherDataSource.open();
        try {
            result = weatherDataSource.getWeatherInfo(name);
        } catch (Exception e) {

        }
        weatherDataSource.close();
        weatherDataSource = null;
        return result;
    }

    public ArrayList<String> getNames() {
        ArrayList<String> result = null;
        WeatherDataSource weatherDataSource = new WeatherDataSource(_context);
        weatherDataSource.open();
        try {
            result = weatherDataSource.getNames();
            Log.d("LOG NAMES", result.toString());
        } catch (Exception e) {

        }
        weatherDataSource.close();
        weatherDataSource = null;
        return result;
    }

    public void deleteInfo() {

    }

}
