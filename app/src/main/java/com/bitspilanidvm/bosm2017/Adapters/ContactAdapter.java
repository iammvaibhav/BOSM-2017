package com.bitspilanidvm.bosm2017.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitspilanidvm.bosm2017.Modals.Contacts;
import com.bitspilanidvm.bosm2017.R;

import java.util.List;

/**
 * Created by sammy on 29/8/17.
 */

public class ContactAdapter extends ArrayAdapter<Contacts>{
    Context context;
    Typeface typeface;
    int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 434;
    public ContactAdapter(Context context, List<Contacts> list, Typeface typeface){
        super(context, R.layout.contact_cards,list);
        this.context=context;
        this.typeface = typeface;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.contact_cards,null,false);
            viewHolder=new ViewHolder();

            viewHolder.name=(TextView)convertView.findViewById(R.id.contact_name);
            viewHolder.number=(TextView)convertView.findViewById(R.id.number);
            viewHolder.call=(ImageView) convertView.findViewById(R.id.call);
            viewHolder.mail=(ImageView)convertView.findViewById(R.id.mail);
            viewHolder.info=(ImageView) convertView.findViewById(R.id.info);
            viewHolder.name.setTypeface(typeface);
            viewHolder.number.setTypeface(typeface);

            convertView.setTag(viewHolder);

        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();

        }
        Contacts item=getItem(position);
        viewHolder.name.setText(item.getName());
        viewHolder.number.setText(item.getNumber());
        setClickListners(viewHolder,item);
        return convertView;
    }

    void setClickListners(ViewHolder viewHolder , final Contacts item){
        //light: fed649 ,dark : ffc700
        viewHolder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call
                Log.d("Here : "," in call Listener");
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + item.getNumber()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



                /*if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale((AppCompatActivity)context,
                            Manifest.permission.CALL_PHONE)) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions((AppCompatActivity) context,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                }

                if (ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.CALL_PHONE}, 1);*/

                    Log.d("Here","Now");
                    try {
                        context.startActivity(callIntent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

               // }

            }
        });

        viewHolder.mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mail
                sendEmail();
            }
        });

        viewHolder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),item.getDept_info(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"someone@gmail.com"};
        String[] CC = {"xyz@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Log.i("Finished email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
    static class ViewHolder{
        TextView name;
        TextView number;
        ImageView call;
        ImageView mail;
        ImageView info;

    }
}
