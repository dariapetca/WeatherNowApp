package project.android.app.androidproject.db;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import project.android.app.androidproject.application.AndroidProjectApplication;
import project.android.app.androidproject.webservice.response.WeatherResponse;

/**
 * Created by Daria on 8/8/2018.
 **/
public class WeatherRepository {
    private WeatherDao weatherDao;
    private LiveData<WeatherResponse> weatherData;



    LiveData<WeatherResponse> getWeatherResponse(String date) { return weatherDao.getWeather(date); }

    public void insert(WeatherResponse weatherResponse) { new insertAsyncTask(weatherDao).execute(weatherResponse); }

    public WeatherRepository(AndroidProjectApplication application) {
        WeatherDb db = WeatherDb.getDatabase(application);
        weatherDao = db.weatherDao();
    }

    private static class insertAsyncTask extends AsyncTask<WeatherResponse, Void, Void> {

        private WeatherDao mAsyncTaskDao;

        insertAsyncTask(WeatherDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WeatherResponse... params) {
            mAsyncTaskDao.insertWeather(params[0]);
            return null;
        }
    }
}
