package com.example.diptapaul.learningsystem;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Dipta Paul on 5/20/2017.
 */
public class MyCustomAdapterforTutorial extends BaseAdapter {

    String [] name;
    Context ct;
    String program;
    private static LayoutInflater inflater = null;

    public MyCustomAdapterforTutorial(Context eng, String[] nameofitem, String myProgram) {
        name = nameofitem;
        program = myProgram;
        ct = eng;
        inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class MyHolder {
        TextView tv;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final String nam = name[i];
        final MyHolder myh = new MyHolder();
        View myView;

        myView = inflater.inflate(R.layout.listview1, null);
        myh.tv = (TextView) myView.findViewById(R.id.t5);

        Firebase myDatabase = new Firebase("https://learning-system-74e8a.firebaseio.com/"+ FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + program +" ListView");
        myDatabase.child(name[0]).setValue("complete");

        myDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String st = dataSnapshot.getKey();
                if(nam.equals(st)) {
                    myh.tv.setCompoundDrawables(null, null, null, null);
                    myh.tv.setTextColor(Color.BLACK);
                    myh.tv.setBackgroundColor(Color.WHITE);
                    myh.tv.setText(nam);
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

        return myView;
    }
}
