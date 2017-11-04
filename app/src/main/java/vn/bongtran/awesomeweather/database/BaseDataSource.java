package vn.bongtran.awesomeweather.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Peter on 11/3/17.
 */
public class BaseDataSource {
    protected Context _context;
    protected SQLiteDatabase database;
    protected DatabaseHelper dbHelper;

    public BaseDataSource(Context context) {
        _context = context;
        dbHelper = new DatabaseHelper(_context);
    }


    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
}
