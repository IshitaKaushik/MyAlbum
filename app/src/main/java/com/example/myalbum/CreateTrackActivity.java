package com.example.myalbum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class CreateTrackActivity extends AppCompatActivity {

    ImageView trackImage;
    EditText trackTitle,trackSinger , trackDuration;
    String albumId;
    Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_track);
        trackImage=findViewById(R.id.create_track_image);
        trackTitle=findViewById(R.id.create_track_title);
        trackDuration=findViewById(R.id.edit_duration1);
        trackSinger=findViewById(R.id.create_track_singer);
        trackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser("image/*",1);
            }
        });

        Intent intent=getIntent();
        albumId= intent.getStringExtra("albumThisId");
    }

    private void openImageChooser(String type, int Rcode) {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType(type);
        startActivityForResult(i, Rcode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                mImageUri = data.getData();
                Picasso.get().load(mImageUri).placeholder(R.drawable.music).into(trackImage);
            }
        }
        else {
            Toast.makeText(this, "image not selected", Toast.LENGTH_LONG).show();
        }
    }

    public void createNewTrack(View view){
        if(trackTitle.getText().toString().isEmpty()){
            Toast.makeText(this,"Enter Track Name",Toast.LENGTH_SHORT).show();
        }

        else if(trackDuration.getText().toString().isEmpty()){
            Toast.makeText(this,"Enter Duration",Toast.LENGTH_SHORT).show();
        }
        else if( ! trackDuration.getText().toString().contains(":")) {
            Toast.makeText(this,"Enter format = 00 : 00",Toast.LENGTH_SHORT).show();
        }
        else if(trackSinger.getText().toString().isEmpty()){
            Toast.makeText(this,"Enter Singer Name",Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(getApplicationContext(), TrackActivity.class);
            String[] s=new String[5];
            s[0]=trackTitle.getText().toString();
            if(mImageUri != null)
            s[1]=mImageUri.toString();
            else
                s[1]="https://firebasestorage.googleapis.com/v0/b/myalbum-527fb.appspot.com/o/backevent.jpg?alt=media&token=ead331e7-e15e-4009-a6a9-f614e1879a80";
            s[2]=trackDuration.getText().toString();
            s[3]=trackSinger.getText().toString();
            s[4]=albumId;
            intent.putExtra("trackDetails",s);
            startActivity(intent);
        }

    }
}