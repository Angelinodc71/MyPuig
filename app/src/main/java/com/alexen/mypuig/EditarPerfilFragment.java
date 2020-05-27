package com.alexen.mypuig;


import android.media.midi.MidiOutputPort;
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
import android.widget.TextView;

import com.alexen.mypuig.viewmodel.MoodleViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditarPerfilFragment extends Fragment {

    MoodleViewModel moodleViewModel;
    NavController navController;
    Button guardarCambios;
    EditText nombre, apellidos, nacimiento, contacto, localidad;

    public EditarPerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_perfil, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moodleViewModel = ViewModelProviders.of(requireActivity()).get(MoodleViewModel.class);
        navController = Navigation.findNavController(view);

        guardarCambios = view.findViewById(R.id.buttonGuardarCambiosPerfil);

        nombre = view.findViewById(R.id.editTextNamePerfil);
        apellidos = view.findViewById(R.id.editTextApellidosPerfil);
        nacimiento = view.findViewById(R.id.editTextNacimientoPerfil);
        contacto = view.findViewById(R.id.editTextContactoPerfil);
        localidad = view.findViewById(R.id.editTextLocalidadPerfil);
        nombre.setText(moodleViewModel.obtenerNombreUsuario());

        guardarCambios.setOnClickListener(v -> {
            moodleViewModel.guardarNombreUsuario(nombre.getText().toString());
            navController.navigate(R.id.nav_account);
        });
    }
}
