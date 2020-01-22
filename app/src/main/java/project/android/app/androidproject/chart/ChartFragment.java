package project.android.app.androidproject.chart;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import project.android.app.androidproject.R;
import project.android.app.androidproject.base.BaseFragment;
import project.android.app.androidproject.global.IGetWeather;
import project.android.app.androidproject.global.OnWeatherReceived;
import project.android.app.androidproject.helpers.DateUtil;
import project.android.app.androidproject.helpers.UnitHelper;
import project.android.app.androidproject.util.ListUtils;
import project.android.app.androidproject.util.MinMaxValue;
import project.android.app.androidproject.webservice.response.WeatherResponse;

/**
 * Created by Daria on 8/9/2018.
 **/
public class ChartFragment extends BaseFragment implements OnChartValueSelectedListener, OnWeatherReceived {

    private static final String TAG = ChartFragment.class.getSimpleName();
    private IGetWeather callback;

    private Typeface mTfLight;
    private LineChart mChart;

    private EditText editSelectDate;
    private String selectedDate;


    public ChartFragment() { }

    public static ChartFragment newInstance() {
        ChartFragment fragment = new ChartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mChart = view.findViewById(R.id.chart1);
        editSelectDate = view.findViewById(R.id.editSelectDate);
        getWeather(DateUtil.getCurrentDate());
    }

    private void show(WeatherResponse response) {
        try {
            initDate();
            setEdit();
            clearChartData();
            initFonts(getActivity());
            initChart(mChart, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List reverseList(List myList) {
        List invertedList = new ArrayList();
        for (int i = myList.size() - 1; i >= 0; i--) invertedList.add(myList.get(i));
        return invertedList;
    }

    private void initFonts(Activity activity) {
        if (activity == null) return;
        mTfLight = Typeface.createFromAsset(activity.getAssets(), "OpenSans-Light.ttf");
    }

    private void initChart(LineChart mChart, WeatherResponse response) throws Exception {
        List<String> date = new ArrayList<>();
        List<Double> temp = new ArrayList<>();
        List<Double> humidity = new ArrayList<>();
        date.addAll(response.getOre());
        temp.addAll(response.getTemperatura());
        humidity.addAll(response.getUmiditate());
        date = reverseList(date);
        temp = reverseList(temp);
        humidity = reverseList(humidity);
        mChart.setOnChartValueSelectedListener(this);
        mChart.getDescription().setEnabled(false);
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        mChart.setPinchZoom(true);
        mChart.setBackgroundColor(Color.LTGRAY);

        setData(date, temp, humidity);
        mChart.animateX(2500);
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(mTfLight);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(MinMaxValue.getMax(humidity));
        leftAxis.setAxisMinimum(MinMaxValue.getMin(humidity));
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setTypeface(mTfLight);
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaximum(MinMaxValue.getMax(temp));
        rightAxis.setAxisMinimum(MinMaxValue.getMin(temp));
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
    }

    private void setData(List<String> date, List<Double> temp, List<Double> humidity) throws Exception {

        ArrayList<Entry> yVals1 = new ArrayList<>();

        int count = date.size();

        for (int i = 0; i < count; i++) {
            String dateD = date.get(i);
            float dateF = DateUtil.getTimeAsFraction(dateD);

            Double humD = humidity.get(i);
            float humF = humD.floatValue();
            yVals1.add(new Entry(dateF, humF));
        }

        ArrayList<Entry> yVals2 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String dateD = date.get(i);
            float dateF = DateUtil.getTimeAsFraction(dateD);

            Double tempD = temp.get(i);
            tempD = UnitHelper.getTemperature(getActivity(), tempD);

            float tempF = tempD.floatValue();
            yVals2.add(new Entry(dateF, tempF));
        }


        LineDataSet set1, set2;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(yVals1, getString(R.string.humidity));
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            set2 = new LineDataSet(yVals2, getString(R.string.temperature));
            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set2.setColor(Color.RED);
            set2.setCircleColor(Color.WHITE);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            LineData data = new LineData(set1, set2);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);
            mChart.setData(data);
        }
    }

    private void clearChartData() {
        if (mChart == null) return;

        mChart.clear();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i(TAG, e.toString());
        mChart.centerViewToAnimated(e.getX(), e.getY(), mChart.getData().getDataSetByIndex(h.getDataSetIndex()).getAxisDependency(), 500);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try { callback = (IGetWeather) activity; }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onNothingSelected() { Log.i(TAG, getString(R.string.nothing_selected)); }

    public void getWeather(String date) {
        showProgressHUD();
        callback.getWeather(date, this);
    }

    @Override
    public void onWeatherReceived(WeatherResponse response) {
        hideProgressHUD();
        if (response == null)
            Toast.makeText(getActivity(), R.string.weather_issue, Toast.LENGTH_LONG).show();
        else if (ListUtils.checkListEmpty(response.getTemperatura()))
            Toast.makeText(getActivity(), R.string.weather_issue, Toast.LENGTH_LONG).show();
         else show(response);
    }

    private void setEdit() {
        editSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                final Calendar calendar = Calendar.getInstance();
                if (selectedDate != null) calendar.setTime(DateUtil.dateFromPattern(selectedDate, "yyyy-MM-dd"));
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        if (Calendar.getInstance().before(cal)) {
                            Toast.makeText(getActivity(), R.string.select_value_until_today, Toast.LENGTH_LONG).show();
                            return;
                        }

                        String date = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);
                        selectedDate = date;
                        editSelectDate.setText(date);
                        getWeather(date);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });
        editSelectDate.setText(selectedDate);
        editSelectDate.setInputType(InputType.TYPE_NULL);
    }

    private void initDate() {
        if (selectedDate == null) selectedDate = DateUtil.getCurrentDate();
    }
}