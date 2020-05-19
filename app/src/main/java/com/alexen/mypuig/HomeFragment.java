package com.alexen.mypuig;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SearchView;
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
import com.alexen.mypuig.model.User;
import com.alexen.mypuig.viewmodel.MoodleViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener {

    MoodleViewModel moodleViewModel;
    NavController navController;
    FavoritosAdapter noticiasAdapter;
    ImageButton imageButton;
    SearchView searchView;
    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        moodleViewModel = ViewModelProviders.of(requireActivity()).get(MoodleViewModel.class);
        navController = Navigation.findNavController(view);

        searchView = view.findViewById(R.id.searchView);
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
        moodleViewModel.getNoticias.observe(getViewLifecycleOwner(), new Observer<List<Discussion>>() {
            @Override
            public void onChanged(List<Discussion> discussions) {
                noticiasAdapter.establecerListaNoticias(discussions);
            }
        });

        searchView.setOnQueryTextListener(this);

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        List<Discussion> newList = new ArrayList<>();

        if (noticiasAdapter.discussionsOriginal!=null){
            for (Discussion discussion : noticiasAdapter.discussionsOriginal){
                if (discussion.name.toLowerCase().contains(userInput)){
                    newList.add(discussion);
                }
            }
        }
        noticiasAdapter.updateList(newList);
        return true;
    }


    class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.NoticiasViewHolder>{

        List<Discussion> discussions;
        List<Discussion> discussionsOriginal;


        @NonNull
        @Override
        public NoticiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NoticiasViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_notice, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final NoticiasViewHolder holder, final int position) {

            final Discussion discussion = this.discussions.get(position);

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
            this.discussionsOriginal = discussions;
            notifyDataSetChanged();
        }

        public void updateList(List<Discussion> newList){
        discussions = new ArrayList<>();
        discussions.addAll(newList);
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