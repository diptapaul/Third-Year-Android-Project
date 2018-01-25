package com.example.diptapaul.learningsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.List;


public class SignupActivity extends AppCompatActivity {
    private EditText e1, e2, e3, e4;
    private Button b1;
    private ImageButton iMage;
    private Firebase mDatabase;
    private FirebaseAuth mAuth;
    private ProgressDialog mPro;
    private StorageReference mSto;
    boolean b;
    private FirebaseStorage mStorage;
    private FirebaseAuth.AuthStateListener mAuthState;
    String imgid;
    private Uri resultUri = null;
    boolean check = false;
    Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mDatabase = new Firebase("https://learning-system-74e8a.firebaseio.com/Users");
        firebase = new Firebase("https://learning-system-74e8a.firebaseio.com/Emails");
        e1 = (EditText) findViewById(R.id.et3);
        e3 = (EditText) findViewById(R.id.et5);
        e4 = (EditText) findViewById(R.id.et6);
        b1 = (Button) findViewById(R.id.b3);
        iMage = (ImageButton) findViewById(R.id.im1);
        mAuth = FirebaseAuth.getInstance();
        mPro = new ProgressDialog(this);
        mStorage = FirebaseStorage.getInstance();
        mSto = mStorage.getReference();


        iMage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/+");
                startActivityForResult(intent, 2);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
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
                        .into(iMage);
                check = true;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }



    private void startRegister() {

        final String name = e1.getText().toString();
        final String email = e3.getText().toString();
        final String pass = e4.getText().toString();
        if(name.isEmpty()) {
            e1.setError("Please Enter a Name");
        }
        else if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            e3.setError("Please Enter a Valid Email Address");
        }
        else if(pass.isEmpty()) {
            e4.setError("Please Enter a Password");
        }
        else if(pass.length() < 8) {
            e4.setError("Password should have at least 8 characters");
        }
        else if(resultUri == null) {
            Toast.makeText(getApplicationContext(), "Upload Your Image", Toast.LENGTH_SHORT).show();
        }
        else {
            mPro.setMessage("Signing Up ...");
            mPro.show();
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String id1;
                        final String user_id = mAuth.getCurrentUser().getUid();
                        firebase.child(name).setValue(email);
                        mDatabase.child(user_id);
                        mDatabase.child(user_id).child("Name").setValue(name);
                        mSto.child(resultUri.getLastPathSegment()).putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                final String id = taskSnapshot.getDownloadUrl().toString();
                                //id1 = id;
                                mDatabase.child(user_id).child("Image").setValue(id);

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .setPhotoUri(taskSnapshot.getDownloadUrl())
                                        .build();

                                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            mPro.dismiss();
                                            Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SignupActivity.this, WelcomeActivity.class);
                                            intent.putExtra("name", name);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                        });
                    }
                    else {
                        if(isOnline()) {
                            Toast.makeText(getApplicationContext(), "This Email already in use. Change the Email...", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "You are offline...", Toast.LENGTH_SHORT).show();
                        }
                        mPro.dismiss();
                    }
                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignupActivity.this, MainActivity.class));
    }
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
