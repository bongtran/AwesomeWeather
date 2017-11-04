package vn.bongtran.awesomeweather.utils;

import android.os.Environment;

import java.io.File;
import java.net.URL;

import vn.bongtran.awesomeweather.app.WeatherApp;

/**
 * Created by Peter on 11/4/17.
 */

public class FileUtils {
    public static File getFileFromURL(URL url) {
        String fileName = FileUtils.getFileNameFromUrl(url.toString());
        return FileUtils.getFilePath(fileName);
    }

    public static String getFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf('/') + 1);

    }
    public static File getFilePath(String fileName) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + WeatherApp.PACKAGE_NAME
                + "/Files/Images");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return mediaFile;
    }

}
