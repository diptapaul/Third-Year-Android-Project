package com.example.diptapaul.learningsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

public class ChapActivity extends AppCompatActivity {

    Firebase myDatabase;
    TextView myText;
    Button myButton;
    String chapterName, activityName, nextChapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chap);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Intent it = this.getIntent();
        chapterName = this.getIntent().getStringExtra("chapter");
        setTitle(chapterName);
        activityName = this.getIntent().getStringExtra("activity");
        nextChapter = this.getIntent().getStringExtra("nextchapter");

        myButton = (Button) findViewById(R.id.b5);
        myText = (TextView) findViewById(R.id.t5);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChapActivity.this, QuestionActivity.class);
                intent.putExtra("nextchapter", nextChapter);
                intent.putExtra("chapter", chapterName);
                intent.putExtra("activity", activityName);
                intent.putExtra("status", it.getIntExtra("status", 1));
                intent.putExtra("position", it.getIntExtra("status", 1));
                startActivity(intent);
            }
        });



        myDatabase = new Firebase("https://learning-system-74e8a.firebaseio.com/"+this.getIntent().getStringExtra("activity"));

        myDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getKey();
                if(value.equals(chapterName)) {
                    myText.setText(dataSnapshot.getValue(String.class));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

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
            AlertDialog.Builder mybuilder = new AlertDialog.Builder(ChapActivity.this);
            mybuilder.setTitle("Logging Out");
            mybuilder.setMessage("Do You Want To Log Out ?");
            mybuilder.setIcon(R.drawable.logalert);

            mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(ChapActivity.this, "Logging Out...", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(ChapActivity.this, MainActivity.class));
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
            AlertDialog.Builder mybuilder = new AlertDialog.Builder(ChapActivity.this);
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
}
