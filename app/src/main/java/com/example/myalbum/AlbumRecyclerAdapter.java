package com.example.myalbum;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AlbumRecyclerAdapter extends ListAdapter<Album,AlbumRecyclerViewHolder> {
    private List<Album> arrayList=new ArrayList<>();
    private onClicked click;

    public AlbumRecyclerAdapter(@NonNull DiffUtil.ItemCallback<Album> diffCallback,onClicked click) {
        super(diffCallback);
        this.click=click;
    }

    @NonNull
    @Override
    public AlbumRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album,parent,false);
        AlbumRecyclerViewHolder albumViewHolder=new AlbumRecyclerViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onClicking(arrayList.get(albumViewHolder.getAdapterPosition()));
            }
        });
        albumViewHolder.deleteAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onClickDelete(arrayList.get(albumViewHolder.getAdapterPosition()));
            }
        });

        albumViewHolder.editAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onCickUpdate(arrayList.get(albumViewHolder.getAdapterPosition()));
            }
        });

        return albumViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumRecyclerViewHolder holder, int position) {
        Album album=arrayList.get(position);
        holder.albumTitle.setText(album.getTitle());
        Picasso.get().load(Uri.parse(album.getAlbumImageUrl())).placeholder(R.drawable.album).into(holder.albumImage);
        holder.albumRating.setRating(album.getRating());
        holder.albumYear.setText(String.valueOf(album.getYear()));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void update(List<Album> albums){
        arrayList.clear();
        arrayList.addAll(albums);
        notifyDataSetChanged();
    }

    static class AlbumDiff extends DiffUtil.ItemCallback<Album> {


        @Override
        public boolean areItemsTheSame(@NonNull Album oldItem, @NonNull Album newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Album oldItem, @NonNull Album newItem) {
            return false;
        }
    }
}
class AlbumRecyclerViewHolder extends RecyclerView.ViewHolder{

    public AlbumRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    ImageView albumImage=itemView.findViewById(R.id.album_image);
    TextView albumTitle = itemView.findViewById(R.id.album_title);
    RatingBar albumRating=itemView.findViewById(R.id.album_rating);
    TextView albumYear=itemView.findViewById(R.id.album_year);
    ImageView deleteAlbum=itemView.findViewById(R.id.album_delete);
    ImageView editAlbum=itemView.findViewById(R.id.album_edit);
}

interface onClicked{
    void onClicking(Album album);
    void onClickDelete(Album album);
    void onCickUpdate(Album album);
}