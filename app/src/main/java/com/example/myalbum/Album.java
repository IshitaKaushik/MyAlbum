package com.example.myalbum;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "album_table")
public class Album {
    @PrimaryKey(autoGenerate = true)private int albumId;

    @NonNull
    private@ColumnInfo(name = "Title")String title;
    @NonNull
    private@ColumnInfo(name = "AlbumImageURL")String albumImageUrl;
    @NonNull
    private@ColumnInfo(name = "Rating")int rating;
    @NonNull
    private@ColumnInfo(name = "Year")int year;

    public Album(String title,String albumImageUrl, int rating, int year){
        this.title=title;
        this.albumImageUrl = albumImageUrl;
        this.rating=rating;
        this.year=year;
    }

    public String getTitle() {
        return this.title;
    }
    public String getAlbumImageUrl() {
        return this.albumImageUrl;
    }
    public int getRating() {
        return this.rating;
    }
    public int getYear() {
        return this.year;
    }

    public int getAlbumId(){
        return albumId;
    }
    public void setAlbumId(int albumId){
        this.albumId=albumId;
    }
}