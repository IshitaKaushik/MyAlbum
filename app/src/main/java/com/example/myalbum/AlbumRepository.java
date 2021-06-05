package com.example.myalbum;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AlbumRepository {
    private AlbumDao albumDao;

    AlbumRepository(Application application){
        MyDatabase albumDatabase = MyDatabase.getInstance(application);
        albumDao=albumDatabase.albumDao();
    }

    void insert(Album album){
        MyDatabase.databaseWriter.execute(()->{
           albumDao.insert(album);
        });
    }

    void delete(Album album){
        MyDatabase.databaseWriter.execute(()->{
            albumDao.delete(album);
        });
    }

    void update(Album album,int albumId){
        MyDatabase.databaseWriter.execute(()->{
            albumDao.update(albumId,album.getTitle(),album.getAlbumImageUrl(),album.getRating(),album.getYear());
        });
    }

    LiveData<List<Album>> getAllAlbums(){
        return albumDao.getAlbums();
    }
}
