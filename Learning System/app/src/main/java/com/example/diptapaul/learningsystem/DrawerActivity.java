package com.example.diptapaul.learningsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.utilities.Base64;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView tt1, tt2;
    ImageView im;
    Button b1, b2, b3, b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        View view = nav.getHeaderView(0);
        tt1 = (TextView) view.findViewById(R.id.t2);
        tt2 = (TextView) view.findViewById(R.id.t3);
        im = (ImageView) view.findViewById(R.id.im2);
        tt1.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        tt2.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        Picasso.with(getApplicationContext()).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).resize(200, 200)
                .centerCrop().into(im);
        b1 = (Button) findViewById(R.id.d1);
        b2 = (Button) findViewById(R.id.d2);
        b3 = (Button) findViewById(R.id.d3);
        b4 = (Button) findViewById(R.id.d4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DrawerActivity.this, BanglaActivity.class));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DrawerActivity.this, EnglishActivity.class));
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DrawerActivity.this, ForumActivity.class));
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DrawerActivity.this, RanklistActivity.class));
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (getSupportFragmentManager().findFragmentByTag("AccountFragment") != null) {
                getSupportFragmentManager().popBackStackImmediate("AccountFragment", 0);
            } else {
                AlertDialog.Builder mybuilder = new AlertDialog.Builder(DrawerActivity.this);
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
        }
    }

    @Override
    protected void onDestroy() {
        Process.killProcess(Process.SIGNAL_QUIT);
       // Process.killProcess(Process.myPid());
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder mybuilder = new AlertDialog.Builder(DrawerActivity.this);
            mybuilder.setTitle("Logging Out");
            mybuilder.setMessage("Do You Want To Log Out ?");
            mybuilder.setIcon(R.drawable.logalert);

            mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(DrawerActivity.this, "Logging Out...", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(DrawerActivity.this, MainActivity.class));
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
        else if(id == R.id.exit) {
            AlertDialog.Builder mybuilder = new AlertDialog.Builder(DrawerActivity.this);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.account) {
            startActivity(new Intent(DrawerActivity.this, AccountActivity.class));
        } else if (id == R.id.coin) {

        } else if (id == R.id.update) {
            startActivity(new Intent(DrawerActivity.this, UpdateActivity.class));
        } else if (id == R.id.forum) {

        } else if (id == R.id.about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
