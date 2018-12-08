package com.example.abbes.marvelapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abbes.marvelapp.Autre.AppContext;
import com.example.abbes.marvelapp.ClassObject.FavorisItem;
import com.example.abbes.marvelapp.Fragment.DescriptionModel;
import com.example.abbes.marvelapp.Autre.PicassoClient;
import com.example.abbes.marvelapp.R;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<FavorisItem> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Bundle bundle;
    private Context context;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, List<FavorisItem> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.favoris_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = mData.get(position).getNom();
        holder.myTextView.setText(text);
        PicassoClient.downloadImage(AppContext.getContext(),mData.get(position).getUrlImg(),holder.myImage);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView myImage ;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.favoriName);
            myImage = itemView.findViewById(R.id.favimg);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

                bundle = new Bundle();
                bundle.putString("itemfavoris", String.valueOf(getItem(getAdapterPosition())));
                replacefragment();

        }
    }

    private void replacefragment() {

        Fragment fragment;
        fragment = new DescriptionModel();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_content,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id).getNom();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}