package com.alexen.mypuig;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alexen.mypuig.model.Chat;
import com.alexen.mypuig.model.Notice;
import com.alexen.mypuig.viewmodel.ChatViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleChatFragment extends Fragment {

    ChatViewModel chatViewModel;
    NavController navController;
    MensajeAdapter mensajeAdapter;
    private ImageButton sendButton;
    private EditText editTextMensaje;

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


        sendButton = view.findViewById(R.id.imageButtonSend);
        editTextMensaje = view.findViewById(R.id.editTextMensaje);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatViewModel.insertarMensajeChat(editTextMensaje.getText().toString());
                Toast.makeText(getContext(),"HOBBÑUI",Toast.LENGTH_SHORT).show();
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
            holder.mensajeTextView.setText(chat.getMensajePropio());
            holder.temaTextView.setText("aslnenasofe");
            holder.fechaTextView.setText("vdrnñsnñrv");

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

            public ChatViewHolder(@NonNull View itemView) {
                super(itemView);
                mensajeTextView = itemView.findViewById(R.id.textViewMensajePropio);
                temaTextView = itemView.findViewById(R.id.textViewTituloChat);
                fechaTextView = itemView.findViewById(R.id.textViewFechaChat);

            }
        }
    }
}

