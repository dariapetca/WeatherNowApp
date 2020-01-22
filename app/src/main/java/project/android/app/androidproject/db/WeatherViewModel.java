package project.android.app.androidproject.db;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import project.android.app.androidproject.application.AndroidProjectApplication;
import project.android.app.androidproject.webservice.response.WeatherResponse;

/**
 * Created by Daria on 8/8/2018.
 **/
public class WeatherViewModel extends AndroidViewModel {
    private String dateFilter;
    private WeatherRepository weatherRepository;
    private LiveData<WeatherResponse> listLiveData;

    public WeatherViewModel(@NonNull AndroidProjectApplication application, final String date) {
        super(application);
        weatherRepository = new WeatherRepository(application);
        listLiveData = weatherRepository.getWeatherResponse(date);
        dateFilter = date;
    }
    public void insertWeather(WeatherResponse response) { weatherRepository.insert(response); }
    public LiveData<WeatherResponse> getWeather(String date) {
        setDateFilter(date);
        return listLiveData;
    }

    public void setDateFilter(String dateFilter) {
        if (!this.dateFilter.equals(dateFilter))
            listLiveData = weatherRepository.getWeatherResponse(dateFilter);
        this.dateFilter = dateFilter;
    }


}
