package com.example.ankush.firstexample;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.icu.lang.UCharacter;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class todaysfeeds extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    TextView show_date;
    AlertDialog.Builder addItem;
    EditText descrip , amount;
    ArrayList<String> addDescription;
    ArrayAdapter<String> arrayAdapter;
    todayfeeds_data data;
    String str[]= {};
    int p,tot;
    String name_selected;
    ListView itemsList;
    MainActivity mainActivity;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todaysfeeds);
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        data = new todayfeeds_data(this);
        mainActivity = new MainActivity();
        getSupportActionBar().setTitle(" " +mainActivity.day_x+"/"+mainActivity.month_x+"/"+mainActivity.year_x);
        addDescription = new ArrayList<String>(Arrays.asList(str));
        addDescription=data.getDescription(mainActivity.day_x,mainActivity.month_x,mainActivity.year_x);
        arrayAdapter = new ArrayAdapter<String>(todaysfeeds.this, android.R.layout.simple_dropdown_item_1line , addDescription);
        itemsList = (ListView) findViewById(R.id.des_list);
        itemsList.setAdapter(arrayAdapter);
        registerForContextMenu(itemsList);
        itemsList.setOnItemLongClickListener(this);
        final FloatingActionButton description = (FloatingActionButton)findViewById(R.id.todaysfeeds);
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem=new AlertDialog.Builder(todaysfeeds.this);
                addItem.setTitle("ENTER DESCRIPTION");
                descrip=new EditText(todaysfeeds.this);
                amount=new EditText(todaysfeeds.this);
                amount.setInputType(UCharacter.NumericType.DIGIT);
                descrip.setHint("Enter the items bought");
                amount.setHint("Enter the amount");
                LinearLayout l =new LinearLayout(todaysfeeds.this);
                l.setOrientation(LinearLayout.VERTICAL);
                l.addView(descrip);
                l.addView(amount);
                addItem.setView(l);
                addItem.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newItem = descrip.getText().toString();
                        int dedect = Integer.parseInt(amount.getText().toString());
                        tot = Integer.parseInt(MainActivity.total_Amount.getText().toString());
                        if (dedect < 0)
                            Toast.makeText(todaysfeeds.this, "Invalid Entry", Toast.LENGTH_SHORT).show();

                        else if(dedect>tot)
                            Toast.makeText(todaysfeeds.this, "Insufficient Amount", Toast.LENGTH_SHORT).show();
                        else {

                            boolean flag = data.insertData(mainActivity.day_x, mainActivity.month_x, mainActivity.year_x, newItem, dedect);
                            if(flag==false)
                                Toast.makeText(todaysfeeds.this, "Invalid Entry", Toast.LENGTH_SHORT).show();
                            else {
                                MainActivity.total_amount.deleteData();
                                MainActivity.total_amount.insertData("" + (tot - dedect));
                                addDescription.add(newItem);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
                addItem.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                addItem.show();
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tod, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.show:{
                Toast.makeText(todaysfeeds.this, data.getAmount(name_selected), Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.edit:{
                AlertDialog.Builder edit = new AlertDialog.Builder(this);
                TextView name = new TextView(this);
                name.setText(name_selected);
                final EditText amount = new EditText(this);
                amount.setText(data.getAmount(name_selected));
                edit.setTitle("Edit the Details");
                edit.setView(name);
                edit.setView(amount);
                edit.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tot = Integer.parseInt(MainActivity.total_amount.getTotalLeft());
                        MainActivity.total_amount.deleteData();
                        MainActivity.total_amount.insertData("" + (tot + Integer.parseInt(data.getAmount(name_selected))));
                        data.update(name_selected,amount.getText().toString());
                        tot = Integer.parseInt(MainActivity.total_amount.getTotalLeft());
                        MainActivity.total_amount.deleteData();
                        MainActivity.total_amount.insertData("" + (tot - Integer.parseInt(data.getAmount(name_selected))));
                    }
                });
                edit.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            }

            case R.id.delete:{
                data.delete(name_selected);
                addDescription.remove(p);
                arrayAdapter.notifyDataSetChanged();
                break;
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        p=position;
        name_selected = itemsList.getItemAtPosition(position).toString();
        return false;
    }
}
