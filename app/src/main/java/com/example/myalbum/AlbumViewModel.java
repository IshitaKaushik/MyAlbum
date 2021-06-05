package com.example.myalbum;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AlbumViewModel extends AndroidViewModel {
    public LiveData<List<Album>> allAlbums;
    public AlbumRepository albumRepository;

    public AlbumViewModel(@NonNull Application application) {
        super(application);
        albumRepository=new AlbumRepository(application);
        allAlbums=albumRepository.getAllAlbums();
    }

    public void delete(Album album){
        albumRepository.delete(album);
    }

    public void insert(Album album){
        albumRepository.insert(album);
    }

    public void update(Album album,int id){
        albumRepository.update(album,id);
    }


}
