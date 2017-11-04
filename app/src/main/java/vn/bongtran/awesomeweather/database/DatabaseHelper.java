package vn.bongtran.awesomeweather.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import vn.bongtran.awesomeweather.constant.Constant;

/**
 * Created by Peter on 11/3/17.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    //Table user
    public static final String TABLE_WEATHER_INFO = "WEATHER";
    public static final String WEATHER_ID_COL = "Id";
    public static final String WEATHER_NAME_COL = "Name";
    public static final String WEATHER_CITY = "City";
    public static final String WEATHER_HUMIDITY = "Humidity";
    public static final String WEATHER_TEMPC = "TempC";
    public static final String WEATHER_TEMPF = "TempF";
    public static final String WEATHER_TIME = "Time";
    public static final String WEATHER_DESCRIPTION = "Description";
    public static final String WEATHER_ICON = "Icon";

    static final String CREATE_WEATHER_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_WEATHER_INFO + " ("
            + WEATHER_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + WEATHER_NAME_COL + " TEXT, "
            + WEATHER_CITY + " TEXT, "
            + WEATHER_HUMIDITY + " TEXT, "
            + WEATHER_TEMPC + " TEXT, "
            + WEATHER_TEMPF + " TEXT, "
            + WEATHER_DESCRIPTION + " TEXT, "
            + WEATHER_TIME + " TEXT, "
            + WEATHER_ICON + " TEXT "
            + ") ";

    static final String dbName = "weather";

    public DatabaseHelper(Context context)
    {
        super(context, dbName, null, Constant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        try {
            sqLiteDatabase.execSQL(CREATE_WEATHER_TABLE);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onOpen(SQLiteDatabase db)
    {
        super.onOpen(db);
        if (!db.isReadOnly())
        {
            db.execSQL("PRAGMA foreign_keys=nON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        try {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER_INFO);
            sqLiteDatabase.execSQL(CREATE_WEATHER_TABLE);
        }
        catch (Exception e){
            Log.e(DatabaseHelper.class.getName(), e.getMessage());
        }
        onCreate(sqLiteDatabase);
    }
}
