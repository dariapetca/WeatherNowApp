package project.android.app.androidproject.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Daria on 8/10/2018.
 **/
public class ConnectionUtil {
    public static final String TAG = ConnectionUtil.class.getSimpleName();
    public static boolean hasNetworkConnection(Context context) {
        try {
            if (context != null) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                return (activeNetwork != null) && activeNetwork.isConnectedOrConnecting();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }return false;
    }
}
