package com.example.diptapaul.learningsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class UpdateActivity extends AppCompatActivity {
    Button b1, b2, b3;
    EditText e1, e2, e3;
    ImageButton im1;
    Uri resultUri;
    private Firebase mDatabase;
    private FirebaseAuth mAuth;
    private ProgressDialog mPro;
    private StorageReference mSto;
    private FirebaseStorage mStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        mDatabase = new Firebase("https://learning-system-74e8a.firebaseio.com/Users");
        mStorage = FirebaseStorage.getInstance();
        mSto = mStorage.getReference();
        b1 = (Button) findViewById(R.id.b12);
        b2 = (Button) findViewById(R.id.b13);
        e1 = (EditText) findViewById(R.id.et12);
        e2 = (EditText) findViewById(R.id.et13);
        e3 = (EditText) findViewById(R.id.et14);
        mPro = new ProgressDialog(this);
        im1 = (ImageButton) findViewById(R.id.im6);
        b3 = (Button) findViewById(R.id.b15);

        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/+");
                startActivityForResult(intent, 2);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(resultUri == null) {
                    Toast.makeText(getApplicationContext(), "Upload Your Image", Toast.LENGTH_SHORT).show();
                }
                else {
                    mPro.setMessage("Image is Uploading...");
                    mPro.show();
                    mSto.child(resultUri.getLastPathSegment()).putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            final String id = taskSnapshot.getDownloadUrl().toString();
                            mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Image").setValue(id);

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(taskSnapshot.getDownloadUrl())
                                    .build();

                            user.updateProfile(profileUpdates);
                        }
                    });
                    mPro.dismiss();
                    Toast.makeText(getApplicationContext(), "Image Updated...", Toast.LENGTH_SHORT);
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = e1.getText().toString();
                if(name.isEmpty()) {
                    e1.setError("Please Enter a Name");
                }
                else {
                    mPro.setMessage("Name is Updating...");
                    mPro.show();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Name Updated...", Toast.LENGTH_SHORT).show();
                                        e1.setText(null);
                                        mPro.dismiss();
                                    }
                                }
                            });
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String pass = e2.getText().toString();
                final String pass2 = e3.getText().toString();

                if(pass.isEmpty()) {
                    e2.setError("Please Enter Your Old Password");
                }
                else if(pass.length() < 8) {
                    e2.setError("Password should be 8 Characters long");
                }
                else if(pass2.isEmpty()) {
                    e3.setError("Please Enter Your New Password");
                }
                else if(pass2.length() < 8) {
                    e3.setError("Password should be 8 Characters long");
                }
                else {
                    mPro.setMessage("Password is Updating...");
                    mPro.show();
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    AuthCredential credential = EmailAuthProvider
                            .getCredential(user.getEmail(), pass);

                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        user.updatePassword(pass2)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), "Password Updated...", Toast.LENGTH_SHORT).show();
                                                            e2.setText(null);
                                                            e3.setText(null);
                                                            mPro.dismiss();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Old Password is Wrong...", Toast.LENGTH_SHORT).show();
                                        mPro.dismiss();
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(16, 11)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                Picasso.with(getApplicationContext())
                        .load(resultUri)
                        .resize(750, 500)
                        .centerCrop()
                        .into(im1);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UpdateActivity.this, DrawerActivity.class));
    }
}
