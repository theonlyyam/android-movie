package com.ysdc.movie.data.network;

import android.app.Application;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.ysdc.movie.BuildConfig;
import com.ysdc.movie.app.GeneralConfig;
import com.ysdc.movie.data.network.config.interceptor.BasicInterceptor;
import com.ysdc.movie.data.network.config.interceptor.ConnectivityInterceptor;
import com.ysdc.movie.utils.NetworkUtils;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static com.ysdc.movie.utils.AppConstants.NETWORK_KEY_API;
import static com.ysdc.movie.utils.AppConstants.NETWORK_KEY_LANGUAGE;

/**
 * Created by david on 5/10/18.
 */

public class NetworkServiceCreator {

    private static final long TIMEOUT_IN_SECONDS = 20;

    private final Gson gson;
    private Retrofit retrofit;
    private final OkHttpClient.Builder httpClient;

    public NetworkServiceCreator(Gson gson, GeneralConfig generalConfig, Application application, NetworkUtils networkUtils) {
        this.gson = gson;

        this.httpClient = new OkHttpClient.Builder();
        this.httpClient.connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
        this.httpClient.readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        httpClient.cookieJar(new JavaNetCookieJar(cookieManager));

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Timber.d(message));
        if (generalConfig.isDebug()) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addNetworkInterceptor(new StethoInterceptor());
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        addInterceptor(httpLoggingInterceptor);
        addInterceptor(new ConnectivityInterceptor(application.getApplicationContext(), networkUtils));
        addInterceptor(new BasicInterceptor(NETWORK_KEY_API, generalConfig.getMovieDbKey()));
        addInterceptor(new BasicInterceptor(NETWORK_KEY_LANGUAGE, Locale.getDefault().getLanguage()));
    }

    /**
     * @return the retrofit instance to build network services
     */
    public Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(httpClient.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    /**
     * Hook to add custom request/response interceptors
     *
     * @param interceptor interceptor to add too our http client
     */
    private void addInterceptor(Interceptor interceptor) {
        httpClient.addInterceptor(interceptor);
    }
}
