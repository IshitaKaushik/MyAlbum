package com.example.myalbum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements onClicked{
    private AlbumRecyclerAdapter albumAdapter;
    private AlbumViewModel albumViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycleAlbum);
        albumAdapter=new AlbumRecyclerAdapter(new AlbumRecyclerAdapter.AlbumDiff(),this);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(albumAdapter);
        albumViewModel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(AlbumViewModel.class);
        albumViewModel.allAlbums.observe(this, new Observer<List<Album>>() {
            @Override
            public void onChanged(List<Album> albums) {
               albumAdapter.update(albums);
            }
        });

        Intent intent=getIntent();
        String[] details=intent.getStringArrayExtra("albumDetails");

        String[] editedDetails=intent.getStringArrayExtra("editedAlbum");

        if(details != null)
            albumViewModel.insert(new Album(details[0],details[1],Integer.parseInt(details[3]),Integer.valueOf(details[2])));
        if(editedDetails != null) {
            albumViewModel.update(new Album(editedDetails[0], editedDetails[1], Integer.parseInt(editedDetails[2]), Integer.valueOf(editedDetails[3])), Integer.parseInt(editedDetails[4]));

        }

    }

    @Override
    public void onClicking(Album album) {
        Intent intent= new Intent(this,TrackActivity.class);
        intent.putExtra("albumId",album.getAlbumId()+"");
        intent.putExtra("albumName",album.getTitle());
        intent.putExtra("albumUrl",album.getAlbumImageUrl());
        intent.putExtra("albumRating",album.getRating()+"");
        startActivity(intent);
    }

    @Override
    public void onClickDelete(Album album) {
        albumViewModel.delete(album);
    }

    @Override
    public void onCickUpdate(Album album) {
        Intent intent= new Intent(this,EditALbumActivity.class);
        String[] s=new String[5];
        s[0]=album.getTitle();
        s[1]=album.getAlbumImageUrl();
        s[2]=(int)album.getRating()+"";
        s[3]=album.getYear()+"";
        s[4]=album.getAlbumId()+"";
        intent.putExtra("albumEdit",s);
        startActivity(intent);

    }

    public void createAlbum(View view){
        Intent intent= new Intent(this,CreateAlbumActivity.class);
        startActivity(intent);
    }


}