package com.slusher.flixter.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.telecom.Conference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.slusher.flixter.DetailActivity;
import com.slusher.flixter.MainActivity;
import com.slusher.flixter.R;
import com.slusher.flixter.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    //    RecyclerView.Adapter is an abstract class, and so we need to define these next three methods:

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
//        Inflate a layout from XML and return the holder

//        From the current state of the application/object (context), create the inflater object
//        Use the inflater object to inflate the custom layout from item_movie.xml (returns a View)
//        The attachToRoot parameter is false because it is the RecyclerViews's job to determine when
//        to inflate and present the child views
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie,
                parent, false);

        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
//        Populate data into the item through the holder

//        Get the movie at the passed in position
        Movie movie = movies.get(position);

//        Bind the movie data into the viewHolder
        holder.bind(movie);


    }

    @Override
    public int getItemCount() {
//        Return the total count of items in the list
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

//        Define member variables for each view in the viewHolder
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
//            Note: Remember that the ViewHolder is a representation of what we made in itemMovie.xml,
//            So that is what we're passing into the constructor here.
            super(itemView);
            tvTitle =  itemView.findViewById(R.id.tvTitle);
            tvOverview =  itemView.findViewById(R.id.tvOverview);
            ivPoster =  itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(final Movie movie) {
//            Bind the TextViews of the ViewHolder with the appropriate data
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            String imageUrl;

//            if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                imageUrl = backdrop image
                imageUrl = movie.getBackdropPath();
            } else {
//              else imageUrl = poster image
               imageUrl = movie.getPosterPath();
            }

//            Use Glide to bind the image to the ImageView
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop
            Glide.with(context).load(imageUrl).fitCenter()
                                .transform(new RoundedCornersTransformation(radius, margin))
                                .into(ivPoster);

//            1. Register click listener for the whole container
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                  2. Navigate to a new activity on the tap

//                    Create an intent to go to the DetailActivity
                    Intent i = new Intent(context, DetailActivity.class);

//                    pass data to the activity we want to go to
                    i.putExtra("movie", Parcels.wrap(movie));

                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) context, tvTitle, "textTransition");

                    context.startActivity(i, options.toBundle());
                }
            });
        }
    }
}
