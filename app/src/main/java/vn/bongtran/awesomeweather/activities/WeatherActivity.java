package vn.bongtran.awesomeweather.activities;

import android.os.Bundle;

import vn.bongtran.awesomeweather.R;
import vn.bongtran.awesomeweather.constant.Constant;

public class WeatherActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.txt_title_weather_search);
        setApiKey(Constant.WEATHER_API_KEY_FIRST);
    }
}
