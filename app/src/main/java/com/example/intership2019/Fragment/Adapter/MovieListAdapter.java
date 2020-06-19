package com.example.intership2019.Fragment.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.intership2019.Constant;
import com.example.intership2019.Fragment.MovieList.MovieDetailActivity;
import com.example.intership2019.Fragment.MovieList.MovieListMain.ListOfMovie;
import com.example.intership2019.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.RecyclerViewHolder> {

      interface IOnItemClickListener {
        void onItemClick();
    }

    private List<ListOfMovie> listOfMovie;
    private Activity mActivityMovieDetail;

    public MovieListAdapter(List<ListOfMovie> items, Activity activityMovieDetail) {
        listOfMovie = items;
        mActivityMovieDetail = activityMovieDetail;
    }

    @NonNull
    @Override
    public MovieListAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View contactView = layoutInflater.inflate(R.layout.item_row_movielist, viewGroup, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(contactView);

        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MovieListAdapter.RecyclerViewHolder recyclerViewHolder, final int position) {
        final ListOfMovie itemList = listOfMovie.get(position);

        TextView textTitleMovie = recyclerViewHolder.textTitleMovie;
        textTitleMovie.setText(itemList.getTitle());

        TextView textOverView = recyclerViewHolder.textOverView;
        textOverView.setText(itemList.getOverview());

        TextView textReleaseDuration = recyclerViewHolder.textReleaseDuration;


        if (itemList.getDuration() != null && itemList.getReleaseDate() != null) {
            textReleaseDuration.setText(itemList.getReleaseDate() + ", " +
                    itemList.getDuration() / 60 + " hour " + itemList.getDuration() % 60 + " minute ");
        } else {
            textReleaseDuration.setText(null);
        }


        ImageView imageMovie = recyclerViewHolder.imageMovie;
        Picasso.get().
                load(Constant.LINK_IMAGE_MOVIE + itemList.getPosterPath()).
                placeholder(R.drawable.ic_launcher_background).fit().into(imageMovie);

        ImageView imageRateMovie1 = recyclerViewHolder.imageRateMovie1;
        ImageView imageRateMovie2 = recyclerViewHolder.imageRateMovie2;
        ImageView imageRateMovie3 = recyclerViewHolder.imageRateMovie3;
        ImageView imageRateMovie4 = recyclerViewHolder.imageRateMovie4;
        ImageView imageRateMovie5 = recyclerViewHolder.imageRateMovie5;

        if (itemList.getVoteAverage() <= 2) {
            imageRateMovie1.setImageResource(R.drawable.staryellow_16px);
        } else if (itemList.getVoteAverage() > 2 && itemList.getVoteAverage() <= 4) {
            imageRateMovie1.setImageResource(R.drawable.staryellow_16px);
            imageRateMovie2.setImageResource(R.drawable.staryellow_16px);
        } else if (itemList.getVoteAverage() > 4 && itemList.getVoteAverage() <= 6) {
            imageRateMovie1.setImageResource(R.drawable.staryellow_16px);
            imageRateMovie2.setImageResource(R.drawable.staryellow_16px);
            imageRateMovie3.setImageResource(R.drawable.staryellow_16px);
        } else if (itemList.getVoteAverage() > 6 && itemList.getVoteAverage() <= 8) {
            imageRateMovie1.setImageResource(R.drawable.staryellow_16px);
            imageRateMovie2.setImageResource(R.drawable.staryellow_16px);
            imageRateMovie3.setImageResource(R.drawable.staryellow_16px);
            imageRateMovie4.setImageResource(R.drawable.staryellow_16px);
        } else if (itemList.getVoteAverage() > 8 && itemList.getVoteAverage() <= 10) {
            imageRateMovie1.setImageResource(R.drawable.staryellow_16px);
            imageRateMovie2.setImageResource(R.drawable.staryellow_16px);
            imageRateMovie3.setImageResource(R.drawable.staryellow_16px);
            imageRateMovie4.setImageResource(R.drawable.staryellow_16px);
            imageRateMovie5.setImageResource(R.drawable.staryellow_16px);
        }

        recyclerViewHolder.onBind(new IOnItemClickListener() {
            @Override
            public void onItemClick() {
                Intent intent = new Intent(mActivityMovieDetail, MovieDetailActivity.class);//***

                //intent.putExtra();

                Bundle bundle = new Bundle();//***
                bundle.putInt(Constant.KEY_MOVIE_ID, itemList.getId());
//                intent.putExtra(Constant.KEY_MOVIE_ID,itemList.getId());//cach 2 truyen bang extra
                bundle.putString(Constant.KEY_MOVIE_NAME, itemList.getTitle());
                bundle.putString(Constant.KEY_MOVIE_OVERVIEW, itemList.getOverview());
                if (itemList.getDuration() != null) {
                    bundle.putInt(Constant.KEY_MOVIE_DURATION, itemList.getDuration());
                } else bundle.putInt(Constant.KEY_MOVIE_DURATION, 0);

                intent.putExtras(bundle);//*****
                mActivityMovieDetail.startActivity(intent);
                //mActivityMovieDetail.startActivityForResult(intent,100);
            }
        });
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitleMovie, textOverView, textReleaseDuration, textMovieId;
        private ImageView imageMovie, imageRateMovie1, imageRateMovie2, imageRateMovie3, imageRateMovie4, imageRateMovie5;

        public RecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);
            textTitleMovie = itemView.findViewById(R.id.textTitleMovie);
            textOverView = itemView.findViewById(R.id.textOverView);
            textReleaseDuration = itemView.findViewById(R.id.textRelease_Duration);
            imageMovie = itemView.findViewById(R.id.imageMovie);
            imageRateMovie1 = itemView.findViewById(R.id.imageRateMovie1);
            imageRateMovie2 = itemView.findViewById(R.id.imageRateMovie2);
            imageRateMovie3 = itemView.findViewById(R.id.imageRateMovie3);
            imageRateMovie4 = itemView.findViewById(R.id.imageRateMovie4);
            imageRateMovie5 = itemView.findViewById(R.id.imageRateMovie5);

        }

        public void onBind(final IOnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (listOfMovie != null) {
            return listOfMovie.size();
        } else return 0;
    }

}
