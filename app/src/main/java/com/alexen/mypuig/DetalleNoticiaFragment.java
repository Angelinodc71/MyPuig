package com.alexen.mypuig;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexen.mypuig.api.Discussion;
import com.alexen.mypuig.viewmodel.MoodleViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleNoticiaFragment extends Fragment {

    MoodleViewModel moodleViewModel;

    private boolean fav;
    static Discussion d;
    TextView userfullnameTextView, nameTextView, messageTextView, createdTextView;

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




        moodleViewModel = ViewModelProviders.of(requireActivity()).get(MoodleViewModel.class);

        userfullnameTextView = view.findViewById(R.id.textViewAutorDetalle);
        nameTextView = view.findViewById(R.id.textViewTemaDetalle);
        messageTextView = view.findViewById(R.id.textViewMensajeDetalle);
        createdTextView = view.findViewById(R.id.TextViewFechaDetalle);

        moodleViewModel.getNoticeSeleccionado().observe(getViewLifecycleOwner(), discussion -> {

            if(discussion == null) return;

            userfullnameTextView.setText(discussion.userfullname);
            nameTextView.setText(discussion.name);
            messageTextView.setText(Html.fromHtml(discussion.message));
            createdTextView.setText(TimeConverter.converter(discussion.created));
            d = discussion;
            Log.e("ABC",d.id);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.opcion_compartir:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                Log.e("ABC", String.valueOf(Html.fromHtml(d.message)));
                sendIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(d.message));
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;

            case R.id.opcion_borrar_favorito:
                item.setVisible(false);
                if (d!=null){
                    moodleViewModel.removeDiscussionFav(d.id);
                }
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



        // You can look up you menu item here and store it in a global variable by
        // 'mMenuItem = menu.findItem(R.id.my_menu_item);'
    }
}
