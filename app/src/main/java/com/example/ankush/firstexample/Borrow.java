package com.example.ankush.firstexample;

import android.content.DialogInterface;
import android.graphics.Color;
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

public class Borrow extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    EditText person_name,amount_given;
    int p;
    String name_selected;
    AlertDialog.Builder builder;
    ListView borrowList;
    ArrayAdapter<String> borrow_listAdapter;
    ArrayList<String> borrowArrayList;
    String lendStr[] = {};
    Borrow_Data db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        borrowList = (ListView) findViewById(R.id.borrowList);
        db = new Borrow_Data(this);

        borrowArrayList = new ArrayList<String>(Arrays.asList(lendStr));
        borrowArrayList = db.getNames();
        borrow_listAdapter = new ArrayAdapter<String>(Borrow.this, android.R.layout.simple_dropdown_item_1line , borrowArrayList);
        borrowList.setAdapter(borrow_listAdapter);
        borrowList.setOnItemLongClickListener(this);
        registerForContextMenu(borrowList);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder=new AlertDialog.Builder(Borrow.this);
                builder.setTitle("ADD PERSON'S DETAILS");
                person_name=new EditText(Borrow.this);
                amount_given=new EditText(Borrow.this);
                amount_given.setInputType(UCharacter.NumericType.DIGIT);
                person_name.setHint("Enter the name of person");
                amount_given.setHint("Enter the amount");
                LinearLayout l =new LinearLayout(Borrow.this);
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
                        if(am_gv<0)
                            Toast.makeText(Borrow.this, "Invalid Entry", Toast.LENGTH_SHORT).show();
                            else {
                                boolean flag = db.insertData(person_name.getText().toString(), amount_given.getText().toString());
                                if (flag == false)
                                    Toast.makeText(Borrow.this, "Insertion failed ", Toast.LENGTH_SHORT).show();
                                else{
                                    MainActivity.total_amount.deleteData();
                                    MainActivity.total_amount.insertData("" + (tot + am_gv));
                                    Toast.makeText(Borrow.this, "Available Balance " + (tot + am_gv), Toast.LENGTH_SHORT).show();
                                    borrowArrayList.add(newItem);
                                    borrow_listAdapter.notifyDataSetChanged();
                            }
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
        });
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
                Toast.makeText(Borrow.this, db.getAmount(name_selected), Toast.LENGTH_SHORT).show();
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
                        MainActivity.total_amount.insertData("" + (tot - Integer.parseInt(db.getAmount(name_selected))));
                        db.update(name_selected,amount.getText().toString());
                        tot = Integer.parseInt(MainActivity.total_amount.getTotalLeft());
                        MainActivity.total_amount.deleteData();
                        MainActivity.total_amount.insertData("" + (tot + Integer.parseInt(db.getAmount(name_selected))));
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
                borrowArrayList.remove(p);
                borrow_listAdapter.notifyDataSetChanged();
                break;
            }

            case R.id.given:{
                AlertDialog.Builder edit = new AlertDialog.Builder(this);
                edit.setTitle("Have you updated this amount in your Main-Balance?");
                edit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete(name_selected);
                        borrowArrayList.remove(p);
                        borrow_listAdapter.notifyDataSetChanged();
                    }
                });
                edit.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int am_gv = Integer.parseInt(db.getAmount(name_selected).toString());
                        int tot = Integer.parseInt(MainActivity.total_Amount.getText().toString());
                        MainActivity.total_amount.deleteData();
                        MainActivity.total_amount.insertData("" + (tot - am_gv));
                        Toast.makeText(Borrow.this, "Available Balance " + (tot - am_gv), Toast.LENGTH_SHORT).show();
                        db.delete(name_selected);
                        borrowArrayList.remove(p);
                        borrow_listAdapter.notifyDataSetChanged();
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
        name_selected = borrowList.getItemAtPosition(position).toString();
        return false;
    }
}
