package project.android.app.androidproject.webservice;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import project.android.app.androidproject.BuildConfig;
import project.android.app.androidproject.application.AndroidProjectApplication;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkModule {
    private static final String API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final FieldNamingPolicy API_JSON_NAMING_POLICY = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;
    private static final int TIMEOUT = 90;
    private static final String TAG = "NetworkModule";
    private static Retrofit retrofit;
    private static Gson gson = new GsonBuilder().setDateFormat(API_DATE_FORMAT).setLenient().disableHtmlEscaping().setFieldNamingPolicy(API_JSON_NAMING_POLICY).create();

    public static ApiService provideRepository() {
        return provideRetrofit().create(ApiService.class);
    }

    private static Retrofit provideRetrofit() {
        if (retrofit == null) {
            GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);
            OkHttpClient.Builder clientBuilder;
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                clientBuilder = new OkHttpClient.Builder().addInterceptor(logging);
            } else {
                clientBuilder = new OkHttpClient.Builder();
            }
            clientBuilder.addInterceptor(provideOfflineCacheInterceptor());
            clientBuilder.cache(provideCache());
            clientBuilder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
            clientBuilder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
            clientBuilder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);

            OkHttpClient client = clientBuilder.build();
            retrofit = new Retrofit.Builder().baseUrl(ApiService.BASE_URL).client(client).addConverterFactory(gsonConverterFactory).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        }
        return retrofit;
    }

    private static Cache provideCache() {
        Cache cache = null;
        try {
            cache = new Cache(new File(AndroidProjectApplication.getInstance().getCacheDir(), "http-cache"), 20 * 1024 * 1024); // 20 MB
        } catch (Exception e) { Log.e(TAG, "Could not create cache: " + e); }
        return cache;
    }

    private static Interceptor provideOfflineCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request();
                CacheControl cacheControl = new CacheControl.Builder().maxStale(1, TimeUnit.MINUTES).build();
                request = request.newBuilder().cacheControl(cacheControl).build();
                return chain.proceed(request);
            }
        };
    }
}