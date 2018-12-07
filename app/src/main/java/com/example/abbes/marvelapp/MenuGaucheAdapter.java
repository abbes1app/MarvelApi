package com.example.abbes.marvelapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MenuGaucheAdapter extends BaseAdapter {

    private Context context;
    private List<ItemList> listItem;

    public MenuGaucheAdapter(Context context, List<ItemList> lstItem) {
        this.context = context;
        this.listItem = lstItem;

    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        @SuppressLint("ViewHolder")
        View v = View.inflate(context, R.layout.item_menu, null);
        ImageView img = v.findViewById(R.id.item_img);
        TextView tv =   v.findViewById(R.id.item_title);

        ItemList item = listItem.get(position);
        img.setImageResource(item.getImgId());
        tv.setText(item.getTitle());

        return v;
    }
}
