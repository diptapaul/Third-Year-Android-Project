package com.example.diptapaul.learningsystem;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Dipta Paul on 5/20/2017.
 */
public class MyCustomAdapter extends BaseAdapter {

    String [] name;
    int [] icon;
    Context ct;
    private static LayoutInflater inflater = null;

    public MyCustomAdapter(Context eng, String[] nameofitem, int[] pic) {
        name = nameofitem;
        ct = eng;
        icon = pic;
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        MyHolder myh = new MyHolder();
        View myView;

        myView = inflater.inflate(R.layout.listview, null);
        myh.tv = (TextView) myView.findViewById(R.id.t4);

        myh.tv.setText(name[i]);
        myh.tv.setCompoundDrawablesRelativeWithIntrinsicBounds(icon[i], 0, 0, 0);


        return myView;
    }
}
