package com.example.abbes.marvelapp.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abbes.marvelapp.Autre.AppContext;
import com.example.abbes.marvelapp.BaseDeDonnées.DBHelper;
import com.example.abbes.marvelapp.Adapter.MarvelAdapter;
import com.example.abbes.marvelapp.Adapter.MyDescriptionRecycleViewAdapter;
import com.example.abbes.marvelapp.Autre.PicassoClient;
import com.example.abbes.marvelapp.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionModel extends Fragment {

    private DBHelper db ;
    private boolean aimer = false ;
    private TextView Nom, description ;
    private ImageView image;
    private MyDescriptionRecycleViewAdapter adapter;
    private Button ListSeries ,ListComics,Favorisbtn ;
    private int i ;
    private RecyclerView recyclerlist ;


    public DescriptionModel() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_description_model, container, false);

        db = new DBHelper(AppContext.getContext());

        //Pour savoir d'ou in vient
        if (this.getArguments().getString("nom") != null) {
            i = Integer.parseInt(this.getArguments().getString("nom"));
        }

        else{
            String nom  = this.getArguments().getString("itemfavoris");
            i = MarvelAdapter.getIndexByname(nom);

        }

        final List<String> comic = new ArrayList<>();
        final List<String> serie = new ArrayList<>();


        Nom = rootview.findViewById(R.id.text);
        description = rootview.findViewById(R.id.description);
        image = rootview.findViewById(R.id.dsmarvelImage);
        Favorisbtn = rootview.findViewById(R.id.favoris);
        ListComics = rootview.findViewById(R.id.clickcomic);
        ListSeries = rootview.findViewById(R.id.clickserie);

        recyclerlist = rootview.findViewById(R.id.comiclist);

        recyclerlist.setLayoutManager(new LinearLayoutManager(AppContext.getContext()));

        for(int j=0 ; j < MarvelAdapter.getMdl().get(i).getComicsList().size() ; j++){

            comic.add(MarvelAdapter.getMdl().get(i).getComicsList().get(j).getComic()) ;
        }

        for(int j=0 ; j < MarvelAdapter.getMdl().get(i).getSeriesList().size() ; j++) {

            serie.add(MarvelAdapter.getMdl().get(i).getSeriesList().get(j).getSerie()) ;

        }


        Nom.setText(MarvelAdapter.getMdl().get(i).getName());
        description.setText(MarvelAdapter.getMdl().get(i).getDescription());
        if(description.getText().equals("")){
            description.setText("Pas de description disponible pour le moment");
            ScrollView s = rootview.findViewById(R.id.taille);
            s.getLayoutParams().height = 200 ;
        }

        PicassoClient.downloadImage(AppContext.getContext(),MarvelAdapter.getMdl().get(i).getUrlImage(),image);

        // On verifie si le Model est dans favoris pour la couleur du coeur

        if(db.getData(Nom.getText().toString())){
            Favorisbtn.setBackgroundResource(R.drawable.coeur_r_icon);
            aimer = true ;
        }


        Favorisbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if(aimer){

            Favorisbtn.setBackgroundResource(R.drawable.coeur_icon);

            if(db.deleteModel(Nom.getText().toString()) == 1){
                aimer = false ;
            }
            else{
                Toast.makeText(AppContext.getContext(),"erreur lors de la supression",Toast.LENGTH_SHORT).show();
            }
        }
        else {

            Favorisbtn.setBackgroundResource(R.drawable.coeur_r_icon);

            if(db.Insertfavoris(Nom.getText().toString(),MarvelAdapter.getMdl().get(i).getUrlImage())) {
                aimer = true ;
            }
            else {
                Toast.makeText(AppContext.getContext(),"erreur lors de l'insertion",Toast.LENGTH_LONG).show();
            }
        }
    }
});

        // defiler les series du model
        ListSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               adapter = new MyDescriptionRecycleViewAdapter(getActivity(),serie);
               runLayoutAnimation(recyclerlist);
            }
        });

        //defiler les comics du model
        ListComics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               adapter = new MyDescriptionRecycleViewAdapter(getActivity(),comic);
               runLayoutAnimation(recyclerlist);
            }
        });

        return rootview ;
    }

    // fonction Animation pour le RecycleView

    private void runLayoutAnimation(final RecyclerView recyclerView) {

        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.setAdapter(adapter);
        recyclerView.scheduleLayoutAnimation();
    }

}
