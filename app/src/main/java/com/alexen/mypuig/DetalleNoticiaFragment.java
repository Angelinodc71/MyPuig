package com.alexen.mypuig;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alexen.mypuig.model.Notice;
import com.alexen.mypuig.viewmodel.NoticeViewModel;
import com.intrusoft.squint.DiagonalView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleNoticiaFragment extends Fragment {

    NoticeViewModel noticeViewModel;

    private boolean noticeFav;
    static Notice noticeTmp;
    TextView autorTextView, temaTextView, mensajeTextView, fechaTextView;
    CheckBox favCheckBox;
    public DetalleNoticiaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_noticia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);


        DiagonalView diagonalView = view.findViewById(R.id.diagonal);



        noticeViewModel = ViewModelProviders.of(requireActivity()).get(NoticeViewModel.class);

        autorTextView = view.findViewById(R.id.textViewAutorDetalle);
        temaTextView = view.findViewById(R.id.textViewTemaDetalle);
        mensajeTextView = view.findViewById(R.id.textViewMensajeDetalle);
        fechaTextView = view.findViewById(R.id.TextViewFechaDetalle);

        noticeViewModel.getNoticeSeleccionado().observe(getViewLifecycleOwner(), new Observer<Notice>() {
            @Override
            public void onChanged(Notice notice) {

                if(notice == null) return;

                autorTextView.setText(notice.getAutor());
                temaTextView.setText(notice.getTema());
                mensajeTextView.setText(notice.getMsg());
                fechaTextView.setText(notice.getFecha());
                noticeFav = notice.getFavNotice();
                noticeTmp = notice;
                noticeTmp.setFavNotice(noticeFav);
                if (notice.getFavNotice()!=noticeTmp.getFavNotice()){
                    notice.setFavNotice(notice.getFavNotice());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.opcion_compartir:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mensajeTextView.getText().toString());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;

            case R.id.opcion_borrar_favorito:
                item.setVisible(false);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detalle_noticia, menu);

        MenuItem itemBorrarFav = menu.findItem(R.id.opcion_borrar_favorito);

        if (!noticeFav){
            itemBorrarFav.setVisible(false);
        }else {
            itemBorrarFav.setVisible(true);
        }
        // You can look up you menu item here and store it in a global variable by
        // 'mMenuItem = menu.findItem(R.id.my_menu_item);'
    }
}
