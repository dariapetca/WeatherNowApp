package project.android.app.androidproject.db;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Executors;

import project.android.app.androidproject.constants.DBConstants;
import project.android.app.androidproject.global.OnWeatherReceived;
import project.android.app.androidproject.webservice.response.WeatherResponse;

/**
 * Created by Daria on 8/9/2018.
 **/
public class DatabaseHelper {
    private static WeatherDb weatherDb;

    public static void saveWeatherToDatabase(final WeatherResponse weatherResponse) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                weatherDb.weatherDao().insertWeather(weatherResponse);
            }
        });


    }

    public static void createDatabase(Context ctx) {
        weatherDb = Room.databaseBuilder(ctx, WeatherDb.class, DBConstants.DATABASENAME).build();

    }
    public static void getWeatherForDate(final String date, final OnWeatherReceived callback) {

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
//                List<WeatherResponse> queryResponse = weatherDb.weatherDao().getWeather(date);
//                if (queryResponse != null && queryResponse.size() > 0)
//                    return;
//                WeatherResponse response = queryResponse.get(0);//return first result
//                callback.onWeatherReceived(response);
            }
        });
    }

}
