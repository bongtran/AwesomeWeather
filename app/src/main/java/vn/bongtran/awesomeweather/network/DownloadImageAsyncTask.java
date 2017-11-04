package vn.bongtran.awesomeweather.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import vn.bongtran.awesomeweather.utils.FileUtils;


/**
 * Created by Peter on 11/4/17.
 */
public class DownloadImageAsyncTask extends AsyncTask<String, String, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... urls) {
        int count;

        try {

            URL url = new URL(urls[0]);
            File realImageFile = FileUtils.getFileFromURL(url);
            if(realImageFile != null && realImageFile.exists() && realImageFile.length() > 1){
                return null;
            }

            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            int lengthOfFile = urlConnection.getContentLength();
            Log.d("ANDRO_ASYNC", "Lenght of file: " + lengthOfFile);

            InputStream input = new BufferedInputStream(url.openStream());

            OutputStream output = new FileOutputStream(realImageFile);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress(""+(int)((total*100)/lengthOfFile));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    protected void onProgressUpdate(String... progress) {

    }

    @Override
    protected void onPostExecute(String unused) {
    }
}
