package com.example.abbes.marvelapp.ClassObject;

public class FavorisItem {


        private String Nom;
        private String urlImg;


        public FavorisItem(String Nom, String urlImg) {
            this.Nom = Nom;
            this.urlImg = urlImg;
        }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
