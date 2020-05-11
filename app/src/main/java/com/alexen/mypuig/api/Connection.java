package com.alexen.mypuig.api;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Connection {
    private static String baseUrl = "https://moodle.elpuig.xeill.net/";
    private static String forumid = "440";

    public static MoodleAPI api = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();

                                long t1 = System.nanoTime();
                                Log.e("INTERCEPTOR", String.format("Sending request %s on %s%n%s",
                                        request.url(), chain.connection(), request.headers()));

                                okhttp3.Response response = chain.proceed(request);

                                long t2 = System.nanoTime();
                                Log.e("INTERCEPTOR---", String.format("Received response for %s in %.1fms%n%s",
                                        response.request().url(), (t2 - t1) / 1e6d, response.headers()));

                                return response;
                            }
                        })
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MoodleAPI.class);

    public static String getForumid() {
        return forumid;
    }
}
