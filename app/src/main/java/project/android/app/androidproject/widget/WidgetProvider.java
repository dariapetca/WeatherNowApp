package project.android.app.androidproject.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import project.android.app.androidproject.R;
import project.android.app.androidproject.db.DatabaseHelper;
import project.android.app.androidproject.global.OnWeatherReceived;
import project.android.app.androidproject.helpers.ConnectionUtil;
import project.android.app.androidproject.helpers.DateUtil;
import project.android.app.androidproject.helpers.UnitHelper;
import project.android.app.androidproject.webservice.WebserviceHelper;
import project.android.app.androidproject.webservice.response.WeatherResponse;

/**
 * Created by Daria on 8/11/2018.
 **/
public class WidgetProvider extends AppWidgetProvider implements OnWeatherReceived {
    private static WeatherResponse weather;
    private RemoteViews remoteViews;
    private Context context;
    private int[] appWidgetIds;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;
        this.appWidgetIds = appWidgetIds;
        this.context = context;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_weather_layout);

            setWeather(context);
            Intent intent = new Intent(context, WidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.container, pendingIntent);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        getWeather(context);
    }

    private void setWeather(Context context) {
        if (weather != null && context != null && remoteViews != null) {
            double temperature = weather.getTemperatura().get(0);
            remoteViews.setTextViewText(R.id.temperature_txt, UnitHelper.getTemperature(context, temperature) + "°" + UnitHelper.getSavedUnit(context));


            double humidity = weather.getUmiditate().get(0);
            remoteViews.setTextViewText(R.id.humidity_txt, humidity + "%");
        }
    }

    @Override
    public void onWeatherReceived(WeatherResponse response) {
        this.weather = response;
        refreshWidget();
    }

    private void getWeather(Context context) {
        if (ConnectionUtil.hasNetworkConnection(context)) {
            WebserviceHelper.getWeatherForDate(DateUtil.getCurrentDate(), this, context);
        } else DatabaseHelper.getWeatherForDate(DateUtil.getCurrentDate(), this);
    }

    private void refreshWidget() {
        if (context != null) {
            Intent intent = new Intent(context, WidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            context.sendBroadcast(intent);
        }
    }



}
