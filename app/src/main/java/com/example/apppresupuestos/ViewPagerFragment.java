package com.example.apppresupuestos;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment {
    public static final String KEY_DATABASE_REFERENCE = "Database Reference";
    private DatabaseReference myDatabaseReference;
    private List<ImageUpload> imageUploadsList;
    private DatabaseReferenceObject myDatabaseReferenceObject;
    public ViewPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_view_pager, container, false);

        Bundle bundle = getArguments();
        myDatabaseReferenceObject = (DatabaseReferenceObject) bundle.getSerializable(KEY_DATABASE_REFERENCE);
        imageUploadsList = new ArrayList<>();
        myDatabaseReference = myDatabaseReferenceObject.getDatabaseReference();
        myDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot track: dataSnapshot.getChildren()){
                    ImageUpload imageUpload = track.getValue(ImageUpload.class);
                    imageUploadsList.add(imageUpload);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        ViewPager viewPagerPresupuestos = view.findViewById(R.id.view_pager_database_images);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), imageUploadsList);
        viewPagerPresupuestos.setAdapter(viewPagerAdapter);

        return view;
    }

}
