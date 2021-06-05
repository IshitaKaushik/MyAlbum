package com.example.myalbum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class EditTrackActivity extends AppCompatActivity {
    EditText trackTitle,trackDuration,trackSinger;
    ImageView trackImage;
    Uri imageUri;
    String z;
    String trackId;
    String parentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_track);
        trackTitle=findViewById(R.id.edittrack_title);
        trackDuration=findViewById(R.id.edit_duration);
        trackSinger=findViewById(R.id.edittrack_singer);
        trackImage=findViewById(R.id.edittrackimage);

        Intent intent=getIntent();
        String[] detailsToEdit = intent.getStringArrayExtra("trackEdit");
        if(detailsToEdit != null){
           z=detailsToEdit[1];
            Picasso.get().load(Uri.parse(z)).placeholder(R.drawable.music).into(trackImage);
            trackTitle.setText(detailsToEdit[0]);
           trackSinger.setText(detailsToEdit[3]);
           trackDuration.setText(detailsToEdit[2]);
           trackId=detailsToEdit[4];
           parentId=detailsToEdit[5];
        }

        trackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser("image/*",1);
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

                Picasso.get().load(imageUri).placeholder(R.drawable.music).into(trackImage);
            }
        }
    }

    public void editTrack(View view){
        Intent intent=new Intent(getApplicationContext(),TrackActivity.class);
        String s[]=new String[6];
        s[0]=trackTitle.getText().toString();
        if(imageUri != null)
            s[1]=imageUri.toString();
        else
            s[1]=z;
        s[2]=trackDuration.getText().toString();
        s[3]=trackSinger.getText().toString();
        s[4]=trackId;
        s[5]=parentId;
        intent.putExtra("editedTrack",s);
        startActivity(intent);
    }
}