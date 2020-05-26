package com.alexen.mypuig;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.alexen.mypuig.api.Discussion;
import com.alexen.mypuig.viewmodel.MoodleViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritosFragment extends Fragment {
    MoodleViewModel moodleViewModel;
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

        moodleViewModel = ViewModelProviders.of(requireActivity()).get(MoodleViewModel.class);
        navController = Navigation.findNavController(view);

        RecyclerView elementosRecyclerView = view.findViewById(R.id.item_list_favoritos);


        favoritosAdapter = new FavoritosAdapter();
        elementosRecyclerView.setAdapter(favoritosAdapter);

        moodleViewModel.userFavs.observe(getViewLifecycleOwner(), user -> {
            Log.e("ABC","63");
            favoritosAdapter.establecerFavoritos(user.favs);

        });

        moodleViewModel.getNoticiasFav.observe(getViewLifecycleOwner(), new Observer<List<Discussion>>() {
            @Override
            public void onChanged(List<Discussion> discussions) {
                favoritosAdapter.establecerListaNoticias(discussions);
            }
        });
    }

    class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.FavoritosViewHolder>{

        List<Discussion> discussions;

        HashMap<String, Boolean> favs = new HashMap<>();

        @NonNull
        @Override
        public FavoritosAdapter.FavoritosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FavoritosAdapter.FavoritosViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_favoritos, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final FavoritosViewHolder holder, final int position) {

            final Discussion discussion = discussions.get(position);

            holder.autorTextView.setText(discussion.name);
            holder.temaTextView.setText(discussion.name);
            holder.mensajeTextView.setText(Html.fromHtml(discussion.message));
            holder.fechaTextView.setText(TimeConverter.converter(discussion.timemodified));

            holder.favCheckBox.setChecked(favs!=null && favs.containsKey(discussion.id) && favs.get(discussion.id).booleanValue()==true);

            holder.favCheckBox.setOnClickListener(v -> {
                if (holder.favCheckBox.isChecked()){
                    moodleViewModel.addDiscussionFav(discussion.id);
                    Toast.makeText(getContext(),"Noticia Marcada",Toast.LENGTH_SHORT).show();
                } else{
                    moodleViewModel.removeDiscussionFav(discussion.id);
                    Toast.makeText(getContext(),"Noticia Desmarcada",Toast.LENGTH_SHORT).show();
                }
            });

            holder.chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moodleViewModel.establecerElementoSeleccionado(discussion);
                    navController.navigate(R.id.detalleChatFragment);
                }
            });
            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moodleViewModel.establecerElementoSeleccionado(discussion);
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    Log.e("ABC", String.valueOf(Html.fromHtml(discussion.message)));
                    sendIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(discussion.message));
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moodleViewModel.establecerElementoSeleccionado(discussion);
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


        public void establecerFavoritos(HashMap<String, Boolean> favs) {
            this.favs = favs;
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
