package com.alexen.mypuig.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.alexen.mypuig.api.Connection;
import com.alexen.mypuig.api.Discussion;
import com.alexen.mypuig.api.Discussions;
import com.alexen.mypuig.api.Token;
import com.alexen.mypuig.model.Chat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alexen.mypuig.api.Connection.api;

public class MoodleViewModel extends AndroidViewModel {

    public enum EstadoLogin{
        INITIAL,
        LOGINOK,
        LOGINFAILED
    }
    MutableLiveData<String> terminoBusqueda = new MutableLiveData<>();

    public MutableLiveData<EstadoLogin> estadoLogin = new MutableLiveData<>();
    public MutableLiveData<String> token = new MutableLiveData<>();
    private MutableLiveData<List<Discussion>> listaNotices = new MutableLiveData<List<Discussion>>();
    private MutableLiveData<Discussion> noticeSeleccionado = new MutableLiveData<>();

    private MutableLiveData<List<Chat>> listaChats = new MutableLiveData<>();
    private MutableLiveData<Chat> chatSeleccionado = new MutableLiveData<>();

    public MoodleViewModel(@NonNull Application application) {
        super(application);

    }
    public void initialLogin(){
        estadoLogin.setValue(EstadoLogin.INITIAL);
    }
    public void login(String username, String password) {
        Log.e("ABC","login...");
        Log.e("ABC",username +" "+password);

        api.login(username, password).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.body() != null){
                    if (response.body().token!=null) {
                        token.postValue(response.body().token);
                        estadoLogin.postValue(EstadoLogin.LOGINOK);
                    }
                    else {
                        estadoLogin.postValue(EstadoLogin.LOGINFAILED);
                    }
                    Log.e("ABC","TOKEN = " + token);


                } else {
                    Log.e("ABC", response.message());
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e("ABC", "Login connection failed");
                estadoLogin.postValue(EstadoLogin.LOGINFAILED);
            }

        });
    }

    public LiveData<List<Discussion>> getNoticias = Transformations.switchMap(token, new Function<String, LiveData<List<Discussion>>>() {
        @Override
        public LiveData<List<Discussion>> apply(String input) {
            api.discussions(input,Connection.getForumid()).enqueue(new Callback<Discussions>() {
                @Override
                public void onResponse(Call<Discussions> call, Response<Discussions> response) {
                    Log.e("ABC", response.message());
                    if(response.body() != null){
//                    for(Discussion d: response.body().discussions) Log.e("ABC", d.toString());
                        listaNotices.postValue(response.body().discussions);
                    } else {
                        Log.e("ABC", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Discussions> call, Throwable t) {
                    Log.e("ABC", "getDiscussions connection failed");
                }
            });
            return listaNotices;
        }
    });

    public void insertarChat(String autor, String tema, String image){
        List<Chat> chats = new ArrayList<>();
        Chat chat = new Chat(autor, tema, image);
        chats.add(chat);

        listaChats.setValue(chats);
    }
    public void establecerTerminoBusqueda(String termino){
        terminoBusqueda.setValue(termino);
    }

    public void establecerElementoSeleccionado(Discussion discussion){
        noticeSeleccionado.setValue(discussion);
    }

    public MutableLiveData<Discussion> getNoticeSeleccionado() {
        return noticeSeleccionado;
    }



    public void establecerElementoSeleccionado(Chat chat){
        chatSeleccionado.setValue(chat);
    }


    public MutableLiveData<Chat> getChatSeleccionado() {
        return chatSeleccionado;
    }
}
