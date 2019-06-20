package com.example.apppresupuestos;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<ImagenesSubidasFragment>  listaDeImagenesSubidas;

    public ViewPagerAdapter(FragmentManager fm, List<ImageUpload> imageUploadList) {
        super(fm);
        listaDeImagenesSubidas = new ArrayList<>();
        for (ImageUpload track: imageUploadList){
            listaDeImagenesSubidas.add(ImagenesSubidasFragment.imagenesSubidasFragmentFactory(track));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return listaDeImagenesSubidas.get(position);
    }

    @Override
    public int getCount() {
        return listaDeImagenesSubidas.size();
    }
}
