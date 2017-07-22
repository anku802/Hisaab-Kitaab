package com.example.ankush.firstexample;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.lang.UCharacter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Alerts extends AppCompatActivity implements AdapterView.OnItemClickListener {

   public static ListView notes;
    Intent i;
    public static boolean flag;
    alerts_data data ;
    public static ArrayList<String> list_of_notes;
    public static ArrayAdapter<String> note_adapter;
    public String n[] ={};
    public static int year_x,month_x,day_x,min,hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        data = new alerts_data(this);
        i = new Intent(Alerts.this, notes.class);
        list_of_notes  = new ArrayList<String>(Arrays.asList(n));
        list_of_notes = data.getNames();
        note_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,list_of_notes);
        notes = (ListView)findViewById(R.id.note);
        notes.setAdapter(note_adapter);
        notes.setOnItemClickListener(this);
        FloatingActionButton alertDialog = (FloatingActionButton) findViewById(R.id.alert);
        alertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal=Calendar.getInstance();
                flag = true;
                year_x=cal.get(Calendar.YEAR);
                month_x=cal.get(Calendar.MONTH)+1;
                day_x=cal.get(Calendar.DAY_OF_MONTH);
                hour = cal.get(Calendar.HOUR_OF_DAY);
                min = cal.get(Calendar.MINUTE);
                startActivity(i);
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String s = notes.getItemAtPosition(position).toString();
        flag = false;
        s=s.replaceAll("\\s","");
        String[]a = s.split("-|\\:");
        a[2]=a[2].substring(0,4);
        year_x = Integer.parseInt(a[0]);
        month_x = Integer.parseInt(a[1]);
        year_x = Integer.parseInt(a[2]);
        hour = Integer.parseInt(a[3]);
        min = Integer.parseInt(a[4]);
        startActivity(i);
    }


}