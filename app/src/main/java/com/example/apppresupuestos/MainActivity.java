package com.example.apppresupuestos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity implements SeleccionFragment.MyDatabaseReferenceListener, SeleccionFragment.SubirImagenes {
    private UploadImageFragment uploadImageFragment = new UploadImageFragment();
    private ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
    private SeleccionFragment seleccionFragment = new SeleccionFragment();
    private RecyclerViewTemporal recyclerViewTemporal = new RecyclerViewTemporal();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pegarFragment(seleccionFragment);
    }

    public void pegarFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_main_activity, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void myDatabaseReferenceData(DatabaseReferenceObject myDatabaseReference) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ViewPagerFragment.KEY_DATABASE_REFERENCE, myDatabaseReference);
        recyclerViewTemporal.setArguments(bundle);
        pegarFragment(recyclerViewTemporal);
    }

    @Override
    public void subirImagenesListener() {
        pegarFragment(uploadImageFragment);
    }
}
