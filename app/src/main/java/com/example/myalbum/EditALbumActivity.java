package com.example.myalbum;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.squareup.picasso.Picasso;

import java.io.File;

public class EditALbumActivity extends AppCompatActivity {
    ImageView editImage;
    EditText editTitle,editYear;
    RatingBar editRating;
    Uri imageUri;
    String url;
    String albumId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_a_lbum);
        editImage = findViewById(R.id.editalbum_image);
       editTitle=findViewById(R.id.album_title);
       editYear=findViewById(R.id.editalbum_year);
       editRating=findViewById(R.id.editalbum_rating);
        Intent intent=getIntent();
        String[] detailsToEdit = intent.getStringArrayExtra("albumEdit");
        if(detailsToEdit != null){
            Picasso.get().load(Uri.parse(detailsToEdit[1])).placeholder(R.drawable.album).into(editImage);
            url=detailsToEdit[1];
            editTitle.setText(detailsToEdit[0]);
            editYear.setText(detailsToEdit[3]);
            editRating.setRating(Integer.parseInt(detailsToEdit[2]));
            albumId=detailsToEdit[4];
        }

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser("image/*",1);
            }
        });

        editRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                AlertDialog.Builder abuilder=new AlertDialog.Builder(EditALbumActivity.this);
                LayoutInflater inflater = EditALbumActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.smiley_rating, null);
                abuilder.setView(dialogView);

                Smiley smiley=dialogView.findViewById(R.id.faceView);

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







    }

    private void openImageChooser(String type, int Rcode) {
        Intent intent = new Intent();
        intent.setType(type);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select a picture"),Rcode);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                imageUri = data.getData();
                Picasso.get().load(imageUri).placeholder(R.drawable.album).into(editImage);
            }
        }
    }
    public void editAlbum(View view){
        Intent intent=new Intent(this,MainActivity.class);
        String s[]=new String[5];
        s[0]=editTitle.getText().toString();
        if(imageUri != null)
        s[1]=imageUri.toString();
        else
            s[1]=url;
        s[2]=(int)editRating.getRating()+"";
        s[3]=editYear.getText().toString();
        s[4]=albumId;
        intent.putExtra("editedAlbum",s);
        startActivity(intent);
    }
}