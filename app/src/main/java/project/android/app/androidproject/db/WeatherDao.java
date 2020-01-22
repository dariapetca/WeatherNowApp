package project.android.app.androidproject.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import project.android.app.androidproject.webservice.response.WeatherResponse;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Daria on 8/9/2018.
 **/
@Dao
public interface WeatherDao {

    @Query("SELECT * FROM weather WHERE weatherDate=:date")
    LiveData<WeatherResponse> getWeather(String date);

    @Insert(onConflict = REPLACE)
    void insertWeather(WeatherResponse user);


}
