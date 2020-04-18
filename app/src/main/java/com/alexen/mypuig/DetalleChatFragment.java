package com.alexen.mypuig;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.alexen.mypuig.model.Chat;
import com.alexen.mypuig.viewmodel.ChatViewModel;

import java.util.EmptyStackException;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleChatFragment extends Fragment {

    ChatViewModel chatViewModel;
    NavController navController;
    MensajeAdapter mensajeAdapter;
    private ImageButton sendButton, cameraButton, galleryButton;
    private EditText editTextMensaje;

    public MutableLiveData<TipoMSG> estadoDelMSG = new MutableLiveData<>();

    public enum TipoMSG {
        INITIAL,
        IMAGE,
        TEXT,
        VIDEO
    }

    public DetalleChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detalle_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chatViewModel = ViewModelProviders.of(requireActivity()).get(ChatViewModel.class);
        navController = Navigation.findNavController(view);

        galleryButton = view.findViewById(R.id.imageButtonClip);
        cameraButton = view.findViewById(R.id.imageButtonCamera);

        sendButton = view.findViewById(R.id.imageButtonSend);
        editTextMensaje = view.findViewById(R.id.editTextMensaje);

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estadoDelMSG.setValue(TipoMSG.TEXT);
                chatViewModel.insertarMensajeChat(editTextMensaje.getText().toString());
            }
        });

        RecyclerView elementosRecyclerView = view.findViewById(R.id.lista_mensajes);
        elementosRecyclerView.addItemDecoration(new DividerItemDecoration(elementosRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        mensajeAdapter = new MensajeAdapter();
        elementosRecyclerView.setAdapter(mensajeAdapter);

        chatViewModel.getListaMensajes().observe(getViewLifecycleOwner(), new Observer<List<Chat>>() {
            @Override
            public void onChanged(List<Chat> mensajes) {
                mensajeAdapter.establecerListaMensajes(mensajes);
            }
        });
    }
    class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.ChatViewHolder>{

        List<Chat> chats;

        @NonNull
        @Override
        public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_mensaje, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ChatViewHolder holder, final int position) {

            final Chat chat = chats.get(position);

//            holder.nombreTextView.setText(chat.getMensajeAjeno());

            if (estadoDelMSG.getValue()== TipoMSG.IMAGE){
                holder.imageViewPreview.setImageURI(Uri.parse(chat.getMensajePropio()));
                Toast.makeText(getContext(),"Imagen enviado",Toast.LENGTH_SHORT).show();
            }else if (estadoDelMSG.getValue()== TipoMSG.VIDEO){
                holder.videoViewPreview.setVideoURI(Uri.parse(chat.getMensajePropio()));
                Toast.makeText(getContext(),"Video enviado",Toast.LENGTH_SHORT).show();
            }else if (estadoDelMSG.getValue()== TipoMSG.TEXT){
                holder.mensajeTextView.setText(chat.getMensajePropio());
                Toast.makeText(getContext(),"Texto enviado",Toast.LENGTH_SHORT).show();
            }



//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    chatViewModel.establecerElementoSeleccionado(chat);
//                    navController.navigate(R.id.detalleElementoFragment);
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return chats == null ? 0 : chats.size();
        }

        public void establecerListaMensajes(List<Chat> chats){
            this.chats = chats;
            notifyDataSetChanged();
        }

        class ChatViewHolder extends RecyclerView.ViewHolder {
            TextView mensajeTextView, temaTextView, fechaTextView;
            ImageView imageViewPreview;
            VideoView videoViewPreview;
            public ChatViewHolder(@NonNull View itemView) {
                super(itemView);
                mensajeTextView = itemView.findViewById(R.id.textViewMensajePropio);
                temaTextView = itemView.findViewById(R.id.textViewTituloChat);
                fechaTextView = itemView.findViewById(R.id.textViewFechaChat);
                imageViewPreview = itemView.findViewById(R.id.imageViewPreviewMSG);
                videoViewPreview = itemView.findViewById(R.id.videoViewPreviewMSG);
            }
        }
    }



    public void abrirGaleria(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(Intent.createChooser(intent,"Seleccione la aplicacion"),10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            Uri path = data.getData();
            estadoDelMSG.setValue(TipoMSG.IMAGE);
            chatViewModel.insertarMensajeChat(String.valueOf(path));
        }
    }
}

