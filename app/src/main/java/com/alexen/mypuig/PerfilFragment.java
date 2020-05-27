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
import android.widget.TextView;

import com.alexen.mypuig.viewmodel.MoodleViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {
    MoodleViewModel moodleViewModel;
    NavController navController;
    Button editarPerfil, cerrarSesion;
    TextView nombre, apellidos, nacimiento, contacto, localidad;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moodleViewModel = ViewModelProviders.of(requireActivity()).get(MoodleViewModel.class);
        navController = Navigation.findNavController(view);

        editarPerfil = view.findViewById(R.id.buttonAdministracionCuentas);
        cerrarSesion = view.findViewById(R.id.buttonCerrarSesion);

        nombre = view.findViewById(R.id.textViewNamePerfil);
        apellidos = view.findViewById(R.id.textViewApellidosPerfil);
        nacimiento = view.findViewById(R.id.textViewNacimientoPerfil);
        contacto = view.findViewById(R.id.textViewContactoPerfil);
        localidad = view.findViewById(R.id.textViewLocalidadPerfil);

        nombre.setText(moodleViewModel.obtenerNombreUsuario());
        moodleViewModel.guardarNombreUsuario(nombre.getText().toString());
        editarPerfil.setOnClickListener(v -> navController.navigate(R.id.editarPerfilFragment));
        cerrarSesion.setOnClickListener(v -> {
            moodleViewModel.cerrarSesion();
            navController.navigate(R.id.loginSelectionFragment);
        });
    }
}
