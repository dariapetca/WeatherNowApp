package project.android.app.androidproject.webservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import project.android.app.androidproject.webservice.service.WeatherService;

/**
 * Created by Daria on 8/8/2018.
 **/
public class DataManager {
    private static DataManager instance;
    private WeatherService weatherService;

    private DataManager() {
        weatherService = new WeatherService();
    }

    public static synchronized DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();

        return instance;
    }

    public static boolean hasNetworkConnection(Context context) {
        try {
            if (context != null) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = null;
                if (connectivityManager != null)
                    activeNetwork = connectivityManager.getActiveNetworkInfo();

                return (activeNetwork != null) && activeNetwork.isConnectedOrConnecting();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public static void shutDown() {
        if (instance != null) instance = null;
        System.gc();
    }

    public WeatherService getWeatherService() {
        return weatherService;
    }
}
