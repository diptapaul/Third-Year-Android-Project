package com.example.diptapaul.learningsystem;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Dipta Paul on 5/20/2017.
 */
public class CustomListColor extends BaseAdapter {

    Context ct;
    private static LayoutInflater inflater = null;
    TextView tv;

    public CustomListColor(Context eng) {
        inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View myView;

        myView = inflater.inflate(R.layout.listview1, null);
        tv = (TextView) myView.findViewById(R.id.t5);

        tv.setTextColor(Color.WHITE);

        return myView;
    }
}
