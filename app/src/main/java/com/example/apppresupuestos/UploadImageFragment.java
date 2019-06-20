package com.example.apppresupuestos;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class UploadImageFragment extends Fragment {
    private Button myBtnSelectImage;
    private static StorageReference myStoragePuntoRecto;
    private static StorageReference myStorageBrazoInvisible;
    private static StorageReference myStorageCerramiento;
    private static StorageReference myStorageToldoFijo;
    private DatabaseReference myDatabase;
    private DatabaseReference myDatabasePuntoRecto;
    private DatabaseReference myDatabaseBrazoInvisible;
    private DatabaseReference myDatabaseCerramiento;
    private DatabaseReference myDatabaseToldoFijo;
    private Button myBtnUploadToldoFijo;
    private Button myBtnUploadCerramiento;
    private Button myBtnUploadPuntoRecto;
    private Button myBtnUploadBrazoInvisible;
    private ImageView imageViewImagenASubir;
   // private ProgressBar progressbar;
    private Uri myImageUri;
    private static final Integer INTENT_GALERIA = 1;
    private StorageReference fileReference;
    public UploadImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_image, container, false);
        myBtnSelectImage = view.findViewById(R.id.select_image_button);
        myStoragePuntoRecto = FirebaseStorage.getInstance().getReference("Uploads").child("ToldoPuntoRecto");
        myStorageBrazoInvisible = FirebaseStorage.getInstance().getReference("Uploads").child("ToldoBrazoInvisible");
        myStorageCerramiento = FirebaseStorage.getInstance().getReference("Uploads").child("ToldoCerramiento");
        myStorageToldoFijo = FirebaseStorage.getInstance().getReference("Uploads").child("ToldoFijo");
        myDatabase = FirebaseDatabase.getInstance().getReference("Uploads");
        myBtnUploadCerramiento = view.findViewById(R.id.upload_Buttom_ceramiento);
        myBtnUploadBrazoInvisible = view.findViewById(R.id.upload_Buttom_brazo_invisible);
        myBtnUploadToldoFijo = view.findViewById(R.id.upload_Buttom_toldo_fijo);
        myBtnUploadPuntoRecto = view.findViewById(R.id.upload_Buttom_punto_recto);
        imageViewImagenASubir = view.findViewById(R.id.imagen_a_subir);
       // progressbar = view.findViewById(R.id.progresa_bar);
        myBtnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               selectorDeImagenes();
            }
        });

        myBtnUploadPuntoRecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImagenes(view);
            }
        });
        myBtnUploadCerramiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImagenes(view);
            }
        });
        myBtnUploadBrazoInvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImagenes(view);
            }
        });
        myBtnUploadToldoFijo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImagenes(view);
            }
        });
        return view;
    }



    private void selectorDeImagenes(){
        //Abrir galeria
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, INTENT_GALERIA);
    }

    private void uploadImagenes(View view){
        if(myImageUri != null){
            if(view.getId() == (R.id.upload_Buttom_brazo_invisible)){
                fileReference = myStorageBrazoInvisible.child(System.currentTimeMillis()
                        +"."+getFileExtension(myImageUri));
            } else if(view.getId() == (R.id.upload_Buttom_ceramiento)){
                fileReference = myStorageCerramiento.child(System.currentTimeMillis()
                        +"."+getFileExtension(myImageUri));
            } else if(view.getId() == (R.id.upload_Buttom_punto_recto)){
                fileReference = myStoragePuntoRecto.child(System.currentTimeMillis()
                        +"."+getFileExtension(myImageUri));
            } else {
                fileReference = myStorageToldoFijo.child(System.currentTimeMillis()
                        +"."+getFileExtension(myImageUri));
            }
            fileReference.putFile(myImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    /*//Este handler es para que el usuario pueda ver la carga en 100%
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setProgress(0);
                        }
                    }, 2000);*/
                    Toast.makeText(getContext(), "La carga de la imagen fue exitosa", Toast.LENGTH_SHORT).show();
                    ImageUpload upload = new ImageUpload(fileReference.getDownloadUrl().toString());
                    //String uploadId = myDatabase.push().getKey;
                    if((fileReference.toString()).contains("ToldoPuntoRecto")){
                        myDatabase.child("ToldoPuntoRecto").push().setValue(upload);
                    } else if((fileReference.toString().contains("ToldoBrazoInvisible"))){
                        myDatabase.child("ToldoBrazoInvisible").push().setValue(upload);
                    } else if((fileReference.toString().contains("ToldoCerramiento"))){
                        myDatabase.child("ToldoCerramiento").push().setValue(upload);
                    } else if((fileReference.toString().contains("ToldoFijo"))){
                        myDatabase.child("ToldoFijo").push().setValue(upload);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    //Esta es la cuenta para mostrar el % de progreso
                    /*double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressbar.setProgress((int) progress);*/
                }
            });
        } else {
            Toast.makeText(getContext(), "Seleccione una imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == INTENT_GALERIA && resultCode == RESULT_OK
        && data != null && data.getData() != null){
            myImageUri = data.getData();
            Glide.with(getContext())
                    .load(myImageUri)
                    .into(imageViewImagenASubir);

        }
    }
}
