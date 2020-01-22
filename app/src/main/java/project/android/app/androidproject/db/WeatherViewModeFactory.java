package project.android.app.androidproject.db;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import project.android.app.androidproject.application.AndroidProjectApplication;

/**
 * Created by Daria on 8/9/2018.
 **/
public class WeatherViewModeFactory extends ViewModelProvider.NewInstanceFactory {
    @NonNull
    private final AndroidProjectApplication mApplication;

    private final String date;


    public WeatherViewModeFactory(@NonNull AndroidProjectApplication application, String date) {
        mApplication = application;
        this.date = date;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) { return (T) new WeatherViewModel(mApplication, date); }
}

