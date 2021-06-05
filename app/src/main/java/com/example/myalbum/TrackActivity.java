package com.example.myalbum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TrackActivity extends AppCompatActivity implements onClickedTrack{
    private TrackRecyclerAdapter trackRecyclerAdapter;
    private TrackViewModel trackViewModel;
    private int albumId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleTrack);
        trackRecyclerAdapter = new TrackRecyclerAdapter(new TrackRecyclerAdapter.TrackDiff(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(trackRecyclerAdapter);
        trackViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(TrackViewModel.class);


        Intent intent = getIntent();
        String albumID = intent.getStringExtra("albumId");

        String[] detail = intent.getStringArrayExtra("trackDetails");
        String[] editedDetails=intent.getStringArrayExtra("editedTrack");
        if (albumID != null) {
            albumId = Integer.parseInt(albumID);
        }
            if (detail != null) {
                albumId=Integer.parseInt(detail[4]);
                trackViewModel.insert(new Track(detail[0], detail[1], detail[2], detail[3],albumId));
                Intent i=new Intent(TrackActivity.this,MainActivity.class);
                startActivity(i);
            }

            if(editedDetails != null) {
                trackViewModel.update(new Track(editedDetails[0], editedDetails[1], editedDetails[2], editedDetails[3], Integer.parseInt(editedDetails[5])), Integer.parseInt(editedDetails[4]));
                Intent i=new Intent(TrackActivity.this,MainActivity.class);
                startActivity(i);
            }
            TextView albumName=findViewById(R.id.album_ti);
            ImageView albumUrl=findViewById(R.id.album_im);
            String name=intent.getStringExtra("albumName");
            String url=intent.getStringExtra("albumUrl");
            if(name != null)
                albumName.setText(name);
            if(url != null)
                Picasso.get().load(Uri.parse(url)).placeholder(R.drawable.album).into(albumUrl);
            RatingBar ratingBar=findViewById(R.id.album_ra);
            String rating=intent.getStringExtra("albumRating");
            if(rating != null)
                ratingBar.setRating(Integer.parseInt(rating));


        trackViewModel.getTracks(albumId).observe(this, new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                trackRecyclerAdapter.update(tracks);
            }
        });
    }

    public void createTrack(View view){
        Intent intent= new Intent(this,CreateTrackActivity.class);
        intent.putExtra("albumThisId",albumId+"");
        startActivity(intent);
    }

    @Override
    public void onClickDeleteTrack(Track track) {
        trackViewModel.delete(track);

    }

    @Override
    public void onClickUpdateTrack(Track track) {

        Intent intent= new Intent(this,EditTrackActivity.class);
        String[] s=new String[6];
        s[0]=track.getTrackName();
        s[1]=track.getTrackImageUrl();
        s[2]=track.getDuration();
        s[3]=track.getSingers();
        s[4]=track.getTrackId()+"";
        s[5]=track.getParentId()+"";
        intent.putExtra("trackEdit",s);
        startActivity(intent);

    }
}