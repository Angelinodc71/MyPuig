package com.alexen.mypuig.api;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Connection {
    private static String token;
    private static String baseUrl = "https://moodle.elpuig.xeill.net/";
    private static String courseId = "277";
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


    public static void login(String username, String password) {
        Log.e("ABC","login...");
        Log.e("ABC",username +" "+password);

        api.login(username, password).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.body() != null){
//                    token = response.body().token;
                    setToken(response.body().token);
                    Log.e("ABC","TOKEN = " + token);

                } else {
                    Log.e("ABC", response.message());
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e("ABC", "Login connection failed");
            }

        });
    }
//    public static void forum() {
//        Log.e("ABC","forum...");
//        Log.e("ABC",courseId);
//
//        api.courses(getToken()).enqueue(new Callback<Forum>() {
//            @Override
//            public void onResponse(Call<Forum> call, Response<Forum> response) {
//                if(response.body() != null){
//                    setForumid(response.body().id);
//                    Log.e("ABC","ForumID = " + getForumid());
////                    getDiscussions();
//                } else {
//                    Log.e("ABC", response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Forum> call, Throwable t) {
//                Log.e("ABC", "Login connection failed");
//            }
//
//        });
//    }

//    public static void getDiscussions() {
//        Log.e("ABC","forum...");
//
//        api.discussions(token, forumid).enqueue(new Callback<Discussions>() {
//            @Override
//            public void onResponse(Call<Discussions> call, Response<Discussions> response) {
//                Log.e("ABC", response.message());
//                if(response.body() != null){
//                    for(Discussion d: response.body().discussions) Log.e("ABC", d.toString());
//                } else {
//                    Log.e("ABC", response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Discussions> call, Throwable t) {
//                Log.e("ABC", "getDiscussions connection failed");
//            }
//        });
//    }


    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Connection.token = token;
    }

    public static void setForumid(String forumid) {
        Connection.forumid = forumid;
    }

    public static String getForumid() {
        return forumid;
    }

    public static String getCourseId() {
        return courseId;
    }
}
