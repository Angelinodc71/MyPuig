package com.alexen.mypuig;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alexen.mypuig.api.Connection;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoodleLoginFragment extends Fragment {

    EditText usuarioEditText, contraseñaEditText;

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
        usuarioEditText= view.findViewById(R.id.editTextUsuarioMoodle);
        contraseñaEditText = view.findViewById(R.id.editTextContraseñaMoodle);

        Connection.login(usuarioEditText.getText().toString(), contraseñaEditText.getText().toString());
    }
}
