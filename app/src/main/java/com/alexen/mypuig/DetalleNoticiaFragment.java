package com.alexen.mypuig;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alexen.mypuig.api.Discussion;
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
    TextView userfullnameTextView, nameTextView, messageTextView, createdTextView;
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

        userfullnameTextView = view.findViewById(R.id.textViewAutorDetalle);
        nameTextView = view.findViewById(R.id.textViewTemaDetalle);
        messageTextView = view.findViewById(R.id.textViewMensajeDetalle);
        createdTextView = view.findViewById(R.id.TextViewFechaDetalle);

        noticeViewModel.getNoticeSeleccionado().observe(getViewLifecycleOwner(), new Observer<Discussion>() {
            @Override
            public void onChanged(Discussion discussion) {

                if(discussion == null) return;

                userfullnameTextView.setText(discussion.userfullname);
                nameTextView.setText(discussion.name);
                messageTextView.setText(Html.fromHtml(discussion.message));
                createdTextView.setText(TimeConverter.converter(discussion.created));
//                noticeFav = discussion.getFavNotice();
//                noticeTmp = discussion;
//                noticeTmp.setFavNotice(noticeFav);
//                if (discussion.getFavNotice()!=noticeTmp.getFavNotice()){
//                    discussion.setFavNotice(discussion.getFavNotice());
//                }
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
                sendIntent.putExtra(Intent.EXTRA_TEXT, messageTextView.getText().toString());
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
