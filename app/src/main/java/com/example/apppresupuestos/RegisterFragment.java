package com.example.apppresupuestos;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassWord;
    private Button registerButton;
    private ProgressDialog progressDialog;
    private RegisterAndLogInListener registerAndLogInListener;
    private Button logInButton;
    private LogInListener logInListener;
    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        registerAndLogInListener = (RegisterAndLogInListener) context;
        logInListener = (LogInListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = view.findViewById(R.id.edit_text_mail);
        editTextPassWord = view.findViewById(R.id.edit_text_pass);
        registerButton = view.findViewById(R.id.btn_registrar);
        progressDialog = new ProgressDialog(getContext());
        logInButton = view.findViewById(R.id.btn_ingresar);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInListener.logIn();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();
            }
        });

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        registerAndLogInListener.updateUI(currentUser);
        //updateUI(currentUser);
    }



    public interface RegisterAndLogInListener{
        void updateUI(FirebaseUser currentUser);
    }
    public interface LogInListener{
        void logIn();
    }

    public void registrarUsuario(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassWord.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getContext(), "Ingrese un Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getContext(), "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Realizando Registro...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getContext(), "Se ha registrado", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            registerAndLogInListener.updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //si el usuario ya existe
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getContext(), "El usuario ya existe",
                                        Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getContext(), "No se pudo registrar el usuario.",
                                    Toast.LENGTH_SHORT).show();
                            registerAndLogInListener.updateUI(null);
                        }
                        progressDialog.dismiss();
                        // ...
                    }

                });
    }
}
