package project.android.app.androidproject.helpers;

/**
 * Created by Daria on 8/10/2018.
 **/
public class WeatherUnitConverter {
    public static double celsiustoKelvin(double tempCelsius) {
        return tempCelsius + 273.15;

    }

    public static double celsiustoFahrenheit(double tempCelsius) {
        return (tempCelsius * 9.0 / 5.0) + 32;

    }


}
