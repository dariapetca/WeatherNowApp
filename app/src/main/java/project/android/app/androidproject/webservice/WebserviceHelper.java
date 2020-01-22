package project.android.app.androidproject.webservice;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import project.android.app.androidproject.R;
import project.android.app.androidproject.global.OnWeatherReceived;
import project.android.app.androidproject.helpers.DialogFactory;
import project.android.app.androidproject.webservice.response.WeatherResponse;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Daria on 8/10/2018.
 **/
public class WebserviceHelper {
    private static final String TAG = WebserviceHelper.class.getSimpleName();

    public static void getWeatherForDate(final String date, final OnWeatherReceived callback, final Context ctx) {
        if (ctx == null)  return;
        new GetWeatherAsync(date, callback, ctx).execute();


    }

    static class GetWeatherAsync extends AsyncTask<Void, Void, Void> {
        private String date;
        private OnWeatherReceived callback;
        private Context ctx;

        public GetWeatherAsync(String date, OnWeatherReceived callback, Context ctx) {
            this.date = date;
            this.callback = callback;
            this.ctx = ctx;
        }

        protected Void doInBackground(Void... params) {
            DataManager.getInstance().getWeatherService().getWeather(date).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn(new Func1<Throwable, WeatherResponse>() {
                        @Override
                        public WeatherResponse call(Throwable throwable) {
                            DialogFactory.createSimpleOkErrorDialog(ctx, R.string.dialog_error_title, R.string.dialog_weather_error);
                            return null;
                        }
                    })
                    .subscribe(new Action1<WeatherResponse>() {
                        @Override
                        public void call(WeatherResponse weatherResponse) {
                            weatherResponse.setWeatherDate(date);//set weather date
                            Log.d(TAG, ctx.getString(R.string.received_weather_response) + weatherResponse.toString());
                            callback.onWeatherReceived(weatherResponse);
                        }
                    });
            return null;

        }
    }
}
