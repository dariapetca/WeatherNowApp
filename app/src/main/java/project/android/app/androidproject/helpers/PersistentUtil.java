package project.android.app.androidproject.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * Created by Daria on 8/10/2018.
 **/
public class PersistentUtil {
    private static final String LANGUAGE = "LANGUAGE";

    private static final String TEMPERATURE_REFERENCE = "TEMPERATURE_REFERENCE";

    private static final String THEME = "THEME";

    private static SharedPreferences obtainSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static boolean setString(Context context, String strValue, String prefKey) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.putString(prefKey, strValue);
        return e.commit();
    }
    private static boolean setBoolean(Context context, boolean value, String prefKey) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.putBoolean(prefKey, value);
        return e.commit();
    }
    public static int getLanguage(Context con) { return obtainSharedPreferences(con).getInt(LANGUAGE, 0); }
    public static boolean setTemperatureReference(Context context, int value) { return setInt(context, value, TEMPERATURE_REFERENCE); }

    public static boolean setTheme(Context context, int value) { return setInt(context, value, THEME); }
    private static boolean setInt(Context context, int value, String prefKey) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.putInt(prefKey, value);
        return e.commit();
    }

    private static boolean setLong(Context context, long value, String prefKey) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.putLong(prefKey, value);
        return e.commit();
    }public static int getTemperatureReference(Context con) {
        return obtainSharedPreferences(con).getInt(TEMPERATURE_REFERENCE, 0);
    }

    public static int getTheme(Context con) {
        return obtainSharedPreferences(con).getInt(THEME, 0);
    }

    public static boolean setLanguage(Context context, int value) {
        return setInt(context, value, LANGUAGE);
    }

}
