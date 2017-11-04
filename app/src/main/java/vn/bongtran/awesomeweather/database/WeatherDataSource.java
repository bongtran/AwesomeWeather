package vn.bongtran.awesomeweather.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import vn.bongtran.awesomeweather.model.WeatherInfo;

/**
 * Created by Peter on 11/3/17.
 */
public class WeatherDataSource extends BaseDataSource {
    public WeatherDataSource(Context context) {
        super(context);
    }

    static String[] ALL_COLUMN = {
            DatabaseHelper.WEATHER_CITY,
            DatabaseHelper.WEATHER_ID_COL,
            DatabaseHelper.WEATHER_NAME_COL,
            DatabaseHelper.WEATHER_DESCRIPTION,
            DatabaseHelper.WEATHER_HUMIDITY,
            DatabaseHelper.WEATHER_TEMPC,
            DatabaseHelper.WEATHER_TEMPF,
            DatabaseHelper.WEATHER_TIME,
            DatabaseHelper.WEATHER_ICON,
    };

    public boolean insertWeatherInfo(WeatherInfo info, String name) {
        ContentValues values = new ContentValues();
        try {
//            values.put(DatabaseHelper.WEATHER_ID_COL, 1);
            values.put(DatabaseHelper.WEATHER_NAME_COL, name);
            values.put(DatabaseHelper.WEATHER_CITY, info.getCityName());
            values.put(DatabaseHelper.WEATHER_DESCRIPTION, info.getWeatherDescription());
            values.put(DatabaseHelper.WEATHER_HUMIDITY, info.getHumidity());
            values.put(DatabaseHelper.WEATHER_ICON, info.getWeatherIcon());
            values.put(DatabaseHelper.WEATHER_TEMPC, info.getTempC());
            values.put(DatabaseHelper.WEATHER_TEMPF, info.getTempF());
            values.put(DatabaseHelper.WEATHER_TIME, info.getObservationTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        long val = database.insert(DatabaseHelper.TABLE_WEATHER_INFO, null, values);
        return val >= 0;
    }


    public WeatherInfo getWeatherInfo(String name) {
        WeatherInfo result = null;
        String whereClause = DatabaseHelper.WEATHER_NAME_COL + " = ?";
        String[] whereArgs = new String[]{
                name
        };
        Cursor cursor = database.query(DatabaseHelper.TABLE_WEATHER_INFO, new String[]{"*"},
                whereClause, whereArgs, null, null, DatabaseHelper.WEATHER_ID_COL + " DESC", "1");
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            try {
                result = new WeatherInfo();
                result.setCityName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.WEATHER_CITY)));
                result.setHumidity(cursor.getString(cursor.getColumnIndex(DatabaseHelper.WEATHER_HUMIDITY)));
                result.setObservationTime(cursor.getString(cursor.getColumnIndex(DatabaseHelper.WEATHER_TIME)));
                result.setTempC(cursor.getString(cursor.getColumnIndex(DatabaseHelper.WEATHER_TEMPC)));
                result.setTempF(cursor.getString(cursor.getColumnIndex(DatabaseHelper.WEATHER_TEMPF)));
                result.setWeatherDescription(cursor.getString(cursor.getColumnIndex(DatabaseHelper.WEATHER_DESCRIPTION)));
                result.setWeatherIcon(cursor.getString(cursor.getColumnIndex(DatabaseHelper.WEATHER_ICON)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();

        return result;
    }

    public ArrayList<String> getNames() {
        ArrayList<String> result = new ArrayList();
        String[] tableColumns = new String[]{
                DatabaseHelper.WEATHER_NAME_COL
        };
        Cursor cursor = database.query(DatabaseHelper.TABLE_WEATHER_INFO, tableColumns,
                null, null, null, null, DatabaseHelper.WEATHER_ID_COL + " DESC", "10");

        if (cursor.moveToFirst()) {
            do {
                try {
                    result.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.WEATHER_NAME_COL)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }while (cursor.moveToNext());
        }
        cursor.close();

        return result;
    }

    public void deleteDataBase() {
        database.delete(DatabaseHelper.TABLE_WEATHER_INFO, null, null);

    }

    public boolean updateInfo(WeatherInfo info) {
        ContentValues values = new ContentValues();
        String usrId = "";
        try {

        } catch (Exception e) {

        }
        long val = database.update(DatabaseHelper.TABLE_WEATHER_INFO, values, DatabaseHelper.WEATHER_ID_COL + "=?", new String[]{usrId});
        return val >= 0;
    }

}
