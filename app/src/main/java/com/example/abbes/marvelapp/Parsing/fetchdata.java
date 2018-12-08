package com.example.abbes.marvelapp.Parsing;

import android.os.AsyncTask;

import com.example.abbes.marvelapp.Adapter.MarvelAdapter;
import com.example.abbes.marvelapp.ClassObject.MarvelModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhishek Panwar on 7/14/2017.
 */

public class fetchdata extends AsyncTask<Void,Void,Void> {

    private static List<MarvelModel> ModelList;
    private MarvelAdapter adapter ;
    private String nom = "" ;
    private String data ="";
    public static int count = 0;
    public static int offset = 0;
    public static int limit = 0;


    public fetchdata(MarvelAdapter adapter,String nom){

        this.adapter = adapter ;
        this.nom = nom ;

    }

    public fetchdata(MarvelAdapter adapter){

        this.adapter = adapter ;

    }



    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url;
                    if (nom.equals("")) {
                        url = new URL("https://gateway.marvel.com:443/v1/public/characters?offset="+offset+"&ts=1&apikey=007d6474b1b13067477db04343c0c385&hash=51758a84f6fadf388b4b02cc9dc0c702");
                    }
                    else {
                        url = new URL("https://gateway.marvel.com:443/v1/public/characters?name="+nom+"&ts=1&apikey=007d6474b1b13067477db04343c0c385&hash=51758a84f6fadf388b4b02cc9dc0c702");

                    }

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;

            }

            JSONObject JSONdata = new JSONObject(data);

            limit =  Integer.valueOf(JSONdata.getJSONObject("data").getString("limit"));
            count =  Integer.valueOf(JSONdata.getJSONObject("data").getString("count"));

            ModelList = new ArrayList<>() ;

            JSONArray MarvelList = JSONdata.getJSONObject("data").getJSONArray("results");

            for (int i =0 ; i< MarvelList.length();i++) {


                MarvelModel md = new MarvelModel();


                JSONObject JO = (JSONObject) MarvelList.get(i);

                String id = JO.getString("id");

                String name = JO.getString("name");

                String description = JO.getString("description");

                String dataParsed=  JO.getJSONObject("thumbnail").getString("path");
                String extension = JO.getJSONObject("thumbnail").getString("extension");



                List<MarvelModel.Series> seriesList = new ArrayList<>();

                JSONArray SeriesList = JO.getJSONObject("series").getJSONArray("items") ;

                for (int j =0 ; j< SeriesList.length();j++) {

                    JSONObject IO = (JSONObject) SeriesList.get(j);

                    MarvelModel.Series st = new  MarvelModel.Series();

                    st.setSerie(IO.getString("name"));
                    seriesList.add(st);


                }


                List<MarvelModel.Comics> comicsList = new ArrayList<>();

                JSONArray ComicsList = JO.getJSONObject("comics").getJSONArray("items") ;

                for (int j =0 ; j< ComicsList.length();j++) {

                    JSONObject IO = (JSONObject) ComicsList.get(j);

                    MarvelModel.Comics st = new  MarvelModel.Comics();

                    st.setComic(IO.getString("name"));
                    comicsList.add(st);

                }


                md.setId(id);
                md.setName(name);
                md.setDescription(description);
                md.setUrlImage(dataParsed+"."+extension);
                md.setSeriesList(seriesList);
                md.setComicsList(comicsList);

                ModelList.add(md);

            }


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

         adapter.loadlist(ModelList);

    }
}
