package com.alexen.mypuig;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexen.mypuig.model.Notice;
import com.alexen.mypuig.ui.home.HomeFragment;
import com.alexen.mypuig.viewmodel.NoticeViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritosFragment extends Fragment {
    NoticeViewModel noticeViewModel;
    NavController navController;
    FavoritosAdapter favoritosAdapter;



    public FavoritosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favoritos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noticeViewModel = ViewModelProviders.of(requireActivity()).get(NoticeViewModel.class);
        navController = Navigation.findNavController(view);

        RecyclerView elementosRecyclerView = view.findViewById(R.id.item_list_favoritos);


        favoritosAdapter = new FavoritosAdapter();
        elementosRecyclerView.setAdapter(favoritosAdapter);

        noticeViewModel.getListaNotices().observe(getViewLifecycleOwner(), new Observer<List<Notice>>() {
            @Override
            public void onChanged(List<Notice> notices) {
                favoritosAdapter.establecerListaNoticias(notices);
            }
        });
    }

    class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.FavoritosViewHolder>{

        List<Notice> notices;

        @NonNull
        @Override
        public FavoritosAdapter.FavoritosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FavoritosAdapter.FavoritosViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_favoritos, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final FavoritosAdapter.FavoritosViewHolder holder, final int position) {

            final Notice notice = notices.get(position);

            holder.autorTextView.setText(notice.getAutor());
            holder.temaTextView.setText(notice.getTema());
            holder.mensajeTextView.setText(notice.getMsgCorto());
            holder.fechaTextView.setText(notice.getFechaCorta());
            holder.favCheckBox.setChecked(notice.getFavNotice());
            holder.favCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    holder.favCheckBox.setChecked(isChecked);
                }
            });

            holder.chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noticeViewModel.establecerElementoSeleccionado(notice);
                    navController.navigate(R.id.detalleChatFragment);
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    noticeViewModel.establecerElementoSeleccionado(notice);
                    navController.navigate(R.id.detalleNoticiaFragment);
                }
            });
        }

        @Override
        public int getItemCount() {
            return notices == null ? 0 : notices.size();
        }

        public void establecerListaNoticias(List<Notice> notices){
            this.notices = notices;
            notifyDataSetChanged();
        }

        class FavoritosViewHolder extends RecyclerView.ViewHolder {
            TextView autorTextView, temaTextView, mensajeTextView, fechaTextView;
            CheckBox favCheckBox;
            Button chat, share;
            FavoritosViewHolder(@NonNull View itemView) {
                super(itemView);
                autorTextView = itemView.findViewById(R.id.textViewAutorFav);
                temaTextView = itemView.findViewById(R.id.textViewTemaFav);
                mensajeTextView = itemView.findViewById(R.id.textViewMensajeCortoFav);
                fechaTextView = itemView.findViewById(R.id.textViewFechaCortaFav);
                favCheckBox = itemView.findViewById(R.id.checkBoxFav2);
                chat = itemView.findViewById(R.id.buttonChat);
                share = itemView.findViewById(R.id.buttonCompartir);


            }
        }
    }

}
