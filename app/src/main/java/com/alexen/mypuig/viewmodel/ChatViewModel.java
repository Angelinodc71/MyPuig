package com.alexen.mypuig.viewmodel;

import android.app.Application;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.alexen.mypuig.model.Mensaje;
import com.alexen.mypuig.model.Notice;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel  extends AndroidViewModel {


    private MutableLiveData<List<Mensaje>> listaMensajes = new MutableLiveData<>();
    private MutableLiveData<Mensaje> mensajeSeleccionado = new MutableLiveData<>();

    public ChatViewModel(@NonNull Application application) {
        super(application);
    }
    public void insertarMensaje(String mensaje){
        List<Mensaje> mensajes = new ArrayList<>();
        Mensaje msg = new Mensaje(mensaje);
        mensajes.add(msg);

        Log.e("alv", mensaje);
        listaMensajes.setValue(mensajes);
    }

    public MutableLiveData<List<Mensaje>> getListaMensajes() {
        return listaMensajes;
    }
    public void establecerElementoSeleccionado(Mensaje mensaje){
        mensajeSeleccionado.setValue(mensaje);
    }

}
