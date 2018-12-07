package com.example.abbes.marvelapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionModel extends Fragment {
    private  DBHelper db ;
    boolean aimer = false ;
    TextView text, description ;
    ImageView image;
    MyDescriptionRecycleViewAdapter adapter;
    Button clickcomic , clickserie,favoris ;
    int i ;
    RecyclerView recyclerlist ;
    String nom ;


    public DescriptionModel() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_description_model, container, false);

        db = new DBHelper(getContext());


        if (getArguments() != null) {
            i = Integer.parseInt(this.getArguments().getString("nom"));
        }




        final List<String> comic = new ArrayList<>();
        final List<String> serie = new ArrayList<>();


Toast.makeText(getContext(),String.valueOf(MarvelAdapter.getMdl().size()),Toast.LENGTH_LONG).show();

        text = rootview.findViewById(R.id.text);
        description = rootview.findViewById(R.id.description);
        image = rootview.findViewById(R.id.dsmarvelImage);
        favoris = rootview.findViewById(R.id.favoris);
        clickcomic = rootview.findViewById(R.id.clickcomic);
        clickserie = rootview.findViewById(R.id.clickserie);

        recyclerlist = rootview.findViewById(R.id.comiclist);

        recyclerlist.setLayoutManager(new LinearLayoutManager(getContext()));
        //    nom = String.valueOf(fetchdata.ModelList.get(i).getComicsList().size());
        for(int j=0 ; j < MarvelAdapter.getMdl().get(i).getComicsList().size() ; j++) {

            String   kom = MarvelAdapter.getMdl().get(i).getComicsList().get(j).getComic();

            comic.add(kom);
        }

        for(int j=0 ; j < MarvelAdapter.getMdl().get(i).getSeriesList().size() ; j++) {

            String   kom = MarvelAdapter.getMdl().get(i).getSeriesList().get(j).getSerie();


            serie.add(kom);
        }



        text.setText(MarvelAdapter.getMdl().get(i).getName());
        description.setText(MarvelAdapter.getMdl().get(i).getDescription());

        PicassoClient.downloadImage(getContext(),MarvelAdapter.getMdl().get(i).getUrlImage(),image);

        if(db.getData(text.getText().toString())){
            favoris.setBackgroundResource(R.drawable.coeur_r_icon);
            aimer = true ;
        }


   favoris.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        if(aimer){

            favoris.setBackgroundResource(R.drawable.coeur_icon);

            if(db.deleteModel(text.getText().toString()) == 1){
                Toast.makeText(getContext(),"supprimer avec succes",Toast.LENGTH_SHORT).show();
                aimer = false ;
            }
            else{
                Toast.makeText(getContext(),"erreur lors de la supression",Toast.LENGTH_SHORT).show();

            }

        }
        else {
            favoris.setBackgroundResource(R.drawable.coeur_r_icon);
            if(db.Insertfavoris(text.getText().toString(),MarvelAdapter.getMdl().get(i).getUrlImage())) {
                Toast.makeText(getContext(),"succes",Toast.LENGTH_SHORT).show();
                aimer = true ;
            }
            else {
                Toast.makeText(getContext(),"non",Toast.LENGTH_LONG).show();
            }
        }





    }
});


        clickserie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              adapter = new MyDescriptionRecycleViewAdapter(getContext(),serie);
               runLayoutAnimation(recyclerlist);
            }
        });


        clickcomic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              adapter = new MyDescriptionRecycleViewAdapter(getContext(),comic);
               runLayoutAnimation(recyclerlist);
            }
        });
        return rootview ;
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {

        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.setAdapter(adapter);
        recyclerView.scheduleLayoutAnimation();
    }

}
