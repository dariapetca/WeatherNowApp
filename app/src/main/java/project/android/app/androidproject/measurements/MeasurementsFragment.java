package project.android.app.androidproject.measurements;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
public class MeasurementsFragment extends BaseFragment implements OnWeatherReceived {
    private TableLayout table;
    private IGetWeather callback;

    public MeasurementsFragment() {}


    public static MeasurementsFragment newInstance() {
        MeasurementsFragment fragment = new MeasurementsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_measurements, container, false);
        table = view.findViewById(R.id.measurements_table);
        getWeather(DateUtil.getCurrentDate());


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            callback = (IGetWeather) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener");
        }
    }


    private void setDataIntoTableView(WeatherResponse response) {
        table.removeAllViews();
        if (response.getUmiditate() == null || response.getTemperatura() == null) {
            return;
        }
        table.addView(getHeaderTableRow());

        ArrayList<Double> temperatures = response.getTemperatura();
        ArrayList<Double> humidities = response.getUmiditate();
        ArrayList<String> dateTimes = response.getOre();
        for (int i = 0; i < temperatures.size(); i++) {
            String date = dateTimes.get(i);
            double temperature = temperatures.get(i);
            double humidity = humidities.get(i);
            table.addView(getTableRow(date, temperature, humidity));
        }
    }

    private View getTableRow(String date, double temp, double humidity) {
        View tableRow = getActivity().getLayoutInflater()
                .inflate(R.layout.measurement_table_row, null);
        tableRow.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.measurements_table_bg));
        TextView dateTxt = tableRow.findViewById(R.id.date_txt);
        dateTxt.setText(DateUtil.dateToReadableDate(date));
        TextView tempTxt = tableRow.findViewById(R.id.temperature_txt);
        tempTxt.setText(UnitHelper.getTemperature(getActivity(), temp) + "°" + UnitHelper.getSavedUnit(getActivity()));
        TextView humidityTxt = tableRow.findViewById(R.id.humidity_txt);
        humidityTxt.setText(String.valueOf(humidity) + "%");
        return tableRow;
    }
    @Override
    public void onWeatherReceived(WeatherResponse response) {
        hideProgressHUD();
        if (response == null)
            Toast.makeText(getActivity(), R.string.could_not_get_weather, Toast.LENGTH_LONG);
        else
            setDataIntoTableView(response);
    }

    public void getWeather(String date) {
        showProgressHUD();
        callback.getWeather(date, this);
    }

    private View getHeaderTableRow() {
        View tableRow = getActivity().getLayoutInflater()
                .inflate(R.layout.measurement_table_header_row, null);
        return tableRow;
    }


}
