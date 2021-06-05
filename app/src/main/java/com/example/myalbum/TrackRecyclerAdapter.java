package com.example.myalbum;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TrackRecyclerAdapter extends ListAdapter<Track,TrackRecyclerViewHolder>{
    private List<Track> trackArrayList=new ArrayList<>();
    private onClickedTrack clicked;

    protected TrackRecyclerAdapter(@NonNull DiffUtil.ItemCallback<Track> diffCallback,onClickedTrack clicked) {
        super(diffCallback);
        this.clicked=clicked;
    }

    @NonNull
    @Override
    public TrackRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track,parent,false);
        TrackRecyclerViewHolder trackViewHolder=new TrackRecyclerViewHolder(view);
        trackViewHolder.trackDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked.onClickDeleteTrack(trackArrayList.get(trackViewHolder.getAdapterPosition()));
            }
        });
        trackViewHolder.trackEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked.onClickUpdateTrack(trackArrayList.get(trackViewHolder.getAdapterPosition()));
            }
        });
        return trackViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrackRecyclerViewHolder holder, int position) {
        Track track=trackArrayList.get(position);
       Picasso.get().load(Uri.parse(track.getTrackImageUrl())).placeholder(R.drawable.music).into( holder.trackImage);
       holder.trackTitle.setText(track.getTrackName());
       holder.trackDuration.setText(track.getDuration());
       holder.trackSinger.setText(track.getSingers());

    }
    @Override
    public int getItemCount() {
        return trackArrayList.size();
    }

    public void update(List<Track> tracks){
        trackArrayList.clear();
        trackArrayList.addAll(tracks);
        notifyDataSetChanged();
    }

    static class TrackDiff extends DiffUtil.ItemCallback<Track> {


        @Override
        public boolean areItemsTheSame(@NonNull Track oldItem, @NonNull Track newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Track oldItem, @NonNull Track newItem) {
            return false;
        }
    }
}





class TrackRecyclerViewHolder extends RecyclerView.ViewHolder{

    public TrackRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    ImageView trackImage=itemView.findViewById(R.id.song_image);
    TextView trackTitle = itemView.findViewById(R.id.song_title);
    TextView trackSinger=itemView.findViewById(R.id.song_singer);
    TextView trackDuration=itemView.findViewById(R.id.song_duration);
    ImageView trackDelete=itemView.findViewById(R.id.song_delete);
    ImageView trackEdit=itemView.findViewById(R.id.song_edit);

}


interface onClickedTrack{
    void onClickDeleteTrack(Track track);
    void onClickUpdateTrack(Track track);
}
