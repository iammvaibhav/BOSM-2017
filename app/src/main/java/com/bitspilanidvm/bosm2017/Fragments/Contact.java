package com.bitspilanidvm.bosm2017.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bitspilanidvm.bosm2017.Activity.Main;
import com.bitspilanidvm.bosm2017.Adapters.ContactAdapter;
import com.bitspilanidvm.bosm2017.Modals.Contacts;
import com.bitspilanidvm.bosm2017.R;

import java.util.ArrayList;
import java.util.List;


public class Contact extends Fragment {

    String names[] = {"ABC", "PQR", "PQS", "XYZ", "PST", "XYZ"};
    String dept_info[] = {"ABC", "PQR", "PQS", "XYZ", "PST", "XYZ"};
    String numbers[] = {"9432567654", "9432567654", "9432567654", "9432567654", "9432567654", "9432567654"};
    ListView listView;
    List<Contacts> list;
    ImageView hamburgerIcon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        list = new ArrayList<>();

        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        for (int i = 0; i < names.length; i++) {
            Contacts contacts = new Contacts(names[i], numbers[i], dept_info[i]);
            list.add(contacts);
        }
        hamburgerIcon = rootView.findViewById(R.id.hamburgerIcon);

        hamburgerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((Main)getActivity()).drawerLayout.isDrawerOpen(GravityCompat.START))
                    ((Main)getActivity()).drawerLayout.closeDrawer(GravityCompat.START);
                else
                    ((Main)getActivity()).drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Coves-Bold.otf");
        ContactAdapter adapter = new ContactAdapter(this.getActivity(), list, typeface);

        listView = (ListView) rootView.findViewById(R.id.contacts);
        listView.setAdapter(adapter);
        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.allContacts);
        return rootView;

    }
}
