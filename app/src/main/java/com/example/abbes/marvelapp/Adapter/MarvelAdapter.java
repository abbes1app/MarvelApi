package com.example.abbes.marvelapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abbes.marvelapp.Fragment.ListMarvel;
import com.example.abbes.marvelapp.ClassObject.MarvelModel;
import com.example.abbes.marvelapp.Autre.PicassoClient;
import com.example.abbes.marvelapp.R;

import java.util.ArrayList;
import java.util.List;

public class MarvelAdapter extends BaseAdapter {

    private  Context context;

    private static List<MarvelModel> mdl;

    public static List<MarvelModel> getMdl() {
        return mdl;
    }


    public static int getIndexByname(String Name)
    {
        for(MarvelModel item : mdl)
        {
            if(item.getName().equals(Name))
                return mdl.indexOf(item);
        }
        return -1;
    }

    public void setMdl(List<MarvelModel> mdl) {
        mdl = mdl;
    }

    public MarvelAdapter(Context context) {
        this.context = context;
        mdl = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mdl.size();
    }

    @Override
    public Object getItem(int position) {
        return mdl.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        @SuppressLint("ViewHolder")


        View v = View.inflate(context, R.layout.model, null);
        TextView     nameTxt = v.findViewById(R.id.nameTxt);
        ImageView   img = v.findViewById(R.id.marvelImage);

        MarvelModel item = mdl.get(position);
        nameTxt.setText(item.getName());
        PicassoClient.downloadImage(context,item.getUrlImage(),img);


        return v;
    }


    public void loadlist(List<MarvelModel> list) {

        mdl.addAll(list);

        notifyDataSetChanged();

        ListMarvel.Chargement = false ;


    }

    public static void clear() {
         mdl.clear();
    }
}
