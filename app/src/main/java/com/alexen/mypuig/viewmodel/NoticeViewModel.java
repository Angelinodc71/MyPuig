package com.alexen.mypuig.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.alexen.mypuig.api.Connection;
import com.alexen.mypuig.api.Discussion;
import com.alexen.mypuig.api.Discussions;
import com.alexen.mypuig.api.MoodleAPI;
import com.alexen.mypuig.model.Chat;
import com.alexen.mypuig.model.Notice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alexen.mypuig.api.Connection.api;

public class NoticeViewModel extends AndroidViewModel {

    private MutableLiveData<List<Discussion>> listaNotices = new MutableLiveData<List<Discussion>>();
    private MutableLiveData<Discussion> noticeSeleccionado = new MutableLiveData<>();

    private MutableLiveData<List<Chat>> listaChats = new MutableLiveData<>();
    private MutableLiveData<Chat> chatSeleccionado = new MutableLiveData<>();


    public NoticeViewModel(@NonNull Application application) {
        super(application);
        rellenarListaNoticias();

    }

    public void rellenarListaNoticias(){
//        Log.e("ABC",token);
        api.discussions(Connection.getToken(),Connection.getForumid()).enqueue(new Callback<Discussions>() {
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
    }
//    public void rellenarListaElementos(){
//        List<Notice> notices = new ArrayList<>();
//        for (int i = 0; i < 50; i++) {
//            Notice notice = new Notice(
//                    "Fernando Porrino Serrano ",
//                    "Oferta prácticas IT sistemas y microinformática",
//                    "Mi nombre es Dulce Afonso. Soy la reponsable del Dpt...",
//                    "Buenos días,\n" +
//                            "\n" +
//                            "\n" +
//                            "Mi nombre es Dulce Afonso. Soy la responsable del Dpto. de Informática y Comunicaciones de la Unidad de Tecnología Marina, un centro del CSIC que tiene su sede central en Barcelona (www.utm.csic.es). Me pongo en contacto con ustedes porque necesitamos personal para nuestro departamento que se dé de alta en la bolsa de trabajo del CSIC, de modo que puedan acceder a nuestra oferta laboral.\n" +
//                            "\n" +
//                            "Los candidatos deberían tener el título de Técnico Superior de FP de cualquiera de las especialidades de informática (Administración de Sistemas Informáticos, Desarrollo de Aplicaciones Web, o bien Desarrollo de Aplicaciones Multiplataforma). La carga principal del trabajo se desarrollaría en nuestra sede de Barcelona, con posibilidad de viajes (no obligatorios) para la preparación de los sistemas informáticos implicados en campañas oceanográficas y realización de campañas como técnico informático a bordo de los buques oceanográficos del CSIC.\n",
//                    "Monday, 27 May 2019, 11:15",
//                    "27 de mar.",
//                    "drawable-hdpi/user_image.png",
//                    true);
//            notices.add(notice);
//            insertarChat(notice.getAutor(),notice.getTema(),notice.getImgAutor());
//        }
//        listaNotices.setValue(notices);
//    }

    public void insertarChat(String autor, String tema, String image){
        List<Chat> chats = new ArrayList<>();
        Chat chat = new Chat(autor, tema, image);
        chats.add(chat);

        listaChats.setValue(chats);
    }


    public void establecerElementoSeleccionado(Discussion discussion){
        noticeSeleccionado.setValue(discussion);
    }

    public MutableLiveData<List<Discussion>> getListaNotices() {
        return listaNotices;
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
