package com.example.myalbum;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Album album);

    @Query("UPDATE album_table SET Title=:title ,AlbumImageURL=:albumImageURL ,Rating=:rating , Year=:year WHERE albumId=:aid")
    void update(int aid,String title , String albumImageURL,int rating , int year);

    @Delete
    void delete(Album album);

    @Query("SELECT * FROM album_table ORDER BY Rating DESC")
    LiveData<List<Album>> getAlbums();
}