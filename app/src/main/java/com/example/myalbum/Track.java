package com.example.myalbum;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

@Entity(tableName = "track_table")


public class Track {
        @PrimaryKey(autoGenerate = true)private int trackId;

        @NonNull
        private@ColumnInfo(name = "AlbumId")int parentId;
        @NonNull
        private@ColumnInfo(name = "TrackName")String trackName;
        @NonNull
        private@ColumnInfo(name = "TrackImageURL")String trackImageUrl;
        @NonNull
        private@ColumnInfo(name = "Duration")String duration;
        @NonNull
        private@ColumnInfo(name = "Singers")String singers;


        public Track(String trackName,String trackImageUrl,String duration, String singers,int parentId){
                this.trackName=trackName;
                this.trackImageUrl=trackImageUrl;
                this.duration=duration;
                this.singers=singers;
                this.parentId=parentId;
        }

        public String getTrackName() {
                return this.trackName;
        }
        public String getTrackImageUrl() {
                return this.trackImageUrl;
        }
        public String getDuration() {
                return this.duration;
        }
        public String getSingers() {
                return this.singers;
        }

        public int getTrackId(){
                return trackId;
        }
        public void setTrackId(int trackId){
                this.trackId=trackId;
        }

        public int getParentId() {
                return parentId;
        }

}
