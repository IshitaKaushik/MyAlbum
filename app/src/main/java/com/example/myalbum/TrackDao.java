package com.example.myalbum;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Track track);

    @Delete
    void delete(Track track);

    @Query("UPDATE track_table SET TrackName=:trackName ,TrackImageURL=:trackImageURL ,Duration=:duration ,Singers=:singers WHERE trackId=:tid")
    void update(int tid,String trackName , String trackImageURL, String duration , String singers);


@Query("SELECT * FROM track_table WHERE AlbumId=:albumID")
    LiveData<List<Track>> getTracks(int albumID);
}