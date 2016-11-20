package cj1098.animeshare;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cj1098.animeshare.userList.AnimeObject;

public class ShowsRecyclerAdapter extends RecyclerView.Adapter<ShowsRecyclerAdapter.ShowsViewHolder> {
    private Context mContext;
    private ArrayList<AnimeObject> mShowObjects;

    public ShowsRecyclerAdapter(Context context, ArrayList<AnimeObject> data) {
        this.mContext = context;
        this.mShowObjects = data;
    }

    public static class ShowsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.anime_title) TextView title;
        @Bind(R.id.anime_description) TextView synopsis;
        @Bind(R.id.anime_image) ImageView image;
        @Bind(R.id.anime_rating) RatingBar rb;
        @Bind(R.id.image_loader) ProgressBar pb;

        public ShowsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.anime_image:
                   break;
            }
        }
    }

    @Override
    public ShowsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.userlist_grid_singleview, parent, false);
        return new ShowsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ShowsViewHolder holder, final int position) {
        Glide.with(mContext)
                .load(mShowObjects.get(position).getCover_image())
                .asBitmap()
                .error(R.drawable.code_geass)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        holder.pb.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        holder.pb.setVisibility(View.GONE);
                        holder.image.setAdjustViewBounds(true);
                        holder.image.setImageBitmap(bitmap);
                    }
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        holder.pb.setVisibility(View.GONE);
                        holder.image.setImageDrawable(errorDrawable);
                    }
                });
        holder.title.setText(mShowObjects.get(position).getTitle());
        holder.rb.setRating(mShowObjects.get(position).getCommunity_rating());

        holder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(mShowObjects.get(position).getTitle())
                    .setMessage(mShowObjects.get(position).getSynopsis())
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return mShowObjects.size();
    }

}