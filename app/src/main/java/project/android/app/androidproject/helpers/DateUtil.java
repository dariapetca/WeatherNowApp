package project.android.app.androidproject.helpers;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Daria on 8/10/2018.
 **/
public class DateUtil {

    private final static String dateFormatter = "yyyy-MM-ddHH:mm:ss";

    public static String getCurrentDate() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(currentTime);
        return currentDateandTime;
    }


    public static String getParsedDateTime(Date datetime) {
        if (datetime == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String currentDateandTime = sdf.format(datetime);
        return currentDateandTime;
    }


    public static String dateToISO8601(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String asISO = df.format(date);
        return asISO;
    }

    public static String dateStringToReadableDate(String lastMeasurementTime) {
        Date date = dateFromISO8601(lastMeasurementTime);
        return getParsedDateTime(date);

    }

    public static Date dateFromISO8601(String dateString) {
        Date date = null;
        TimeZone tz = TimeZone.getTimeZone("Europe/Bucharest");
        DateFormat df;

        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZ");
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            Log.e("DateUtil", e.getMessage());
        }

        return date;
    }

    public static Date dateFromPattern(String dateString, String pattern) {
        Date date = null;
        TimeZone tz = TimeZone.getTimeZone("Europe/Bucharest");
        DateFormat df;

        df = new SimpleDateFormat(pattern);
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            Log.e("DateUtil", e.getMessage());
        }

        return date;
    }

    public static String dateToReadableDate(String dateString) {
        String pattern = "yyyy-MM-dd'T'HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            Log.e("DateUtil", e.getMessage());
        }
        return date.toGMTString();
    }

    private static Date getDate(String date) throws Exception {
        date = date.replaceAll("T", "");
        Date dateD = new SimpleDateFormat(dateFormatter, Locale.ENGLISH).parse(date);
        return dateD;
    }

    private static long convertToDate(String date) throws Exception {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(getDate(date));
        return calendar.getTimeInMillis();
    }

    public static float getTimeAsFraction(String date) throws Exception {
        int hour = getHour(date);
        int minute = getMinute(date);
        int seconds = getSeconds(date);

        return hour + ((float) minute / 60) + ((float) seconds / 60);
    }

    private static int getHour(String date) throws Exception {
        return getDate(date).getHours();
    }

    private static int getMinute(String date) throws Exception {
        return getDate(date).getMinutes();
    }

    private static int getSeconds(String date) throws Exception {
        return getDate(date).getSeconds();
    }

}