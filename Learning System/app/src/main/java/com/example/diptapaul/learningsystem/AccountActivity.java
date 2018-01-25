package com.example.diptapaul.learningsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class AccountActivity extends AppCompatActivity {
    TextView tv1, tv2, tv3;
    ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        tv1 = (TextView) findViewById(R.id.t9);
        tv2 = (TextView) findViewById(R.id.t10);
        tv3 = (TextView) findViewById(R.id.t11);
        im = (ImageView) findViewById(R.id.im3);

        tv1.setText("Name: " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        tv2.setText("Email: " + FirebaseAuth.getInstance().getCurrentUser().getEmail());
        Picasso.with(getApplicationContext()).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).resize(750, 500)
                .centerCrop().into(im);
    }
}
