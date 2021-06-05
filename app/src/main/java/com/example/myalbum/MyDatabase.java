package com.example.myalbum;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Album.class , Track.class} , version =1 , exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract AlbumDao albumDao();
    public abstract TrackDao trackDao();
    private static volatile MyDatabase INSTANCE;
    private static final int FIXED_THREADS = 4;

    static final ExecutorService databaseWriter = Executors.newFixedThreadPool(FIXED_THREADS);

    static MyDatabase getInstance(final Context context){
        if(INSTANCE==null){
            synchronized (MyDatabase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),MyDatabase.class, "app_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriter.execute(() -> {
                AlbumDao alDao = INSTANCE.albumDao();
                Album album=new Album("Title","url",5,2021);
                alDao.insert(album);
                alDao.update(album.getAlbumId(),album.getTitle(),album.getAlbumImageUrl(),album.getRating(),album.getYear());
                alDao.delete(album);

                TrackDao trDao = INSTANCE.trackDao();
                Track track = new Track("Tname","URL" , "10min" , "JO Bono",1);
                trDao.insert(track);
                trDao.update(track.getTrackId(),track.getTrackName(),track.getTrackImageUrl(),track.getDuration(),track.getSingers());
                trDao.delete(track);

            });
        }
    };
}