package com.example.abbes.marvelapp;

import java.util.ArrayList;
import java.util.List;

public class MarvelModel {

    private String id;
    private String name;
    private String description;
    private String urlImage ;
    private List<Comics> comicsList ;
    private List<Series> seriesList ;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public List<Comics> getComicsList() {
        return comicsList;
    }

    public void setComicsList(List<Comics> comicsList) {
        this.comicsList = comicsList;
    }

    public List<Series> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(List<Series> seriesList) {
        this.seriesList = seriesList;
    }


    public static class Comics {
        private String comic;

        public String getComic() {
            return comic;
        }

        public void setComic(String comic) {
            this.comic = comic;
        }
    }


    public static class Series {
        private String serie;

        public String getSerie() {
            return serie;
        }

        public void setSerie(String serie) {
            this.serie = serie;
        }
    }


}
