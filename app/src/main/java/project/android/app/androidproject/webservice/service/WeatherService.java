package project.android.app.androidproject.webservice.service;

import project.android.app.androidproject.webservice.ApiService;
import project.android.app.androidproject.webservice.NetworkModule;
import project.android.app.androidproject.webservice.response.WeatherResponse;
import rx.Observable;

/**
 * Created by Daria on 8/8/2018.
 **/
public class WeatherService {
    private ApiService service;

    public WeatherService() {
        service = NetworkModule.provideRepository();
    }

    public Observable<WeatherResponse> getWeather(String date) {
        return service.getWeather(date);
    }
}
