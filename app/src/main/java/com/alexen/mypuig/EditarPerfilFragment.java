package com.alexen.mypuig;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alexen.mypuig.viewmodel.MoodleViewModel;
import com.bumptech.glide.Glide;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditarPerfilFragment extends Fragment {

    MoodleViewModel moodleViewModel;
    NavController navController;
    Button guardarCambios;
    EditText nombre, apellidos, nacimiento, contacto, localidad;
    ImageView imageAccount, editButton;
    String uri = "";
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
        editButton = view.findViewById(R.id.imageButtonEdit);

        imageAccount = view.findViewById(R.id.imageViewPerfilEdit);
        nombre = view.findViewById(R.id.editTextNamePerfil);
        apellidos = view.findViewById(R.id.editTextApellidosPerfil);
        nacimiento = view.findViewById(R.id.editTextNacimientoPerfil);
        contacto = view.findViewById(R.id.editTextContactoPerfil);
        localidad = view.findViewById(R.id.editTextLocalidadPerfil);
        nombre.setText(moodleViewModel.obtenerNombreUsuario());
        if (moodleViewModel.imageAccount.getValue()!=null){
            Glide.with(requireActivity()).load(Uri.parse(moodleViewModel.imageAccount.getValue())).into(imageAccount);
        }

        guardarCambios.setOnClickListener(v -> {
            moodleViewModel.guardarCambiosPerfil(nombre.getText().toString(),moodleViewModel.imageAccount.getValue());
            navController.navigate(R.id.nav_account);
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
    }
    @SuppressLint("IntentReset")
    public void abrirGaleria(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(Intent.createChooser(intent,"Seleccione la aplicacion"),10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){

            Uri path = data.getData();
            Log.e("wow", String.valueOf(path));

            uri = String.valueOf(path);
            moodleViewModel.imageAccount.postValue(uri);
            Glide.with(requireActivity()).load(path).into(imageAccount);

        }

    }
}
