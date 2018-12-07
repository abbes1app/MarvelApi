package com.example.abbes.marvelapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MarvelAdapter extends BaseAdapter {

  private  Context context;
  private List<MarvelModel> mdl;

    public List<MarvelModel> getMdl() {
        return mdl;
    }

    public void setMdl(List<MarvelModel> mdl) {
        this.mdl = mdl;
    }

    MarvelAdapter(Context context) {
        this.context = context;
        this.mdl = new ArrayList<>();
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

        ListMarvel.etat = true ;


    }

    public void clear() {
         mdl.clear();
    }
}
