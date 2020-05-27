package com.alexen.mypuig.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.alexen.mypuig.MainActivity;
import com.alexen.mypuig.api.Connection;
import com.alexen.mypuig.api.Discussion;
import com.alexen.mypuig.api.Discussions;
import com.alexen.mypuig.api.Token;
import com.alexen.mypuig.model.Chat;
import com.alexen.mypuig.model.User;
import com.google.common.io.LittleEndianDataInputStream;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.alexen.mypuig.api.Connection.api;

public class MoodleViewModel extends AndroidViewModel {
    FirebaseFirestore db;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;


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
    public enum EstadoDiscussionFav{
        INITIAL,
        FAV,
        NOT_FAV
    }
    public MutableLiveData<EstadoLogin> estadoLogin = new MutableLiveData<>();
    public MutableLiveData<EstadoToken> estadoToken = new MutableLiveData<>();
    public MutableLiveData<EstadoDiscussionFav> estadoDiscussionFav = new MutableLiveData<>();

    public MutableLiveData<User> userFavs = new MutableLiveData<>();
    public MutableLiveData<String> token = new MutableLiveData<>();
    public MutableLiveData<String> nombre = new MutableLiveData<>();
    public MutableLiveData<Boolean> userCargado = new MutableLiveData<>();

    private MutableLiveData<List<String>> listaFav = new MutableLiveData<>();
    private MutableLiveData<List<Discussion>> listaNoticesFav = new MutableLiveData<List<Discussion>>();

    private MutableLiveData<List<Discussion>> listaNotices = new MutableLiveData<List<Discussion>>();
    private MutableLiveData<Discussion> noticeSeleccionado = new MutableLiveData<>();

    private MutableLiveData<List<Chat>> listaChats = new MutableLiveData<>();
    private MutableLiveData<Chat> chatSeleccionado = new MutableLiveData<>();

    public MoodleViewModel(@NonNull Application application) {
        super(application);
        db = FirebaseFirestore.getInstance();
        estadoDiscussionFav.setValue(EstadoDiscussionFav.INITIAL);
        mAuth = FirebaseAuth.getInstance();
        userCargado.setValue(false);
    }
    public void initialLogin(){
        estadoLogin.setValue(EstadoLogin.INITIAL);
        estadoToken.setValue(EstadoToken.INITIAL);
    }
    public void cerrarSesion(){
        currentUser = null;
        token.postValue(null);
        nombre.postValue(null);
        userCargado.postValue(false);
        mAuth.signOut();
    }

    public void guardarNombreUsuario(String username){
        nombre.postValue(username);
        addDataUser();
    }
    public String obtenerNombreUsuario(){
        if (currentUser.getDisplayName().isEmpty() | currentUser.getDisplayName()==null){//iniciado sesion por correo
            Log.e("ABC",nombre.getValue());
            return nombre.getValue();
        }else {//iniciado sesion por google
            return currentUser.getDisplayName();
        }
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

    public LiveData<List<Discussion>> getNoticiasFav = Transformations.switchMap(token, new Function<String, LiveData<List<Discussion>>>() {
        @Override
        public LiveData<List<Discussion>> apply(String input) {
            api.discussions(input,Connection.getForumid()).enqueue(new Callback<Discussions>() {
                @Override
                public void onResponse(Call<Discussions> call, Response<Discussions> response) {
                    Log.e("ABC", response.message());
                    if(response.body() != null){
//                    for(Discussion d: response.body().discussions) Log.e("ABC", d.toString());
                        listaNotices.postValue(response.body().discussions);
                        List<Discussion> newList = new ArrayList<>();
                        if (listaNotices.getValue()!=null && listaFav.getValue()!=null){
                            for (Discussion discussion : listaNotices.getValue()){
                                for (String id : listaFav.getValue()){
                                    if (discussion.id.contains(id)){
                                        newList.add(discussion);
                                        listaNoticesFav.postValue(newList);
                                    }
                                }
                            }
                        }
                    } else {
                        Log.e("ABC", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Discussions> call, Throwable t) {
                    Log.e("ABC", "getDiscussions connection failed");
                }
            });
            return listaNoticesFav;
        }
    });

    public void addDiscussionFav(String id){
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        Discussion discussionFav = new Discussion(id);
// Add a new document with a generated ID
        Map<String, Map<String, Boolean>> data = new HashMap<>();
        Map<String, Boolean> data2 = new HashMap<>();
        data2.put(id,true);
        data.put("favs",data2);
        db.collection("favs")
                .document(currentUser.getUid())
                .set(data, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.w(TAG, "TOKEN ->"+ discussionFav.toString()))
//                .addOnSuccessListener((OnSuccessListener<DocumentReference>) documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    @SuppressLint("NewApi")
    public void removeDiscussionFav(String id) {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        Discussion discussionFav = new Discussion(id);
// Add a new document with a generated ID
        Map<String, Map<String, Boolean>> data = new HashMap<>();
        Map<String, Boolean> data2 = new HashMap<>();
        data2.put(id,false);
        data.put("favs",data2);
        db.collection("favs")
                .document(currentUser.getUid())
                .set(data, SetOptions.merge());
    }

    public void addDataUser(){
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Create a new userFavs with a first and last name
        User user = new User(currentUser.getUid(),token.getValue(),nombre.getValue());

// Add a new document with a generated ID
        db.collection("users")
                .document(currentUser.getUid())
                .set(user)
                .addOnSuccessListener(aVoid -> Log.w(TAG, "TOKEN ->"+ user.token))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    public void readDataUser(){
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
                Log.e("ABC",currentUser.getUid());
        db.collection("users")
                .document(currentUser.getUid())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (e!=null)return;

                        User userTmp = documentSnapshot.toObject(User.class);
                        if (userTmp==null){
                            estadoToken.postValue(EstadoToken.TOKEN_NO_REGISTRADO);
                        }else {
                            if (userTmp.token!=null){
                                Log.e("ABC",userTmp.token);

                                estadoToken.postValue(EstadoToken.TOKEN_REGISTRADO);
                                token.postValue(userTmp.token);
                                nombre.postValue(userTmp.name);
                            }
                        }
                    }
                });

    }

    public void readFavsUser(){
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.e("ABC",currentUser.getUid());
        db.collection("favs")
                .document(currentUser.getUid())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (e!=null)return;

                        User userTmp = documentSnapshot.toObject(User.class);

                        if (userTmp!=null){
                            HashMap<String, Boolean> favsTmp = userTmp.favs;
                            List<String> favId = new ArrayList<>();
//                            favsTmp.forEach((k,v) -> {
//                                if (v)favId.add(k);
//                            });
                            for(Map.Entry<String, Boolean> entry : favsTmp.entrySet()) {
                                String k = entry.getKey();
                                Boolean v = entry.getValue();
                                if (v)favId.add(k);
                                // do what you have to do here
                                // In your case, another loop.
                            }
                            listaFav.postValue(favId);
                            userFavs.postValue(userTmp);
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
