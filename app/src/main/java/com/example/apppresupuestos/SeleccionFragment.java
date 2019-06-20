package com.example.apppresupuestos;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class SeleccionFragment extends Fragment {
    private Button cerramiento;
    private Button puntoRecto;
    private Button brazoInvisible;
    private Button toldoFijo;
    private Button subirImagenes;
    private DatabaseReference myDatabasePuntoRecto;
    private DatabaseReference myDatabaseBrazoInvisible;
    private DatabaseReference myDatabaseCerramiento;
    private DatabaseReference myDatabaseToldoFijo;
    private MyDatabaseReferenceListener myDatabaseReferenceListener;
    private SubirImagenes subirImagenesListener;
    private DatabaseReferenceObject databaseReference;


    public SeleccionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myDatabaseReferenceListener = (MyDatabaseReferenceListener) context;
        subirImagenesListener = (SubirImagenes) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seleccion, container, false);
        subirImagenes = view.findViewById(R.id.subir_imagenes);
        cerramiento = view.findViewById(R.id.toldo_cerramiento);
        puntoRecto = view.findViewById(R.id.toldo_punto_recto);
        brazoInvisible = view.findViewById(R.id.toldo_brazo_invisible);
        toldoFijo = view.findViewById(R.id.toldo_fijo);
        myDatabasePuntoRecto = FirebaseDatabase.getInstance().getReference("Uploads").child("ToldoPuntoRecto");
        myDatabaseCerramiento = FirebaseDatabase.getInstance().getReference("Uploads").child("ToldoCerramiento");
        myDatabaseBrazoInvisible = FirebaseDatabase.getInstance().getReference("Uploads").child("ToldoBrazoInvisible");
        myDatabaseToldoFijo = FirebaseDatabase.getInstance().getReference("Uploads").child("ToldoBFijo");
        cerramiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReferenceObject databaseReference = new DatabaseReferenceObject(myDatabaseCerramiento);
                //databaseReference.setDatabaseReference(myDatabaseCerramiento);
                myDatabaseReferenceListener.myDatabaseReferenceData(databaseReference);
            }
        });
        puntoRecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //databaseReference.setDatabaseReference(myDatabasePuntoRecto);
                DatabaseReferenceObject databaseReference = new DatabaseReferenceObject(myDatabasePuntoRecto);
                myDatabaseReferenceListener.myDatabaseReferenceData(databaseReference);
            }
        });
        toldoFijo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // databaseReference.setDatabaseReference(myDatabaseToldoFijo);
                DatabaseReferenceObject databaseReference = new DatabaseReferenceObject(myDatabaseToldoFijo);
               myDatabaseReferenceListener.myDatabaseReferenceData(databaseReference);
            }
        });
        brazoInvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //databaseReference.setDatabaseReference(myDatabaseBrazoInvisible);
               DatabaseReferenceObject databaseReference = new DatabaseReferenceObject(myDatabaseBrazoInvisible);
               myDatabaseReferenceListener.myDatabaseReferenceData(databaseReference);
            }
        });
        subirImagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subirImagenesListener.subirImagenesListener();
            }
        });
        return view;
    }


    public interface MyDatabaseReferenceListener{
        void myDatabaseReferenceData(DatabaseReferenceObject myDatabaseReference);
    }

    public interface SubirImagenes{
        void subirImagenesListener();

    }
}
