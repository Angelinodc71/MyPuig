package com.alexen.mypuig;


import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.alexen.mypuig.model.Notice;
import com.alexen.mypuig.viewmodel.NoticeViewModel;
import com.bumptech.glide.Glide;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaChatsFragment extends Fragment {

    NoticeViewModel noticeViewModel;
    NavController navController;
    ChatAdapter chatAdapter;
    public ListaChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noticeViewModel = ViewModelProviders.of(requireActivity()).get(NoticeViewModel.class);
        navController = Navigation.findNavController(view);


        RecyclerView elementosRecyclerView = view.findViewById(R.id.item_list_Chats);
        elementosRecyclerView.addItemDecoration(new DividerItemDecoration(elementosRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        chatAdapter = new ChatAdapter();
        elementosRecyclerView.setAdapter(chatAdapter);

        noticeViewModel.getListaNotices().observe(getViewLifecycleOwner(), new Observer<List<Notice>>() {
            @Override
            public void onChanged(List<Notice> chats) {
                chatAdapter.establecerListaMensajes(chats);
            }
        });
    }

    class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>{

        List<Notice> chats;

        @NonNull
        @Override
        public ChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ChatAdapter.ChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_chat, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ChatViewHolder holder, final int position) {

            final Notice notice = chats.get(position);

            holder.autorTextView.setText(notice.getAutor());
            holder.temaTextView.setText(notice.getTema());

            Glide.with(requireActivity()).load(R.drawable.user_image).into(holder.imageViewAutor);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    noticeViewModel.establecerElementoSeleccionado(notice);
                    navController.navigate(R.id.detalleChatFragment);
                }
            });

        }

        @Override
        public int getItemCount() {
            return chats == null ? 0 : chats.size();
        }

        public void establecerListaMensajes(List<Notice> chats){
            this.chats = chats;
            notifyDataSetChanged();
        }

        class ChatViewHolder extends RecyclerView.ViewHolder {
            TextView autorTextView, temaTextView;
            ImageView imageViewAutor;
            public ChatViewHolder(@NonNull View itemView) {
                super(itemView);
                autorTextView = itemView.findViewById(R.id.textViewAutorChatViewHolder);
                temaTextView = itemView.findViewById(R.id.textViewTemaChatViewHolder);
                imageViewAutor = itemView.findViewById(R.id.imageViewAccountChatViewHolder);
            }
        }
    }
}
