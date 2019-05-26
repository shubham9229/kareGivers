package com.example.karegivers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class DetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final int CHOOSE_IMAGE =101 ;
    ImageView profilepic;
    EditText username;
    EditText mobilenumber;
    Uri uriProfileImage;
    ProgressBar progressBar;
    String profileImageUrl;
    StorageReference profileImageRefernce;
    FirebaseAuth mAuth1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailsactivity);
        Spinner spinner=findViewById(R.id.localitySpinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Localities,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        profilepic=(ImageView)findViewById(R.id.profilepicimageview);
        username=findViewById(R.id.usernameedit);
        mobilenumber=findViewById(R.id.mobilenoedit);
        progressBar=findViewById(R.id.progress_profilepic);
        mAuth1=FirebaseAuth.getInstance();


        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });

        findViewById(R.id.saveinfobutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     saveUserInfo();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth1.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this,signInActivity.class));
        }
    }



    private void saveUserInfo() {

        String displayName=username.getText().toString();
        String displayNumber=mobilenumber.getText().toString();


        if(displayName.isEmpty())
        {
            username.setError("Name Required");
            username.requestFocus();
            return;
        }

        if(displayNumber.isEmpty())
        {
            mobilenumber.setError("Number Required");
            mobilenumber.requestFocus();
            return;
        }

        FirebaseUser user=mAuth1.getCurrentUser();
        if(user!=null)
        {
            UserProfileChangeRequest profile =new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                              Toast.makeText(DetailsActivity.this,"Profile Updated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text=adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData()!=null){
           uriProfileImage= data.getData();

            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uriProfileImage);
                profilepic.setImageBitmap(bitmap);

                uploadImageToFirebaseStorage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void uploadImageToFirebaseStorage()
    {
        profileImageRefernce =
                FirebaseStorage.getInstance().getReference("profilepics/"+System.currentTimeMillis()+".jpg");

        if(uriProfileImage != null)
        {
            progressBar.setVisibility(View.VISIBLE);
            profileImageRefernce.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);

                            profileImageUrl=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(DetailsActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private  void showImageChooser()
    {
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Image"),CHOOSE_IMAGE);
    }
}
