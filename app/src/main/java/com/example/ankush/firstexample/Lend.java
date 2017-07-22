package com.example.ankush.firstexample;

import android.content.DialogInterface;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Lend extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    EditText person_name,amount_given;
    AlertDialog.Builder builder;
    int p;
    String name_selected;
    ListView lendList;
    ArrayAdapter<String> lend_listAdapter;
    ArrayList<String> lendArrayList;
    String lendStr[] = {};
    Lend_Data db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new Lend_Data(this);
        lendList = (ListView) findViewById(R.id.lendList);
        lendArrayList = new ArrayList<String>(Arrays.asList(lendStr));
        lendArrayList = db.getNames();
        lend_listAdapter = new ArrayAdapter<String>(Lend.this, android.R.layout.simple_dropdown_item_1line , lendArrayList);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_people);
        fab.setOnClickListener(Lend.this);
        lendList.setAdapter(lend_listAdapter);
        lendList.setOnItemLongClickListener(this);
        registerForContextMenu(lendList);
    }

    @Override
    public void onClick(View v) {
        builder=new AlertDialog.Builder(this);
        builder.setTitle("ADD PERSON'S DETAILS");
        person_name=new EditText(Lend.this);
        amount_given=new EditText(Lend.this);
        amount_given.setInputType(UCharacter.NumericType.DIGIT);
        person_name.setHint("Enter the name of person");
        amount_given.setHint("Enter the amount");
        LinearLayout l =new LinearLayout(Lend.this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.addView(person_name);
        l.addView(amount_given);
        builder.setView(l);
        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               String newItem = person_name.getText().toString();
                int am_gv = Integer.parseInt(amount_given.getText().toString());
                int tot = Integer.parseInt(MainActivity.total_Amount.getText().toString());
                if(tot<am_gv)
                    Toast.makeText(Lend.this, "Insufficient Balance", Toast.LENGTH_SHORT).show();
                else if(am_gv<0)
                    Toast.makeText(Lend.this, "Invalid Entry", Toast.LENGTH_SHORT).show();
                else {
                    boolean flag = db.insertData(person_name.getText().toString(), amount_given.getText().toString());

                    MainActivity.total_amount.deleteData();
                    MainActivity.total_amount.insertData("" + (tot - am_gv));
                    Toast.makeText(Lend.this, "Available Balance " + (tot - am_gv), Toast.LENGTH_SHORT).show();

                    if (flag == false)
                        Toast.makeText(Lend.this, "Insertion failed ", Toast.LENGTH_SHORT).show();
                    lendArrayList.add(newItem);
                    lend_listAdapter.notifyDataSetChanged();
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.borrow, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.show:{
                Toast.makeText(Lend.this, db.getAmount(name_selected), Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.edit:{
                AlertDialog.Builder edit = new AlertDialog.Builder(this);
                TextView name = new TextView(this);
                name.setText(name_selected);
                final EditText amount = new EditText(this);
                amount.setText(db.getAmount(name_selected));
                edit.setTitle("Edit the Details");
                edit.setView(name);
                edit.setView(amount);
                edit.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int tot = Integer.parseInt(MainActivity.total_amount.getTotalLeft());
                        MainActivity.total_amount.deleteData();
                        MainActivity.total_amount.insertData("" + (tot + Integer.parseInt(db.getAmount(name_selected))));
                        db.update(name_selected,amount.getText().toString());
                        tot = Integer.parseInt(MainActivity.total_amount.getTotalLeft());
                        MainActivity.total_amount.deleteData();
                        MainActivity.total_amount.insertData("" + (tot - Integer.parseInt(db.getAmount(name_selected))));
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
                db.delete(name_selected);
                lendArrayList.remove(p);
                lend_listAdapter.notifyDataSetChanged();
                break;
            }

            case R.id.given:{
                AlertDialog.Builder edit = new AlertDialog.Builder(this);
                edit.setTitle("Have you updated this amount in your Main-Balance?");
                edit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete(name_selected);
                        lendArrayList.remove(p);
                        lend_listAdapter.notifyDataSetChanged();
                    }
                });
                edit.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int am_gv = Integer.parseInt(db.getAmount(name_selected).toString());
                        int tot = Integer.parseInt(MainActivity.total_Amount.getText().toString());
                        MainActivity.total_amount.deleteData();
                        MainActivity.total_amount.insertData("" + (tot + am_gv));
                        Toast.makeText(Lend.this, "Available Balance " + (tot + am_gv), Toast.LENGTH_SHORT).show();
                        db.delete(name_selected);
                        lendArrayList.remove(p);
                        lend_listAdapter.notifyDataSetChanged();
                    }
                }).show();
                break;
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        p=position;
        name_selected = lendList.getItemAtPosition(position).toString();
        return false;
    }
}
