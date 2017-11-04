package vn.bongtran.awesomeweather.businesslogic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.bongtran.awesomeweather.model.WeatherInfo;

/**
 * Created by Peter on 11/3/17.
 */

public class WeatherInfoParser {
    public static boolean hasWeatherData(String data) {
        boolean result = false;
        try {
            JSONObject dataObject = new JSONObject(data).getJSONObject("data");
            result = dataObject.has("error");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return !result;
    }

    public static String getErrorMessage(String data) {
        String result = "";
        try {
            JSONObject dataObject = new JSONObject(data).getJSONObject("data");
            JSONArray array = dataObject.getJSONArray("error");
            if (array.length() > 0) {
                JSONObject errorObject = array.getJSONObject(0);
                result = errorObject.getString("msg");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static WeatherInfo getWeatherInfo(String data) {
        WeatherInfo result = new WeatherInfo();
        try {
            JSONObject dataObject = new JSONObject(data).getJSONObject("data");

            JSONObject requestObject = dataObject.getJSONArray("request").getJSONObject(0);
            String city = requestObject.getString("query");

            JSONArray array = dataObject.getJSONArray("current_condition");
            if (array.length() > 0) {
                JSONObject currentObject = array.getJSONObject(0);
                String observationTime = currentObject.getString("observation_time");
                String humidity = currentObject.getString("humidity");
                String icon = currentObject.getJSONArray("weatherIconUrl").getJSONObject(0).getString("value");
                String description = currentObject.getJSONArray("weatherDesc").getJSONObject(0).getString("value");

                result = new WeatherInfo(city, observationTime, humidity, description, icon);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

}
