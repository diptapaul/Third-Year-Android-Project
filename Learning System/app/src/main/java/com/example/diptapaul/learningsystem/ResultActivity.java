package com.example.diptapaul.learningsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.Transaction;
import com.google.firebase.auth.FirebaseAuth;

public class ResultActivity extends AppCompatActivity {

    TextView myResult, myBack;
    Button back;
    String myactivity, mychapter, mynextchapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Result");
        final Intent it = this.getIntent();
        myactivity = this.getIntent().getStringExtra("activity");
        mychapter = this.getIntent().getStringExtra("chapter");
        mynextchapter = this.getIntent().getStringExtra("nextchapter");
        myResult = (TextView) findViewById(R.id.res);
        myBack = (TextView) findViewById(R.id.t15);
        back = (Button) findViewById(R.id.b16);
        myResult.setText("Correct Answer : " + this.getIntent().getStringExtra("result") + "\nWrong Answer : " + Integer.toString(5-
                Integer.parseInt(this.getIntent().getStringExtra("result"))));

        if(this.getIntent().getStringExtra("result").equals("5")) {
            myBack.setText("Congratulation!\nYou have just unlock next chapter.");
        }
        else {
            myBack.setText("Sorry!\nYou have to give all the answers correctly to unlock the next chapter.");
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, TutorialActivity.class);
                intent.putExtra("activity", myactivity);
                intent.putExtra("status", it.getIntExtra("status", 1));
                intent.putExtra("position", it.getIntExtra("status", 1));
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chapter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings3) {
            AlertDialog.Builder mybuilder = new AlertDialog.Builder(ResultActivity.this);
            mybuilder.setTitle("Logging Out");
            mybuilder.setMessage("Do You Want To Log Out ?");
            mybuilder.setIcon(R.drawable.logalert);

            mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(ResultActivity.this, "Logging Out...", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(ResultActivity.this, MainActivity.class));
                }
            });
            mybuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog myDialog = mybuilder.create();
            myDialog.show();
        }
        else if(id == R.id.exit3) {
            AlertDialog.Builder mybuilder = new AlertDialog.Builder(ResultActivity.this);
            mybuilder.setTitle("Exit Learning System");
            mybuilder.setMessage("Do You Want To Exit ?");
            mybuilder.setIcon(R.drawable.exitt);

            mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finishAffinity();
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                }
            });
            mybuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog myDialog = mybuilder.create();
            myDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ResultActivity.this, TutorialActivity.class);
        intent.putExtra("activity", myactivity);
        intent.putExtra("status", this.getIntent().getIntExtra("status", 1));
        intent.putExtra("position", this.getIntent().getIntExtra("status", 1));
    }
}
