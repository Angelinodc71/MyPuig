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
import com.alexen.mypuig.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.alexen.mypuig.api.Connection.api;

public class MoodleViewModel extends AndroidViewModel {
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    public enum EstadoLogin{
        INITIAL,
        LOGIN_OK,
        LOGIN_FAILED
    }
    public enum EstadoToken {
        INITIAL,
        TOKEN_NO_REGISTRADO,
        TOKEN_REGISTRADO
    }

    public MutableLiveData<EstadoLogin> estadoLogin = new MutableLiveData<>();
    public MutableLiveData<EstadoToken> estadoToken = new MutableLiveData<>();

    public MutableLiveData<String> token = new MutableLiveData<>();
    private MutableLiveData<List<Discussion>> listaNotices = new MutableLiveData<List<Discussion>>();
    private MutableLiveData<Discussion> noticeSeleccionado = new MutableLiveData<>();

    private MutableLiveData<List<Chat>> listaChats = new MutableLiveData<>();
    private MutableLiveData<Chat> chatSeleccionado = new MutableLiveData<>();

    public MoodleViewModel(@NonNull Application application) {
        super(application);
        db = FirebaseFirestore.getInstance();

    }
    public void initialLogin(){
        estadoLogin.setValue(EstadoLogin.INITIAL);
        estadoToken.setValue(EstadoToken.INITIAL);
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
                        estadoLogin.postValue(EstadoLogin.LOGIN_OK);
                    }
                    else {
                        estadoLogin.postValue(EstadoLogin.LOGIN_FAILED);
                    }
                    Log.e("ABC","TOKEN = " + token);


                } else {
                    Log.e("ABC", response.message());
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e("ABC", "Login connection failed");
                estadoLogin.postValue(EstadoLogin.LOGIN_FAILED);
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

    public void addDataUser(){
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Create a new user with a first and last name
        User user = new User(currentUser.getUid(),token.getValue());

// Add a new document with a generated ID
        db.collection("users")
                .document(currentUser.getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.w(TAG, "TOKEN ->"+ user.token);
                    }
                })
//                .addOnSuccessListener((OnSuccessListener<DocumentReference>) documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
    public void readDataUser(){
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
                Log.e("ABC",currentUser.getUid());
        db.collection("users")
                .document(currentUser.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        if (user==null){
                            estadoToken.postValue(EstadoToken.TOKEN_NO_REGISTRADO);
                        }else {
                            if (user.token!=null){
                                Log.e("ABC",user.token);

                                estadoToken.postValue(EstadoToken.TOKEN_REGISTRADO);
                                token.postValue(user.token);

                            }
                        }
                    }
                });
    }


    public void insertarChat(String autor, String tema, String image){
        List<Chat> chats = new ArrayList<>();
        Chat chat = new Chat(autor, tema, image);
        chats.add(chat);

        listaChats.setValue(chats);
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
