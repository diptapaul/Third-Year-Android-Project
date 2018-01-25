package com.example.diptapaul.learningsystem;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class QuestionActivity extends AppCompatActivity {

    String myActivity, myChapter, nextChapter;
    Firebase myQuestion;
    TextView myQues, myHead;
    String myKey;
    Map <String, String> map;
    Button myButton;
    int myQuesNumber = 1;
    int myResult = 0;
    EditText myEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myActivity = this.getIntent().getStringExtra("activity");
        myChapter = this.getIntent().getStringExtra("chapter");
        nextChapter = this.getIntent().getStringExtra("nextchapter");
        final Intent it = this.getIntent();
        myQues = (TextView) findViewById(R.id.ques);
        myHead = (TextView) findViewById(R.id.head);
        myButton = (Button) findViewById(R.id.b7);
        myEditText = (EditText) findViewById(R.id.et7);
        myHead.setText("Question No. 1");
        myQuestion = new Firebase("https://learning-system-74e8a.firebaseio.com/C Programming Questions/"+myChapter);
        myResult = 0;
        myQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                map = dataSnapshot.getValue(Map.class);
                myQues.setText(map.get("Ques1"));

                setTitle("Quiz");

                myButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String st = myEditText.getText().toString();
                        if(myQuesNumber == 5)  {
                            if(map.get("Ans"+Integer.toString(myQuesNumber)).equals(st)) {
                                myResult++;
                            }
                            Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
                            intent.putExtra("result", Integer.toString(myResult));
                            intent.putExtra("activity", myActivity);
                            intent.putExtra("chapter", myChapter);
                            intent.putExtra("nextchapter", nextChapter);
                            intent.putExtra("status", it.getIntExtra("status", 1));
                            intent.putExtra("position", it.getIntExtra("status", 1));
                            startActivity(intent);
                        }
                        else if(st.isEmpty()) {
                            myEditText.setError("Give an Answer");
                        }
                        else if(st.length() > 1 || !(st.charAt(0) >= 'A' && st.charAt(0) <= 'D')) {
                            myEditText.setError("Enter an Valid Answer.(Ex. A or B)");
                        }
                        else {
                            if(map.get("Ans"+Integer.toString(myQuesNumber)).equals(st)) {
                                myResult++;
                            }
                            myQuesNumber++;
                            myEditText.setText(null);
                            hideSoftKeyboard(QuestionActivity.this);
                            myQues.setText(map.get("Ques"+Integer.toString(myQuesNumber)));
                            myHead.setText("Question No. " + Integer.toString(myQuesNumber));
                        }
                    }
                });
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
            AlertDialog.Builder mybuilder = new AlertDialog.Builder(QuestionActivity.this);
            mybuilder.setTitle("Logging Out");
            mybuilder.setMessage("Do You Want To Log Out ?");
            mybuilder.setIcon(R.drawable.logalert);

            mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(QuestionActivity.this, "Logging Out...", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(QuestionActivity.this, MainActivity.class));
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
            AlertDialog.Builder mybuilder = new AlertDialog.Builder(QuestionActivity.this);
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

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

}
