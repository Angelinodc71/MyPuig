package com.alexen.mypuig.api;
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
        System.out.println("start");
        api =  new Retrofit.Builder()
                .baseUrl(Config.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MoodleAPI.class);

        login();
        System.out.println(token);
    }

    private static void login(){
        System.out.println("login...");
        api.login(Config.username, Config.password).enqueue((Callback<Token>) response -> {
            System.out.println("TOKEN = " + response.token);
            token = response.token;
            getDiscussions();
        });
    }

    private static void getDiscussions() {
        System.out.println("forum...");
        api.discussions(token, Config.forumid).enqueue((Callback<Discussions>) response ->{
            response.discussions.forEach(d -> System.out.format("%s%n%s%n%s%n%s%n-----%n", d.id, d.name, d.message, d.subject));
        });
    }
}
