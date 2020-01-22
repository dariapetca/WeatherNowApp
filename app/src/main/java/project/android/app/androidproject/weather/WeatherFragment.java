package project.android.app.androidproject.weather;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import project.android.app.androidproject.R;
import project.android.app.androidproject.base.BaseFragment;
import project.android.app.androidproject.global.IGetWeather;
import project.android.app.androidproject.global.OnWeatherReceived;
import project.android.app.androidproject.helpers.DateUtil;
import project.android.app.androidproject.helpers.UnitHelper;
import project.android.app.androidproject.webservice.response.WeatherResponse;

/**
 * Created by Daria on 8/8/2018.
 **/
public class WeatherFragment extends BaseFragment implements OnWeatherReceived {
    private TextView temperatureTxt, humidityTxt, dateTxt;

    private IGetWeather callback;

    public WeatherFragment() { }

    public static WeatherFragment newInstance() { WeatherFragment fragment = new WeatherFragment();return fragment; }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            callback = (IGetWeather) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        temperatureTxt = view.findViewById(R.id.temperature_txt);
        humidityTxt = view.findViewById(R.id.humidity_txt);
        dateTxt = view.findViewById(R.id.weather_time);
        getWeather();
        return view;
    }

    public void setWeather(WeatherResponse weather) {
        if (getActivity() == null) {
            return;
        }
        if (weather.getOre() != null) {
            String lastMeasurementTime = weather.getOre().get(0);
            dateTxt.setText(DateUtil.dateToReadableDate(lastMeasurementTime));
        }
        if (weather.getTemperatura() != null) {
            double temperature = weather.getTemperatura().get(0);
            temperatureTxt.setText(UnitHelper.getTemperature(getActivity(), temperature) + "°" + UnitHelper.getSavedUnit(getActivity()));
        }
        if (weather.getUmiditate() != null) {
            double humidity = weather.getUmiditate().get(0);
            humidityTxt.setText(humidity + "%");
        }
    }

    public void getWeather() { showProgressHUD(); callback.getWeather(DateUtil.getCurrentDate(), this); }

    @Override
    public void onWeatherReceived(WeatherResponse response) {
        hideProgressHUD();
        if (response == null) Toast.makeText(getActivity(), R.string.could_not_get_weather, Toast.LENGTH_LONG);
        else setWeather(response);
    }
}
