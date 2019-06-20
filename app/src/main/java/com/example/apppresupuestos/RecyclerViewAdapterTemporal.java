package com.example.apppresupuestos;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapterTemporal extends RecyclerView.Adapter<RecyclerViewAdapterTemporal.RecyclerViewHolder> {
    private List<ImageUpload> imageUploadList;

    public RecyclerViewAdapterTemporal(List<ImageUpload> imageUploadList) {
        this.imageUploadList = imageUploadList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.celda_recycler_temporal
                , parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        ImageUpload imageUploadActual = imageUploadList.get(position);
        holder.bindItem(imageUploadActual);
    }

    @Override
    public int getItemCount() {
        return imageUploadList.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagen_celda_recycler);
        }
        public void bindItem(ImageUpload imageUploadActual){
            Glide.with(itemView.getContext())
                    .load(imageUploadActual.getImageUrl())
                    .into(imageView);
        }
    }

}
