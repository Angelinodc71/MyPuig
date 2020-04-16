package com.alexen.mypuig.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.alexen.mypuig.model.Chat;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel  extends AndroidViewModel {

    private MutableLiveData<List<Chat>> listaMensajes = new MutableLiveData<>();
    private MutableLiveData<Chat> chatSeleccionado = new MutableLiveData<>();

    private String msgTmp = "";

    public ChatViewModel(@NonNull Application application) {
        super(application);

    }

    public void insertarMensajeChat(String mensajePropio){
        List<Chat> chats = new ArrayList<>();
            Chat chat = new Chat(mensajePropio,"");
            chats.add(chat);

        listaMensajes.setValue(chats);
    }

    public void establecerElementoSeleccionado(Chat chat){
        chatSeleccionado.setValue(chat);
    }

    public MutableLiveData<List<Chat>> getListaMensajes() {
        return listaMensajes;
    }

    public MutableLiveData<Chat> getChatSeleccionado() {
        return chatSeleccionado;
    }
}
