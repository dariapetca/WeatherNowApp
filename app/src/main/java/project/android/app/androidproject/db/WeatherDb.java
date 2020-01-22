package project.android.app.androidproject.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import project.android.app.androidproject.webservice.response.WeatherResponse;

import static project.android.app.androidproject.constants.DBConstants.DATABASENAME;

/**
 * Created by Daria on 8/9/2018.
 **/

@Database(entities = {WeatherResponse.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class WeatherDb extends RoomDatabase {
    public abstract WeatherDao weatherDao();


    private static WeatherDb INSTANCE;

    static WeatherDb getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WeatherDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WeatherDb.class, DATABASENAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
