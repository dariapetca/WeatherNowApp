package project.android.app.androidproject.helpers;

import android.content.Context;

/**
 * Created by Daria on 8/10/2018.
 **/
public class UnitHelper {
    public static String getSavedUnit(Context ctx) {
        int savedUnit = PersistentUtil.getTemperatureReference(ctx);
        switch (savedUnit) {
            case 0:
                return "C";
            case 1:
                return "F";
            case 2:
                return "K";
        }
        return "";
    }

    public static double getTemperature(Context ctx, double temperature) {
        int savedUnit = PersistentUtil.getTemperatureReference(ctx);
        double finalValue = 0;
        switch (savedUnit) {
            case 0:
                finalValue = temperature;
                break;
            case 1:
                finalValue = WeatherUnitConverter.celsiustoFahrenheit(temperature);
                break;
            case 2:
                finalValue = WeatherUnitConverter.celsiustoKelvin(temperature);
                break;

        }

        return Math.round(finalValue * 10.0) / 10.0;
    }
}
