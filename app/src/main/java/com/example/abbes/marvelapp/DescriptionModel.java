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

    TextView text, description ;
    ImageView image;
    MyRecyclerViewAdapter adapter;
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




   favoris.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        favoris.setBackgroundResource(R.drawable.coeur_r_icon);
    }
});


        clickserie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = new MyRecyclerViewAdapter(getContext(),serie);
                runLayoutAnimation(recyclerlist);
            }
        });


        clickcomic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = new MyRecyclerViewAdapter(getContext(),comic);
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
