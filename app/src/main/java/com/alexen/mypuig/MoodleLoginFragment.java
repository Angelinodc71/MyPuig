package com.alexen.mypuig;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.alexen.mypuig.api.Connection;
import com.alexen.mypuig.viewmodel.NoticeViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoodleLoginFragment extends Fragment {

    EditText usuarioEditText, contraseñaEditText;
    Button siguienteButton;
    NavController navController;
    NoticeViewModel noticeViewModel;
    public MoodleLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_moodle_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noticeViewModel = ViewModelProviders.of(requireActivity()).get(NoticeViewModel.class);
        navController = Navigation.findNavController(view);

        usuarioEditText= view.findViewById(R.id.editTextUsuarioMoodle);
        contraseñaEditText = view.findViewById(R.id.editTextContraseñaMoodle);

        siguienteButton = view.findViewById(R.id.buttonSiguienteMoodle);

        siguienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection.login(usuarioEditText.getText().toString(), contraseñaEditText.getText().toString());
                noticeViewModel.rellenarListaNoticias();
                navController.navigate(R.id.nav_home);
            }
        });
    }
}
