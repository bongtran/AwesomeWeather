package vn.bongtran.awesomeweather.network;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import vn.bongtran.awesomeweather.app.WeatherApp;
import vn.bongtran.awesomeweather.utils.NetworkUtils;

/**
 * Created by Peter on 11/3/17.
 */

public abstract class GETTask extends AsyncTask<JSONObject, JSONObject, JSONObject> {
    protected SBTaskListener _listener;
    protected boolean _isFail;

    public void setTaskListener(SBTaskListener listener) {
        _listener = listener;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected JSONObject doInBackground(JSONObject... objects) {
        if (!NetworkUtils.hasInternetConnection(WeatherApp.getAppContext())) {
            _isFail = true;
            JSONObject error = new JSONObject();
            try {
                error.put("statusCode", -100);
                error.put("message", "No internet connection");
            } catch (Exception e) {

            }
            return error;
        }

        JSONObject result = getResult();
        if (result == null) {
            _isFail = true;
        }
        return result;
    }

    public abstract JSONObject getResult();

    @Override
    protected void onPostExecute(JSONObject object) {
        if (_listener != null) {
            if (_isFail) {
                try {
                    _listener.onTaskFailed(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    _listener.onTaskSucceeded(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface SBTaskListener {

        void onTaskSucceeded(JSONObject object) throws JSONException;

        void onTaskFailed(JSONObject object) throws JSONException;
    }
}