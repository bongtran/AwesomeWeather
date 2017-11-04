package vn.bongtran.awesomeweather.model;

/**
 * Created by Peter on 11/3/17.
 */

public class WeatherInfo {
    private String cityName;
    private String observationTime;
    private String humidity;
    private String tempC;
    private String tempF;
    private String weatherDescription;
    private String weatherIcon;
    private boolean isOffline;

    public WeatherInfo() {

    }

    public WeatherInfo(String cityName, String observationTime, String humidity, String weatherDescription, String weatherIcon) {
        this.cityName = cityName;
        this.humidity = humidity;
        this.observationTime = observationTime;
        this.weatherDescription = weatherDescription;
        this.weatherIcon = weatherIcon;
    }

    public WeatherInfo(String cityName, String observationTime, String oC, String oF, String humidity, String weatherDescription, String weatherIcon) {
        this.cityName = cityName;
        this.tempC = oC;
        this.tempF = oF;
        this.humidity = humidity;
        this.observationTime = observationTime;
        this.weatherDescription = weatherDescription;
        this.weatherIcon = weatherIcon;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(String observationTime) {
        this.observationTime = observationTime;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getTempC() {
        return tempC;
    }

    public void setTempC(String tempC) {
        this.tempC = tempC;
    }

    public String getTempF() {
        return tempF;
    }

    public void setTempF(String tempF) {
        this.tempF = tempF;
    }

    public boolean isOffline() {
        return isOffline;
    }

    public void setOffline(boolean offline) {
        isOffline = offline;
    }

}
