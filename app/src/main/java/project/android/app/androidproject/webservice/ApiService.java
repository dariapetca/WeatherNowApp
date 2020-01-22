package project.android.app.androidproject.webservice;

import project.android.app.androidproject.webservice.response.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Daria on 8/8/2018.
 **/
public interface ApiService {
    String BASE_URL = "http://statiemeteo.dyndns.org:443/";
    @GET("bazadate.php?")
    Observable<WeatherResponse> getWeather(@Query("data") String date);
}