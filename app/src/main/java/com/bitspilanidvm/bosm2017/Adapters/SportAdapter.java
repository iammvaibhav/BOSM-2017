package com.bitspilanidvm.bosm2017.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitspilanidvm.bosm2017.ClickListeners.SubscriptionRecyclerViewOnClickListener;
import com.bitspilanidvm.bosm2017.R;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.List;

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.MyViewHolder> {

    private String[] sportList;
    SubscriptionRecyclerViewOnClickListener listener;
    List<String> favouriteList;
    final String[] sports = {"Hockey", "Athletics (Boys)", "Athletics (Girls)", "Basketball (Boys)", "Lawn Tennis (Girls)", "Lawn Tennis (Boys)", "Squash", "Swimming (Boys)", "Football (Boys)", "Badminton (Boys)", "Powerlifting", "Chess", "Table Tennis (Boys)", "Table Tennis (Girls)", "Taekwondo (Boys)", "Taekwondo (Girls)", "Volleyball (Boys)", "Volleyball (Girls)", "Badminton (Girls)", "Carrom", "Swimming (Girls)", "Cricket", "Football (Girls)", "Snooker", "Basketball (Girls)"};


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView SportTitle;
        public ShineButton shineButton;

        public MyViewHolder(View view) {
            super(view);
            SportTitle = (TextView) view.findViewById(R.id.title_id);
            shineButton = (ShineButton) view.findViewById(R.id.shineBtn);
        }
    }

    public SportAdapter(String[] sportList, SubscriptionRecyclerViewOnClickListener listener, List<String> favouriteList) {
        this.sportList = sportList;
        this.listener = listener;
        this.favouriteList = favouriteList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (favouriteList.contains(sports[position])) {
            holder.shineButton.setChecked(true);
        } else {
            holder.shineButton.setChecked(false);
        }
        holder.SportTitle.setText(sportList[position]);
        holder.shineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(holder, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sportList.length;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout, parent, false);
        return new MyViewHolder(v);
    }
}