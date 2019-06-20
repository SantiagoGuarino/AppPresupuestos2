package com.example.apppresupuestos;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerViewTemporal extends Fragment {
    private DatabaseReference myDatabaseReference;
    private List<ImageUpload> imageUploadsList;
    private DatabaseReferenceObject myDatabaseReferenceObject;
    private View view;

    public RecyclerViewTemporal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view =  inflater.inflate(R.layout.fragment_recycler_view_temporal, container, false);

        Bundle bundle = getArguments();
        myDatabaseReferenceObject = (DatabaseReferenceObject) bundle.getSerializable(ViewPagerFragment.KEY_DATABASE_REFERENCE);
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
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_temporal);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()
                , RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerViewAdapterTemporal recyclerViewAdapterTemporal = new RecyclerViewAdapterTemporal(imageUploadsList);
        recyclerView.setAdapter(recyclerViewAdapterTemporal);

        return view;
    }

}
