package com.alexen.mypuig.api;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class Config {
    static String baseUrl = "http://192.168.0.23/moodle/";
    static String username = "admin";
    static String password = "-Bakugan8";
    static String courseId = "2";
    static String forumid = "1";
}


public class Connection {

    private static MoodleAPI api;

    private static String token;
//    private static HashMap<String, String> id2username = new HashMap<>();

    public static void startConnection() {
        Log.e("ABC","start");
        Log.e("ABC",Config.baseUrl);

        api = new Retrofit.Builder()
                .baseUrl(Config.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MoodleAPI.class);

        login();
    }

    private static void login() {
        Log.e("ABC","login...");
        Log.e("ABC",Config.username +Config.password);

        api.login(Config.username, Config.password).enqueue((Callback<Token>) response -> {
            token = response.token;
            Log.e("ABC","TOKEN = " + token);

            getDiscussions();
        });

    }

    private static void getDiscussions() {
        Log.e("ABC","forum...");

        api.discussions(token, Config.forumid).enqueue((Callback<Discussions>) response -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                response.discussions.forEach(d -> System.out.format("%s%n%s%n%s%n%s%n-----%n", d.id, d.name, d.message, d.subject));
            }
            Log.e("ABC",token);
        });
    }
}
