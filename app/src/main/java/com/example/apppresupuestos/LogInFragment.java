package com.example.apppresupuestos;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {
    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassWord;
    private ProgressDialog progressDialog;
    private RegisterFragment.RegisterAndLogInListener registerAndLogInListener;
    private Button logInButton;
    private RegisterFragment.LogInListener logInListener;
    private String email;
    public LogInFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        registerAndLogInListener = (RegisterFragment.RegisterAndLogInListener) context;
        logInListener = (RegisterFragment.LogInListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = view.findViewById(R.id.edit_text_mail);
        editTextPassWord = view.findViewById(R.id.edit_text_pass);
        progressDialog = new ProgressDialog(getContext());
        logInButton = view.findViewById(R.id.btn_ingresar);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loguearUsuario();
            }
        });
        return view;
    }

    public void loguearUsuario(){
        email = editTextEmail.getText().toString().trim();
        String password = editTextPassWord.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getContext(), "Ingrese un Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getContext(), "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Ingresando a la aplicación");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getContext(), "Bienvenido: "+ email, Toast.LENGTH_SHORT).show();
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
