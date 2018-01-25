package com.example.diptapaul.learningsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class EnglishActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTab;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);

        setTitle("English Version");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTab = (TabLayout) findViewById(R.id.tabenglish);
        mViewPager = (ViewPager) findViewById(R.id.container);
        setUpViewPager(mViewPager);
        mTab.setupWithViewPager(mViewPager);

    }

    void setUpViewPager (ViewPager vp){
        ViewPagerAdapter vpa = new ViewPagerAdapter(getSupportFragmentManager());

        vpa.addMyFragment(new FragmentProgramming(), "Programming");
        vpa.addMyFragment(new FragmentWeb(), "Web Programming");
        vpa.addMyFragment(new FragmentAlgorithm(), "Algorithm");
        vpa.addMyFragment(new FragmentDatabase(), "Database");
        vpa.addMyFragment(new FragmentAndroid(), "Android Studio");
        vpa.addMyFragment(new FragmentPhotoshop(), "Photoshop");

        vp.setAdapter(vpa);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragment = new ArrayList<Fragment>();
        private List<String> title = new ArrayList<String>();

        public ViewPagerAdapter (FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragment.get(position);
        }

        @Override
        public int getCount() {
            return title.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position);
        }

        void addMyFragment(Fragment f, String t) {
            fragment.add(f);
            title.add(t);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_english, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings1) {
            AlertDialog.Builder mybuilder = new AlertDialog.Builder(EnglishActivity.this);
            mybuilder.setTitle("Logging Out");
            mybuilder.setMessage("Do You Want To Log Out ?");
            mybuilder.setIcon(R.drawable.logalert);

            mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(EnglishActivity.this, "Logging Out...", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(EnglishActivity.this, MainActivity.class));
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
        else if(id == R.id.exit1) {
            AlertDialog.Builder mybuilder = new AlertDialog.Builder(EnglishActivity.this);
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
