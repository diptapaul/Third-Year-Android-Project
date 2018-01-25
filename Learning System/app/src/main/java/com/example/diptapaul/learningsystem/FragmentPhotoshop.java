package com.example.diptapaul.learningsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * Created by Dipta Paul on 5/20/2017.
 */
public class FragmentPhotoshop extends Fragment {
    View v;
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_english, container, false);

        lv = (ListView) v.findViewById(R.id.list1);

        String listItem[] = {"Photoshop Basic", "Photoshop Advance"};
        String listItem1[] = {"ফটোশপ বেসিক","এডভান্সড ফটোশপ"};
        int icon[] = {R.drawable.finalphotoshopbasic, R.drawable.finalphotoshopadvance};

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
                intent.putExtra("status", 5);
                intent.putExtra("activity", getActivity().getClass().getSimpleName());
                startActivity(intent);
            }
        });

        return v;
    }
}
