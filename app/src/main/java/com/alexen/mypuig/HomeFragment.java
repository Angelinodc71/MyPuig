package com.alexen.mypuig;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.alexen.mypuig.api.Discussion;
import com.alexen.mypuig.viewmodel.NoticeViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    NoticeViewModel noticeViewModel;
    NavController navController;
    FavoritosAdapter noticiasAdapter;
    ImageButton imageButton;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noticeViewModel = ViewModelProviders.of(requireActivity()).get(NoticeViewModel.class);
        navController = Navigation.findNavController(view);

        RecyclerView elementosRecyclerView = view.findViewById(R.id.item_list_anuncios);

        imageButton = view.findViewById(R.id.buttonFilter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.filtrarFragment);
            }
        });

        noticiasAdapter = new FavoritosAdapter();
        elementosRecyclerView.setAdapter(noticiasAdapter);

        noticeViewModel.getListaNotices().observe(getViewLifecycleOwner(), new Observer<List<Discussion>>() {
            @Override
            public void onChanged(List<Discussion> discussions) {
                noticiasAdapter.establecerListaNoticias(discussions);
            }
        });



    }

    class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.NoticiasViewHolder>{

        List<Discussion> discussions;

        @NonNull
        @Override
        public NoticiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NoticiasViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_notice, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final NoticiasViewHolder holder, final int position) {

            final Discussion discussion = this.discussions.get(position);

//            holder.userfullnameTextView.setText(discussions.getAutor());
//            holder.nameTextView.setText(discussions.getTema());
//            holder.messageTextView.setText(discussions.getMsgCorto());
//            holder.timemodifiedTextView.setText(discussions.getFechaCorta());
//            holder.favCheckBox.setChecked(discussions.getFavNotice());

            holder.userfullnameTextView.setText(discussion.userfullname);
            holder.nameTextView.setText(discussion.name);
            holder.mensajeTextView.setText(Html.fromHtml(discussion.message));
            holder.timemodifiedTextView.setText(TimeConverter.converter(discussion.timemodified));
            holder.favCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    holder.favCheckBox.setChecked(isChecked);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    noticeViewModel.establecerElementoSeleccionado(discussion);
                    navController.navigate(R.id.detalleNoticiaFragment);
                }
            });
        }

        @Override
        public int getItemCount() {
            return discussions == null ? 0 : discussions.size();
        }

        public void establecerListaNoticias(List<Discussion> discussions){
            this.discussions = discussions;
            notifyDataSetChanged();
        }

        class NoticiasViewHolder extends RecyclerView.ViewHolder {
            TextView userfullnameTextView, nameTextView, mensajeTextView, timemodifiedTextView;
            CheckBox favCheckBox;

            NoticiasViewHolder(@NonNull View itemView) {
                super(itemView);
                userfullnameTextView = itemView.findViewById(R.id.textViewAutor);
                nameTextView = itemView.findViewById(R.id.textViewTema);
                mensajeTextView = itemView.findViewById(R.id.textViewMensajeCorto);
                timemodifiedTextView = itemView.findViewById(R.id.textViewFechaCorta);
                favCheckBox = itemView.findViewById(R.id.checkBoxFav);
            }
        }
    }
}