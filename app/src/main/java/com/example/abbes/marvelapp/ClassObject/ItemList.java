package com.example.abbes.marvelapp.ClassObject;


import android.graphics.Bitmap;

public class ItemList {

    private int imgId;
    private String title;


   public ItemList(int imgId, String title) {
        this.imgId = imgId;
        this.title = title;
    }



    public int getImgId() {

        return imgId;
    }

    public void setImgId(int imgId) {

        this.imgId = imgId;
    }

    public String getTitle() {
        return title;

    }

    public void setTitle(String title) {

        this.title = title;

    }

}
