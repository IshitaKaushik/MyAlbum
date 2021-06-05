package com.example.myalbum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import okhttp3.internal.Util;

public class CreateAlbumActivity extends AppCompatActivity {
    ImageView albumImage;
    EditText albumTitle,albumYear;
    RatingBar albumRating;
    Uri mImageUri;
    Smiley smiley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_album);
        albumImage=findViewById(R.id.createalbum_image);
        albumTitle=findViewById(R.id.create_album_title);
        albumYear = findViewById(R.id.create_album_year);
        albumRating = findViewById(R.id.create_album_rating);
        albumImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser("image/*",1);
            }
        });

       albumRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                AlertDialog.Builder abuilder=new AlertDialog.Builder(CreateAlbumActivity.this);
                LayoutInflater inflater = CreateAlbumActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.smiley_rating, null);
                abuilder.setView(dialogView);

              smiley=dialogView.findViewById(R.id.faceView);

                String firstcolor="#FFF59D";
                String secondcolor="#ffff0000";

                //if rating is higher than 3 display a happy face
                //otherwise draw a sad face
                if(rating>3) {
                    smiley.setBackgroundColor(Color.parseColor(firstcolor));
                }else if(rating<3){
                    smiley.sadFace();
                    smiley.setBackgroundColor(Color.parseColor(secondcolor));
                }
                else{
                    smiley.setBackgroundColor(Color.parseColor("#FF9FA8DA"));
                }

                abuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                abuilder.show();
            }

        });
        View backgroundimage = findViewById(R.id.background);
        if(backgroundimage != null) {
            Drawable background = backgroundimage.getBackground();
            if(background != null)
            background.setAlpha(80);
        }


    }



    private void openImageChooser(String type, int Rcode) {
        Intent intent = new Intent();
        intent.setType(type);
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "select a picture"),Rcode);
    }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1) {
                if (resultCode == RESULT_OK) {
                    mImageUri = data.getData();
                    Picasso.get().load(mImageUri).placeholder(R.drawable.album).into(albumImage);
                }
            }
        }


    public void createNewAlbum(View view){
        if(albumTitle.getText().toString().isEmpty()){
            Toast.makeText(this,"Enter ALbum Name",Toast.LENGTH_SHORT).show();
        }
        else if(albumYear.getText().toString().isEmpty()){
            Toast.makeText(this,"Enter Release Year",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            String[] s=new String[4];
            s[0]=albumTitle.getText().toString();
            if(mImageUri != null){
                s[1]=mImageUri.toString();
            }
            else{
                s[1]="https://firebasestorage.googleapis.com/v0/b/myalbum-527fb.appspot.com/o/backevent.jpg?alt=media&token=ead331e7-e15e-4009-a6a9-f614e1879a80";
            }

            s[2]=albumYear.getText().toString();
            s[3]=(int)albumRating.getRating() +"";
            intent.putExtra("albumDetails",s);
            startActivity(intent);
        }
    }



}