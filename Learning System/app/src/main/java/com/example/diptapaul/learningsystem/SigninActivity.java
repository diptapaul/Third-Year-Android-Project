package com.example.diptapaul.learningsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SigninActivity extends AppCompatActivity {
    Button b1, b2;
    EditText e1, e2;
    Firebase mData;
    FirebaseAuth mAuth;
    ProgressDialog mPro;
    LinearLayout la;
    int i;
    boolean b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();
        mData = new Firebase("https://learning-system-74e8a.firebaseio.com/Users");
        mPro = new ProgressDialog(this);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        e1 = (EditText) findViewById(R.id.et1);
        e2 = (EditText) findViewById(R.id.et2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninActivity.this, PasswordActivity.class));
            }
        });
    }

    private void signIn() {
        final String email = e1.getText().toString();
        final String pass = e2.getText().toString();
        if (email.isEmpty()) {
            e1.setError("Enter your Email");
        } else if (pass.isEmpty()) {
            e2.setError("Enter Password");
        } else {
            mPro.setMessage("Signing In...");
            mPro.show();
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(SigninActivity.this, DrawerActivity.class);
                        intent.putExtra("name", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Login Sucessful", Toast.LENGTH_SHORT).show();
                        mPro.dismiss();
                    } else {
                        if(!isOnline()) {
                            Toast.makeText(getApplicationContext(), "You are offline...", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            e2.setText(null);
                            Toast.makeText(getApplicationContext(), "Wrong Login Information...", Toast.LENGTH_SHORT).show();
                        }

                        mPro.dismiss();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SigninActivity.this, MainActivity.class));
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
