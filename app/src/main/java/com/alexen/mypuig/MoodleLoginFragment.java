package com.alexen.mypuig;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alexen.mypuig.api.Connection;
import com.alexen.mypuig.model.User;
import com.alexen.mypuig.viewmodel.MoodleViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoodleLoginFragment extends Fragment {

    EditText usuarioEditText, contraseñaEditText;
    Button siguienteButton;
    NavController navController;
    MoodleViewModel moodleViewModel;
    FirebaseFirestore db;
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
        db = FirebaseFirestore.getInstance();
        moodleViewModel = ViewModelProviders.of(requireActivity()).get(MoodleViewModel.class);
        navController = Navigation.findNavController(view);

        usuarioEditText= view.findViewById(R.id.editTextUsuarioMoodle);
        contraseñaEditText = view.findViewById(R.id.editTextContraseñaMoodle);

        siguienteButton = view.findViewById(R.id.buttonSiguienteMoodle);
        moodleViewModel.initialLogin();
        siguienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moodleViewModel.login(usuarioEditText.getText().toString(), contraseñaEditText.getText().toString());
            }
        });
        moodleViewModel.estadoLogin.observe(getViewLifecycleOwner(), new Observer<MoodleViewModel.EstadoLogin>() {
            @Override
            public void onChanged(MoodleViewModel.EstadoLogin estadoLogin) {
                switch (estadoLogin) {
                    case LOGINOK:
                        subirDatos();
                        navController.navigate(R.id.nav_home);
                        break;
                    case LOGINFAILED:
                        Toast.makeText(requireContext(),"Login Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void subirDatos(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Create a new user with a first and last name
        User user = new User(currentUser.getUid(),moodleViewModel.token.getValue());

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

}
