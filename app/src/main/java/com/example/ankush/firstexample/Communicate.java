package com.example.ankush.firstexample;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.lang.UCharacter;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Communicate extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    EditText name, phone_num;
    int p;
    FloatingActionButton addPeople;
    AlertDialog.Builder builder;
    ArrayList<String> arraayList;
    ArrayAdapter<String> adapter;
    ListView listview;
    String str[] = {};
    String pNUm,name_selected;
    Comm_Data database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addPeople = (FloatingActionButton) findViewById(R.id.addPeople);
        database = new Comm_Data(this);
        arraayList = new ArrayList<String>(Arrays.asList(str));
        arraayList = database.getNames();
        adapter = new ArrayAdapter<String>(Communicate.this, android.R.layout.simple_dropdown_item_1line, arraayList);
        listview = (ListView) findViewById(R.id.list_view);
        listview.setAdapter(adapter);
        listview.setOnItemLongClickListener(this);
        registerForContextMenu(listview);
        addPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(Communicate.this);
                builder.setTitle("Add Person Details");
                name = new EditText(Communicate.this);
                phone_num = new EditText(Communicate.this);
                phone_num.setInputType(UCharacter.NumericType.DIGIT);
                name.setHint("Enter the name");
                phone_num.setHint("Enter the phone number");
                final LinearLayout layout = new LinearLayout(Communicate.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(name);
                layout.addView(phone_num);
                builder.setView(layout);
                builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newItem = name.getText().toString();
                        String phnNum = phone_num.getText().toString();
                        boolean flag = database.insertData(newItem, phnNum);
                        if (flag == false)
                            Toast.makeText(Communicate.this, "Insertion failed ", Toast.LENGTH_SHORT).show();
                        else {
                            arraayList.add(newItem);
                            adapter.notifyDataSetChanged();
                        }

                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Communicate.this, "CANCELED", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.communicate, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.call: {
                startActivity(new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",pNUm,null)));
                break;
            }

            case R.id.message:{
                /*final SmsManager smsManager = SmsManager.getDefault();
                AlertDialog.Builder message = new AlertDialog.Builder(Communicate.this);
                final EditText sms = new EditText(this);
                sms.setHint("Enter the message");
                message.setView(sms);
                message.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        smsManager.sendTextMessage(pNUm, null, sms.getText().toString(), null, null);
                    }
                });
                message.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Communicate.this, "Message cancled", Toast.LENGTH_SHORT).show();
                    }
                }).show();
                break;*/

                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("sms:"+pNUm)));
            }

            case R.id.delete:{
                database.delete(name_selected);
                arraayList.remove(p);
                adapter.notifyDataSetChanged();
                break;
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        p=position;
        name_selected = listview.getItemAtPosition(position).toString();
        pNUm= database.getPhoneNumber(name_selected);
        Toast.makeText(Communicate.this, pNUm, Toast.LENGTH_SHORT).show();
        return false;
    }
}
