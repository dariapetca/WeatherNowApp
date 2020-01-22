package project.android.app.androidproject.global;

import project.android.app.androidproject.webservice.response.WeatherResponse;

/**
 * Created by Daria on 8/10/2018.
 **/
public interface OnWeatherReceived {
    public void onWeatherReceived(WeatherResponse response);
}
