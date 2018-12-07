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
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class favoris extends Fragment {

    private  DBHelper db ;
    MyRecyclerViewAdapter favorisadapter;
    RecyclerView favorislist ;
    public favoris() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favoris, container, false);

        db = new DBHelper(getContext());

        favorislist = v.findViewById(R.id.favorislist);
        favorislist.setLayoutManager(new LinearLayoutManager(getContext()));



        ArrayList<FavorisItem> array_list = db.getAllModels();
        if(array_list.size()>0){
            favorisadapter = new MyRecyclerViewAdapter(getContext(),array_list);
        }

        else{
            array_list.add(new FavorisItem("Vous n'avez pas encore ajouter de Marvel ",""));
            favorisadapter = new MyRecyclerViewAdapter(getContext(),array_list);

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
