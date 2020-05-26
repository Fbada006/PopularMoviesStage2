package com.disruption.popularmovies.adapter;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.disruption.popularmovies.R;
import com.disruption.popularmovies.model.trailer.Trailer;

public class TrailersAdapter extends ListAdapter<Trailer, TrailersAdapter.TrailerViewHolder> {
    private static final DiffUtil.ItemCallback<Trailer> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Trailer>() {
                @Override
                public boolean areItemsTheSame(@NonNull Trailer oldItem, @NonNull Trailer newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Trailer oldItem, @NonNull Trailer newItem) {
                    return oldItem == newItem;
                }
            };

    public TrailersAdapter() {
        super(DIFF_CALLBACK);
    }

    private static void watchYoutubeVideo(Context context, String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, final int position) {
        Trailer trailer = getItem(position);
        holder.bind(trailer);
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {
        final ImageView playImage;

        TrailerViewHolder(View v) {
            super(v);
            playImage = v.findViewById(R.id.iv_trailer_play);
        }

        private void bind(Trailer trailer) {
            playImage.setOnClickListener(view -> watchYoutubeVideo(itemView.getContext(), trailer.getKey()));
        }
    }
}