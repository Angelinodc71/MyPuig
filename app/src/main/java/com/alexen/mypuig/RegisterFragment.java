package com.alexen.mypuig;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
    Button buttonRegistrar;
    EditText emailEdit;
    EditText passwordEdit;
    EditText usernameEdit;

    String email;
    String username;
    String password;
    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        // Initialize Firebase Auth
        usernameEdit =view.findViewById(R.id.editTextUsuarioRegister);
        emailEdit = view.findViewById(R.id.editTextEmailRegister);
        passwordEdit = view.findViewById(R.id.editTextContraseñaRegister);
        buttonRegistrar = view.findViewById(R.id.buttonRegistrarse);


        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEdit.getText().toString();
                password = passwordEdit.getText().toString();
                username = usernameEdit.getText().toString();
                createAccount();
            }
        });
    }

    void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) Navigation.findNavController(getView()).navigate(R.id.nav_home);

    }

    public  void createAccount(){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

}
