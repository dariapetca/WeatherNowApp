package project.android.app.androidproject.application;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import project.android.app.androidproject.R;
import project.android.app.androidproject.db.DatabaseHelper;

/**
 * Created by Daria on 8/8/2018.
 **/
public class AndroidProjectApplication extends Application {
    private static AndroidProjectApplication instance;
    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    public static synchronized AndroidProjectApplication getInstance() {
        return instance;
    }

    public void onCreate() {
        super.onCreate();
        instance = this;
        DatabaseHelper.createDatabase(getApplicationContext());
        sAnalytics = GoogleAnalytics.getInstance(this);
    }

    synchronized public Tracker getDefaultTracker() {
        if (sTracker == null) { sTracker = sAnalytics.newTracker(R.xml.global_tracker); }
        return sTracker;
    }
}
