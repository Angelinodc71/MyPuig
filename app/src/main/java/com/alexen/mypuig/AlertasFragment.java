package com.alexen.mypuig;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import static com.alexen.mypuig.MainActivity.make;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlertasFragment extends Fragment {

    Switch mobileNotification, emailnotification;
    ImageButton deleteAlert, goNotice;
    NavController navController;

    public AlertasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alertas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        mobileNotification = view.findViewById(R.id.switchMobile);
        emailnotification = view.findViewById(R.id.switchEmail);

        deleteAlert = view.findViewById(R.id.imageButtonOptionDeleteAlert);
        goNotice = view.findViewById(R.id.imageButtonViewResults);

        deleteAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"alerta borrada",Toast.LENGTH_LONG).show();
            }
        });

        goNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_home);
            }
        });
        mobileNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobileNotification.isChecked()){
                    make(v,"Recibirás alertas  de anuncios similares en el móvil", Snackbar.LENGTH_LONG);
                }else make(v,"Ya no recibiras alertas  de anuncios similares en el móvil", Snackbar.LENGTH_LONG);

            }
        });

        emailnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailnotification.isChecked()){
                    make(v,"Recibirás alertas  de anuncios similares en el correo", Snackbar.LENGTH_LONG);
                }else make(v,"Ya no recibiras alertas  de anuncios similares en el correo", Snackbar.LENGTH_LONG);

            }
        });
    }
}
