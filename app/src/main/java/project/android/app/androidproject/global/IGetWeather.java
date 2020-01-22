package project.android.app.androidproject.global;

/**
 * Created by Daria on 8/10/2018.
 **/
public interface IGetWeather {
    public void getWeather(String date, OnWeatherReceived callback);
}
