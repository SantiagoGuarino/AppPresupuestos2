package com.example.apppresupuestos;


import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImagenesSubidasFragment extends Fragment {
    private static final String IMAGEN_SUBIDA_KEY = "imagenSubida";



    public static ImagenesSubidasFragment imagenesSubidasFragmentFactory(ImageUpload imageUpload) {
        ImagenesSubidasFragment imagenesSubidasFragment = new ImagenesSubidasFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(IMAGEN_SUBIDA_KEY, imageUpload);
        imagenesSubidasFragment.setArguments(bundle);
        return imagenesSubidasFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_imagenes_subidas, container, false);

        Bundle bundle = getArguments();
        ImageUpload imageUpload = (ImageUpload) bundle.getSerializable(IMAGEN_SUBIDA_KEY);
        ImageView imageViewFrameLayoutImage = view.findViewById(R.id.imagenes_para_view_pager);
        Glide.with(getContext())
                .load(imageUpload.getImageUrl())
                .into(imageViewFrameLayoutImage);

        return view;
    }

}
