package com.example.diptapaul.learningsystem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {
    Toolbar tool;
    EditText editText;
    Button button, button1;
    String email;
    ProgressDialog mPro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        button = (Button) findViewById(R.id.b8);
        editText = (EditText) findViewById(R.id.et8);
        button1 = (Button) findViewById(R.id.b9);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PasswordActivity.this, SigninActivity.class));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editText.getText().toString();
                if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editText.setError("Please Enter a Valid Email Address");
                }
                else {
                    mPro.setMessage("Password Reset Processing...");
                    mPro.show();
                    Firebase firebase = new Firebase("https://learning-system-74e8a.firebaseio.com/Emails");
                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                Log.v("E_Value", snapshot.getValue().toString());
                                String st = snapshot.getValue().toString();
                                if(st.equals(email)) {
                                    FirebaseAuth auth = FirebaseAuth.getInstance();

                                    auth.sendPasswordResetEmail(email)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        mPro.dismiss();
                                                        final AlertDialog.Builder alert = new AlertDialog.Builder(PasswordActivity.this);
                                                        alert.setTitle("Reset Password");
                                                        alert.setMessage("A reset password email is sent to you. Go to the mail to reset the password.");
                                                        alert.setIcon(R.drawable.resetpassword);
                                                        alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                dialogInterface.dismiss();
                                                            }
                                                        });
                                                        editText.setText(null);
                                                        alert.show();

                                                    }
                                                    else {
                                                        mPro.dismiss();
                                                        Toast.makeText(getApplicationContext(), "Email Not Exists", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }
                            mPro.dismiss();
                            Toast.makeText(getApplicationContext(), "This is not an Registered Email.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            }
        });
    }

}
