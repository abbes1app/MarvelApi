package com.example.abbes.marvelapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abbes.marvelapp.R;

import java.util.List;

public class MyDescriptionRecycleViewAdapter extends RecyclerView.Adapter<MyDescriptionRecycleViewAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context ;

    // data is passed into the constructor
    public MyDescriptionRecycleViewAdapter(Context context, List<String> data) {
        this.context = context ;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.recycleview_row, parent, false);


        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = mData.get(position);
        holder.myTextView.setText(text);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.comicName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (mClickListener != null)mClickListener.onItemClick(view, getAdapterPosition());

        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
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