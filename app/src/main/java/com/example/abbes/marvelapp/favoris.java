package com.example.abbes.marvelapp;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class favoris extends Fragment {

    private DBHelper db ;
    private MyRecyclerViewAdapter favorisadapter;
    private RecyclerView favorislist ;
    private List<FavorisItem> array_list ;

    public favoris() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favoris, container, false);
        // Connexion a la base ;
        db = new DBHelper(getActivity());

        favorislist = v.findViewById(R.id.favorislist);
        favorislist.setLayoutManager(new LinearLayoutManager(getActivity()));


        MarvelAdapter.clear();

        // Recuperer la liste des favoris depuis la base
        array_list = db.getAllModels();

        // lancer une mise a jour pour chaque model
        for(int i = 0 ; i< array_list.size();i++){
            String nom = array_list.get(i).getNom();
            fetchdata process = new fetchdata(nom.replaceAll(" ","%20"));
            process.execute();
        }

         // tester si on a pas de favoris

        if(array_list.size()>0){
            favorisadapter = new MyRecyclerViewAdapter(getActivity(),array_list);
        }

        else{
            array_list.add(new FavorisItem("Vous n'avez pas encore ajouter de Marvel ",""));
            favorisadapter = new MyRecyclerViewAdapter(getActivity(),array_list);

        }

        runLayoutAnimation(favorislist);

    return  v ;

    }

    private void runLayoutAnimation(RecyclerView favorislist) {
        final Context context = favorislist.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        favorislist.setLayoutAnimation(controller);
        favorislist.setAdapter(favorisadapter);
        favorislist.scheduleLayoutAnimation();
    }

}
