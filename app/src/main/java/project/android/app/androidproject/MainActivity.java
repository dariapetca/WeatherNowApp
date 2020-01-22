package project.android.app.androidproject;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Locale;

import project.android.app.androidproject.about.AboutFragment;
import project.android.app.androidproject.application.AndroidProjectApplication;
import project.android.app.androidproject.chart.ChartFragment;
import project.android.app.androidproject.db.WeatherViewModeFactory;
import project.android.app.androidproject.db.WeatherViewModel;
import project.android.app.androidproject.global.IGetWeather;
import project.android.app.androidproject.global.OnWeatherReceived;
import project.android.app.androidproject.helpers.ConnectionUtil;
import project.android.app.androidproject.helpers.DateUtil;
import project.android.app.androidproject.helpers.PersistentUtil;
import project.android.app.androidproject.measurements.MeasurementsFragment;
import project.android.app.androidproject.settings.SettingsFragment;
import project.android.app.androidproject.weather.WeatherFragment;
import project.android.app.androidproject.webservice.WebserviceHelper;
import project.android.app.androidproject.webservice.response.WeatherResponse;

import static project.android.app.androidproject.constants.Constants.ADD_MOB_KEY;

public class MainActivity extends AppCompatActivity implements IGetWeather {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String ACTIVE_SCREEN = "ACTIVE_SCREEN";
    private BottomNavigationView navigation;
    private WeatherFragment weatherFragment;
    private MeasurementsFragment measurementsFragment;
    private ChartFragment chartFragment;
    private SettingsFragment settingsFragment;
    private AboutFragment aboutFragment;
    private AdView mAdView;
    private ProgressBar progressBar;
    private WeatherViewModel weatherViewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_weather:
                    navigateToFragment(WeatherFragment.class);
                    setTitle(R.string.title_weather);
                    sendAnalitycsScreenView(getString(R.string.title_weather));
                    return true;
                case R.id.navigation_measurements:
                    setTitle(R.string.title_measurements);
                    sendAnalitycsScreenView(getString(R.string.title_measurements));
                    navigateToFragment(MeasurementsFragment.class);
                    return true;
                case R.id.navigation_chart:
                    setTitle(R.string.title_chart);
                    sendAnalitycsScreenView(getString(R.string.title_chart));
                    navigateToFragment(ChartFragment.class);
                    return true;
                case R.id.navigation_settings:
                    setTitle(R.string.title_settings);
                    sendAnalitycsScreenView(getString(R.string.title_settings));
                    navigateToFragment(SettingsFragment.class);
                    return true;
                case R.id.navigation_about_us:
                    setTitle(R.string.title_about_us);
                    sendAnalitycsScreenView(getString(R.string.title_about_us));
                    navigateToFragment(AboutFragment.class);
                    return true;
            }
            return false;
        }
    };

    private void navigateToFragment(Class fragmentClass) {
        if (fragmentClass.equals(WeatherFragment.class)) {
            navigateToFragment(weatherFragment);
        } else if (fragmentClass.equals(SettingsFragment.class)) {
            navigateToFragment(settingsFragment);
        } else if (fragmentClass.equals(AboutFragment.class)) {
            navigateToFragment(aboutFragment);
        } else if (fragmentClass.equals(ChartFragment.class)) {
            navigateToFragment(chartFragment);
        } else if (fragmentClass.equals(MeasurementsFragment.class)) {
            navigateToFragment(measurementsFragment);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.title_weather);

        navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        progressBar = findViewById(R.id.progress_bar);


        WeatherViewModeFactory factory = new WeatherViewModeFactory(
                (AndroidProjectApplication) this.getApplication(), DateUtil.getCurrentDate());
        weatherViewModel = ViewModelProviders.of(this, factory).get(WeatherViewModel.class);

        initialiseFragments();

        navigateToFragment(WeatherFragment.class);
        navigation.setSelectedItemId(R.id.navigation_weather);

    }

    /**private void setUpAdMob() {
        MobileAds.initialize(this, ADD_MOB_KEY);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(ADD_MOB_KEY);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {}

            @Override
            public void onAdFailedToLoad(int errorCode) {}

            @Override
            public void onAdOpened() { }

            @Override
            public void onAdLeftApplication() {}

            @Override
            public void onAdClosed() {}
        });
    }*/


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(ACTIVE_SCREEN, navigation.getSelectedItemId());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        int someIntValue = savedInstanceState.getInt(ACTIVE_SCREEN);
        View view = navigation.findViewById(someIntValue);
        view.performClick();
        progressBar.setVisibility(View.GONE);
    }
    private void initialiseFragments() {
        weatherFragment = WeatherFragment.newInstance();
        measurementsFragment = MeasurementsFragment.newInstance();
        chartFragment = ChartFragment.newInstance();
        settingsFragment = SettingsFragment.newInstance();
        aboutFragment = AboutFragment.newInstance();
    }

    private void navigateToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public void getWeather(String date, final OnWeatherReceived callback) {
        if (ConnectionUtil.hasNetworkConnection(MainActivity.this)) {
            WebserviceHelper.getWeatherForDate(date, new OnWeatherReceived() {
                @Override
                public void onWeatherReceived(WeatherResponse response) {
                    weatherViewModel.insertWeather(response);
                    callback.onWeatherReceived(response);
                }
            }, MainActivity.this);
        } else {
            LiveData<WeatherResponse> liveDataResponse = weatherViewModel.getWeather(date);
            if (liveDataResponse == null) {
                hideProgressHUD();
                Toast.makeText(MainActivity.this, R.string.could_not_get_weather, Toast.LENGTH_SHORT);
                return;
            }
            weatherViewModel.getWeather(date).observe(MainActivity.this, new Observer<WeatherResponse>() {
                @Override
                public void onChanged(@Nullable final WeatherResponse weatherResponse) {
                    callback.onWeatherReceived(weatherResponse);
                }
            });
        }


    }

    public void showProgressHUD() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressHUD() { progressBar.setVisibility(View.GONE); }


    @Override
    protected void attachBaseContext(Context base) { super.attachBaseContext(updateBaseContextLocale(base)); }

    public Context updateBaseContextLocale(Context context) {
        Locale language = PersistentUtil.getLanguage(context) == 0 ? Locale.ENGLISH : new Locale(context.getString(R.string.ro)); // Helper method to get saved language from SharedPreferences
        Configuration cfg = new Configuration();

        cfg.locale = language;

        context.getResources().updateConfiguration(cfg, null);
        return context;
    }

    private void sendAnalitycsScreenView(String screen) {
        AndroidProjectApplication application = (AndroidProjectApplication) getApplication();
        Tracker mTracker = application.getDefaultTracker();
        mTracker.setScreenName(getString(R.string.view_screen) + screen);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
    public void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }



}
