package com.example.apppresupuestos;

import java.io.Serializable;

public class ImageUpload implements Serializable {
    private String imageUrl;

    public ImageUpload() {
        //Es de alguna forma necesario
    }
    public ImageUpload( String imageUrl){
        this.imageUrl = imageUrl;
    }



    public String getImageUrl() {
        return imageUrl;
    }



    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
