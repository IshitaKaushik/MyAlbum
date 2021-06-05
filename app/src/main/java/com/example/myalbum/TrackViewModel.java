package com.example.myalbum;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TrackViewModel extends AndroidViewModel {
    public TrackRepository trackRepository;
    public int albumId;
    public LiveData<List<Track>> tracks;
    public TrackViewModel(@NonNull Application application) {
        super(application);
        trackRepository=new TrackRepository(application);
        tracks=trackRepository.getAllTracks(albumId);
    }

    public void insert(Track track){
        trackRepository.insert(track);
    }

    public void delete(Track track){
        trackRepository.delete(track);
    }

    public  void update(Track track,int trackId){
        trackRepository.update(track,trackId);
    }

    public LiveData<List<Track>> getTracks(int albumId) {

        return trackRepository.getAllTracks(albumId);
    }
}
