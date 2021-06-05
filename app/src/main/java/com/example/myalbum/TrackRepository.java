package com.example.myalbum;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TrackRepository {
    private TrackDao trackdao;

    TrackRepository(Application application){
        MyDatabase albumDatabase = MyDatabase.getInstance(application);
        trackdao=albumDatabase.trackDao();
    }

    void insert(Track track){
        MyDatabase.databaseWriter.execute(()->{
            trackdao.insert(track);
        });
    }

    void update(Track track,int trackId){
        MyDatabase.databaseWriter.execute(()->{
            trackdao.update(trackId,track.getTrackName(),track.getTrackImageUrl(),track.getDuration(),track.getSingers());
        });
    }

    void delete(Track track){
        MyDatabase.databaseWriter.execute(()->{
            trackdao.delete(track);
        });
    }
    LiveData<List<Track>> getAllTracks(int albumId){
        return trackdao.getTracks(albumId);
    }
}
