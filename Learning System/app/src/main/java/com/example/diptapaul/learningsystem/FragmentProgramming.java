package com.example.diptapaul.learningsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Dipta Paul on 5/20/2017.
 */
public class FragmentProgramming extends Fragment {
    View v;
    ListView lv;
    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_english, container, false);

        lv = (ListView) v.findViewById(R.id.list1);

        String listItem[] = {"C Programming", "C++ Programming","CS Programming", "Java Programming", "Python Programming", "Shell Programming"};
        String listItem1[] = {"সি প্রোগ্রামিং", "সি প্লাস প্লাস প্রোগ্রামিং", "সি শার্প প্রোগ্রামিং ", "জাভা প্রোগ্রামিং ", "পাইথন প্রোগ্রামিং ", "শেল প্রোগ্রামিং "};
        int icon[] = {R.drawable.finalc, R.drawable.cpppp,R.drawable.finalcs, R.drawable.finaljava, R.drawable.finalpython, R.drawable.finalbash};

        String st = getActivity().getClass().getSimpleName();
        if (st.equals("BanglaActivity")) {
            listItem = listItem1;
        }

        MyCustomAdapter adapter = new MyCustomAdapter(getActivity(), listItem, icon);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), TutorialActivity.class);
                intent.putExtra("position", i);
                intent.putExtra("status", (int)0);
                intent.putExtra("activity", getActivity().getClass().getSimpleName());
                startActivity(intent);
            }
        });

        return v;
    }
}
