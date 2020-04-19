package com.alexen.mypuig.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.alexen.mypuig.model.Chat;
import com.alexen.mypuig.model.Notice;

import java.util.ArrayList;
import java.util.List;

public class NoticeViewModel extends AndroidViewModel {

    private MutableLiveData<List<Notice>> listaNotices = new MutableLiveData<>();
    private MutableLiveData<Notice> noticeSeleccionado = new MutableLiveData<>();

    private MutableLiveData<List<Chat>> listaChats = new MutableLiveData<>();
    private MutableLiveData<Chat> chatSeleccionado = new MutableLiveData<>();


    public NoticeViewModel(@NonNull Application application) {
        super(application);

        rellenarListaElementos();
    }

    public void rellenarListaElementos(){
        List<Notice> notices = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Notice notice = new Notice(
                    "Fernando Porrino Serrano ",
                    "Oferta prácticas IT sistemas y microinformática",
                    "Mi nombre es Dulce Afonso. Soy la reponsable del Dpt...",
                    "Buenos días,\n" +
                            "\n" +
                            "\n" +
                            "Mi nombre es Dulce Afonso. Soy la responsable del Dpto. de Informática y Comunicaciones de la Unidad de Tecnología Marina, un centro del CSIC que tiene su sede central en Barcelona (www.utm.csic.es). Me pongo en contacto con ustedes porque necesitamos personal para nuestro departamento que se dé de alta en la bolsa de trabajo del CSIC, de modo que puedan acceder a nuestra oferta laboral.\n" +
                            "\n" +
                            "Los candidatos deberían tener el título de Técnico Superior de FP de cualquiera de las especialidades de informática (Administración de Sistemas Informáticos, Desarrollo de Aplicaciones Web, o bien Desarrollo de Aplicaciones Multiplataforma). La carga principal del trabajo se desarrollaría en nuestra sede de Barcelona, con posibilidad de viajes (no obligatorios) para la preparación de los sistemas informáticos implicados en campañas oceanográficas y realización de campañas como técnico informático a bordo de los buques oceanográficos del CSIC.\n",
                    "Monday, 27 May 2019, 11:15",
                    "27 de mar.",
                    "@drawable/user_image",
                    true);
            notices.add(notice);
            insertarChat(notice.getAutor(),notice.getTema(),notice.getImgAutor());
        }
        listaNotices.setValue(notices);
    }

    public void insertarChat(String autor, String tema, String image){
        List<Chat> chats = new ArrayList<>();
        Chat chat = new Chat(autor, tema, image);
        chats.add(chat);

        listaChats.setValue(chats);
    }


    public void establecerElementoSeleccionado(Notice notice){
        noticeSeleccionado.setValue(notice);
    }

    public MutableLiveData<List<Notice>> getListaNotices() {
        return listaNotices;
    }

    public MutableLiveData<Notice> getNoticeSeleccionado() {
        return noticeSeleccionado;
    }



    public void establecerElementoSeleccionado(Chat chat){
        chatSeleccionado.setValue(chat);
    }


    public MutableLiveData<Chat> getChatSeleccionado() {
        return chatSeleccionado;
    }
}
