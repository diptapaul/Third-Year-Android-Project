package com.example.diptapaul.learningsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class TutorialActivity extends AppCompatActivity {

    boolean bool;
    String programNameEnglish[][] = {{"C Programming", "C++ Programming","CS Programming", "Java Programming", "Python Programming", "Shell Programming"},
            {"HTML 5 Programming","CSS 3 Programming", "Java Script Programming", "JQuery Programming", "PHP Programming"
                    ,"XML Programming"}, {"Sorting Algorithm", "Searching Algorithm", "String Algorithm", "Graph Algorithm"},
            {"SQL", "MySQL", "SQLite", "Mongo DB"},{"Android Basics", "Android User Interface", "Android Advance Concepts"},
            {"Photoshop Basic", "Photoshop Advance"}};
    String programNameBangla[][] = {{"সি প্রোগ্রামিং", "সি প্লাস প্লাস প্রোগ্রামিং", "সি শার্প প্রোগ্রামিং ", "জাভা প্রোগ্রামিং ", "পাইথন প্রোগ্রামিং ", "শেল প্রোগ্রামিং "},
            {"এইচটিএমএল ৫ প্রোগ্রামিং", "সিএসএস ৩ প্রোগ্রামিং", "জাভাস্ক্রিপ্ট প্রোগ্রামিং", "জেকোয়েরি প্রোগ্রামিং", "পিএইচপি প্রোগ্রামিং", "এক্স এম এল প্রোগ্রামিং"},
            {"সর্টিং অ্যালগরিদম", "সার্চ অ্যালগরিদম", "স্ট্রিং অ্যালগরিদম", "গ্রাফ অ্যালগরিদম"},{"এসকিউয়েল","মাইএসকিউএল", "এসকিউলাইট্", "মংগোডিবি"},
            {"অ্যান্ড্রয়েড বেসিক","অ্যান্ড্রয়েড ইউজার ইন্টারফেস", "অ্যান্ড্রয়েড এডভান্সড"},{"ফটোশপ বেসিক","এডভান্সড ফটোশপ"}};

    String chapterNameEnglish[][] = {{"Intro to C", "If Statements", "Loops", "Switch case", "Pointers", "Structures", "Arrays"},
            {"It will be coming soon"},{"It will be coming soon"}, {"It will be coming soon"}, {"It will be coming soon"},{"It will be coming soon"} };
    String chapterNameBangla[][] = {{"শুরুর আগে", "প্রথম প্রোগ্রাম", "ডাটা টাইপ, ইনপুট ও আউটপুট", "কন্ডিশনাল লজিক", "লুপ",
            "অ্যারে", "ফাংশন", "স্ট্রিং"},{"It will be coming soon"},{"It will be coming soon"},{"It will be coming soon"},{"It will be coming soon"},{"It will be coming soon"}
            ,{"It will be coming soon"}};

    ListView lv;
    String program;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = this.getIntent();

        MyCustomAdapterforTutorial myCustomAdapterforTutorial;


        if(intent.getStringExtra("activity").equals("BanglaActivity")) {
            setTitle(programNameBangla[intent.getIntExtra("status", 1)][intent.getIntExtra("position", 1)]);
            myCustomAdapterforTutorial = new MyCustomAdapterforTutorial(this, chapterNameBangla[intent.getIntExtra("status", 1)],
                    programNameBangla[intent.getIntExtra("status", 1)][intent.getIntExtra("position", 1)]);
            program = programNameBangla[intent.getIntExtra("status", 1)][intent.getIntExtra("position", 1)];
        }
        else {
            setTitle(programNameEnglish[intent.getIntExtra("status", 1)][intent.getIntExtra("position", 1)]);
            myCustomAdapterforTutorial = new MyCustomAdapterforTutorial(this, chapterNameEnglish[intent.getIntExtra("status", 1)]
                    ,programNameEnglish[intent.getIntExtra("status", 1)][intent.getIntExtra("position", 1)]);
            program = programNameEnglish[intent.getIntExtra("status", 1)][intent.getIntExtra("position", 1)];
        }

        lv = (ListView) findViewById(R.id.list2);


        lv.setAdapter(myCustomAdapterforTutorial);

        final Firebase myDatabase = new Firebase("https://learning-system-74e8a.firebaseio.com/"+ FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + program +" ListView");


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Intent intent1 = new Intent(TutorialActivity.this, ChapActivity.class);
                if(intent.getStringExtra("activity").equals("BanglaActivity")) {
                    intent1.putExtra("chapter", chapterNameBangla[intent.getIntExtra("status", 1)][i]);
                    intent1.putExtra("nextchapter", chapterNameBangla[intent.getIntExtra("status", 1)][i+1]);
                    intent1.putExtra("activity",programNameBangla[intent.getIntExtra("status", 1)][intent.getIntExtra("position", 1)]);
                }
                else {
                    intent1.putExtra("chapter", chapterNameEnglish[intent.getIntExtra("status", 1)][i]);
                    intent1.putExtra("nextchapter", chapterNameEnglish[intent.getIntExtra("status", 1)][i+1]);
                    intent1.putExtra("activity",programNameEnglish[intent.getIntExtra("status", 1)][intent.getIntExtra("position", 1)]);
                }
                bool = false;
                myDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            if(intent1.getStringExtra("chapter").equals(snapshot.getKey())) {
                                bool=true;
                                startActivity(intent1);
                            }
                        }
                        if(!bool) {
                            AlertDialog.Builder alt = new AlertDialog.Builder(TutorialActivity.this);
                            alt.setTitle("Unlock Chapter");
                            alt.setIcon(R.drawable.finalkey);
                            alt.setMessage("This Chapter is Locked. To Unlock this Chapter You have to Complete the Previous Chapter Quiz.");
                            alt.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            alt.create().show();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutorial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings2) {
            AlertDialog.Builder mybuilder = new AlertDialog.Builder(TutorialActivity.this);
            mybuilder.setTitle("Logging Out");
            mybuilder.setMessage("Do You Want To Log Out ?");
            mybuilder.setIcon(R.drawable.logalert);

            mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(TutorialActivity.this, "Logging Out...", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(TutorialActivity.this, MainActivity.class));
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
        else if(id == R.id.exit2) {
            AlertDialog.Builder mybuilder = new AlertDialog.Builder(TutorialActivity.this);
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
