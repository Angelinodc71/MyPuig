package com.alexen.mypuig.ui.home;

import android.os.Bundle;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.alexen.mypuig.R;
import com.alexen.mypuig.model.Notice;
import com.alexen.mypuig.viewmodel.NoticeViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    NoticeViewModel noticeViewModel;
    NavController navController;
    NoticiasAdapter noticiasAdapter;
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

        RecyclerView elementosRecyclerView = view.findViewById(R.id.item_list);

        imageButton = view.findViewById(R.id.buttonFilter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.filtrarFragment);
            }
        });

        noticiasAdapter = new NoticiasAdapter();
        elementosRecyclerView.setAdapter(noticiasAdapter);

        noticeViewModel.getListaNotices().observe(getViewLifecycleOwner(), new Observer<List<Notice>>() {
            @Override
            public void onChanged(List<Notice> notices) {
                noticiasAdapter.establecerListaNoticias(notices);
            }
        });



    }

    class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.NoticiasViewHolder>{

        List<Notice> notices;

        @NonNull
        @Override
        public NoticiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NoticiasViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_notice, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final NoticiasViewHolder holder, final int position) {

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

        class NoticiasViewHolder extends RecyclerView.ViewHolder {
            TextView autorTextView, temaTextView, mensajeTextView, fechaTextView;
            CheckBox favCheckBox;

            NoticiasViewHolder(@NonNull View itemView) {
                super(itemView);
                autorTextView = itemView.findViewById(R.id.textViewAutor);
                temaTextView = itemView.findViewById(R.id.textViewTema);
                mensajeTextView = itemView.findViewById(R.id.textViewMensajeCorto);
                fechaTextView = itemView.findViewById(R.id.textViewFechaCorta);
                favCheckBox = itemView.findViewById(R.id.checkBoxFav);
            }
        }
    }
}