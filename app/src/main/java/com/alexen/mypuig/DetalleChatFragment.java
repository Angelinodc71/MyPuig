package com.alexen.mypuig;


import android.annotation.SuppressLint;
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

import android.os.Environment;
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
import com.alexen.mypuig.model.Mensaje;
import com.alexen.mypuig.model.Notice;
import com.alexen.mypuig.viewmodel.ChatViewModel;
import com.alexen.mypuig.viewmodel.NoticeViewModel;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleChatFragment extends Fragment {

    NoticeViewModel noticeViewModel;

    ChatViewModel chatViewModel;
    NavController navController;
    MensajeAdapter mensajeAdapter;

    EditText editTextMensaje;
    TextView fechaTextView, temaTextView;
    ImageView imageViewAccount;
    public static final int cameraData = 0;

    private ImageButton sendButton, cameraButton, galleryButton;

    public MutableLiveData<TipoMSG> estadoDelMSG = new MutableLiveData<>();

    public enum TipoMSG {
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
        editTextMensaje = view.findViewById(R.id.editTextMensaje);
        sendButton = view.findViewById(R.id.imageButtonSend);

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamera();
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estadoDelMSG.setValue(TipoMSG.TEXT);
                chatViewModel.insertarMensaje(editTextMensaje.getText().toString());
            }
        });

        RecyclerView elementosRecyclerView = view.findViewById(R.id.lista_mensajes);
        elementosRecyclerView.addItemDecoration(new DividerItemDecoration(elementosRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        mensajeAdapter = new MensajeAdapter();
        elementosRecyclerView.setAdapter(mensajeAdapter);

        noticeViewModel = ViewModelProviders.of(requireActivity()).get(NoticeViewModel.class);

        fechaTextView = view.findViewById(R.id.textViewFechaChat);
        temaTextView = view.findViewById(R.id.textViewTituloChat);
        imageViewAccount = view.findViewById(R.id.imageViewAccount);


        chatViewModel.getListaMensajes().observe(getViewLifecycleOwner(), new Observer<List<Mensaje>>() {
            @Override
            public void onChanged(List<Mensaje> msg) {
                mensajeAdapter.establecerListaMensajes(msg);
            }
        });

        noticeViewModel.getNoticeSeleccionado().observe(getViewLifecycleOwner(), new Observer<Notice>() {
            @Override
            public void onChanged(Notice notice) {

                if(notice == null) return;

                fechaTextView.setText(notice.getFechaCorta());
                temaTextView.setText(notice.getTema());
                Glide.with(requireActivity()).load(R.drawable.user_image).into(imageViewAccount);
            }
        });

    }
    class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder>{

        List<Mensaje> mensajes;

        @NonNull
        @Override
        public MensajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MensajeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_mensaje, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MensajeViewHolder holder, final int position) {

            final Mensaje mensaje = mensajes.get(position);
            holder.mensajeTextView.setVisibility(View.GONE);
            holder.videoViewPreview.setVisibility(View.GONE);
            holder.imageViewPreview.setVisibility(View.GONE);

//            holder.nombreTextView.setText(chat.getMensajeAjeno());

            Log.e("alv",mensaje.getMensaje());
            if (estadoDelMSG.getValue()== TipoMSG.IMAGE){
                holder.imageViewPreview.setVisibility(View.VISIBLE);
                holder.imageViewPreview.setImageURI(Uri.parse(mensaje.getMensaje()));
                Toast.makeText(getContext(),"Imagen enviado",Toast.LENGTH_SHORT).show();
            }else if (estadoDelMSG.getValue()== TipoMSG.VIDEO){
                holder.videoViewPreview.setVisibility(View.VISIBLE);
                holder.videoViewPreview.setVideoURI(Uri.parse(mensaje.getMensaje()));
                Toast.makeText(getContext(),"Video enviado",Toast.LENGTH_SHORT).show();
            }else if (estadoDelMSG.getValue()== TipoMSG.TEXT){
                holder.mensajeTextView.setVisibility(View.VISIBLE);
                holder.mensajeTextView.setText(mensaje.getMensaje());
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
            return mensajes == null ? 0 : mensajes.size();
        }

        public void establecerListaMensajes(List<Mensaje> mensajes){
            this.mensajes = mensajes;
            notifyDataSetChanged();
        }

        class MensajeViewHolder extends RecyclerView.ViewHolder {
            TextView mensajeTextView;
            ImageView imageViewPreview;
            VideoView videoViewPreview;
            public MensajeViewHolder(@NonNull View itemView) {
                super(itemView);
                mensajeTextView = itemView.findViewById(R.id.textViewMensajePropio);
                imageViewPreview = itemView.findViewById(R.id.imageViewPreviewMSG);
                videoViewPreview = itemView.findViewById(R.id.videoViewPreviewMSG);
            }
        }
    }



    @SuppressLint("IntentReset")
    public void abrirGaleria(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        estadoDelMSG.setValue(TipoMSG.IMAGE);

        startActivityForResult(Intent.createChooser(intent,"Seleccione la aplicacion"),10);
    }
    @SuppressLint("IntentReset")
    public void abrirCamera(){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, cameraData);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){

            Uri path = data.getData();
            Log.e("wow", String.valueOf(path));
            chatViewModel.insertarMensaje(String.valueOf(path));
        }

    }
}

