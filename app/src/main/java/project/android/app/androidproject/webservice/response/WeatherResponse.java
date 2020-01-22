package project.android.app.androidproject.webservice.response;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import project.android.app.androidproject.db.Converters;

/**
 * Created by Daria on 8/8/2018.
 **/
@Entity(tableName = "weather")
public class WeatherResponse {
    @NonNull
    @PrimaryKey
    private String weatherDate;

    @Expose
    @TypeConverters({Converters.class})
    private ArrayList<String> ore;

    @Expose
    @TypeConverters({Converters.class})
    private ArrayList<Double> umiditate;

    @Expose
    @TypeConverters({Converters.class})
    private ArrayList<Double> temperatura;

    @SerializedName("minDate")
    @Expose
    private String minDate;

    @SerializedName("maxDate")
    @Expose
    private String maxDate;


    @Override
    public String toString() {
        return "WeatherResponse{" + "ore=" + ore + ", temperatura=" + temperatura + ", umiditate=" + umiditate + ", minDate='" + minDate + '\'' + ", maxDate='" + maxDate + '\'' + '}';
    }


    @NonNull
    public String getWeatherDate() {
        return weatherDate;
    }

    public void setWeatherDate(@NonNull String weatherDate) {
        this.weatherDate = weatherDate;
    }

    public ArrayList<String> getOre() {
        return ore;
    }

    public void setOre(ArrayList<String> ore) {
        this.ore = ore;
    }

    public ArrayList<Double> getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(ArrayList<Double> temperatura) {
        this.temperatura = temperatura;
    }

    public ArrayList<Double> getUmiditate() {
        return umiditate;
    }

    public void setMinDate(String minDate) { this.minDate = minDate; }

    public String getMaxDate() { return maxDate; }

    public void setMaxDate(String maxDate) { this.maxDate = maxDate; }

    public void setUmiditate(ArrayList<Double> umiditate) {
        this.umiditate = umiditate;
    }

    public String getMinDate() {
        return minDate;
    }


}
