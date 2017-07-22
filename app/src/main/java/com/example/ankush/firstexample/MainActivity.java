package com.example.ankush.firstexample;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.StaticLayout;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static TextView total_Amount;
    Button todList;
    public  int total;
    Intent i;
    Button pic_date,alerts;
    public static Main_data total_amount;
    String month;
    AlertDialog.Builder addAmount;
    public static int year_x,day_x,month_x;

    static final int DILOG_ID = 0;

    @TargetApi(Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.ajeeb));
        getSupportActionBar().setTitle(" ");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        alerts = (Button)findViewById(R.id.alertMain);
        alerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(MainActivity.this, Alerts.class);
                startActivity(i);
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        total_amount = new Main_data(this);
        total_Amount = (TextView)findViewById(R.id.amount);
        todList = (Button) findViewById(R.id.todList);
        todList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final Calendar cal=Calendar.getInstance();
                   year_x=cal.get(Calendar.YEAR);
                     month_x=cal.get(Calendar.MONTH)+1;
                    day_x=cal.get(Calendar.DAY_OF_MONTH);
                Intent todList = new Intent(MainActivity.this, todaysList.class);
                startActivity(todList);
            }
        });
        final Calendar cal=Calendar.getInstance();
        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH)+1;
        day_x=cal.get(Calendar.DAY_OF_MONTH);
        i=new Intent(MainActivity.this,todaysfeeds.class);
        onClickPlus();

       // total_Amount.setText(total_amount.getTotalLeft().toString());

    }



    public  void onClickPlus(){
        pic_date=(Button)findViewById(R.id.pic_date);

        pic_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialog(DILOG_ID);

                startActivity(i);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == DILOG_ID)
            return new DatePickerDialog(this,dPickerListner,year_x,month_x,day_x);
        return super.onCreateDialog(id);
    }

    private DatePickerDialog.OnDateSetListener dPickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x=year;
            month_x=month;
            day_x=dayOfMonth;
            startActivity(i);
        }
    };
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()){

            case R.id.addToWallet: {
                addAmount = new AlertDialog.Builder(MainActivity.this);
                final EditText askAmount = new EditText(MainActivity.this);
                addAmount.setTitle("ADD MONEY");
                askAmount.setHint("Please enter the amount to add");
                askAmount.setInputType(UCharacter.NumericType.DIGIT);
                addAmount.setView(askAmount);
                addAmount.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String toAddAmount =askAmount.getText().toString();
                        int amt = Integer.parseInt(toAddAmount);
                        String s =total_Amount.getText().toString();
                        if(s == null){
                            Toast.makeText(MainActivity.this, "INVALID ENTRY", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            total = Integer.parseInt(s);
                            total += amt;
                            total_Amount.setText(""+total);
                        }
                        total_amount.deleteData();
                        total_amount.insertData(total_Amount.getText().toString());
                    }
                });
                addAmount.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                addAmount.show();
                return true;
            }
            case R.id.alerts:
                Intent alerts=new Intent(MainActivity.this,Alerts.class);
                startActivity(alerts);
                return  true;

            case R.id.search:
            {
                showDialog(DILOG_ID);
                return true;
            }

            case R.id.communicate: {
                Intent intent = new Intent(MainActivity.this, Communicate.class);
                startActivity(intent);
                return true;
            }

            case R.id.nav_Lend:
                Intent intent = new Intent(MainActivity.this,Lend.class);
                startActivity(intent);
                break;

            case R.id.nav_Borrow:
                Intent i=new Intent(MainActivity.this,Borrow.class);
                startActivity(i);
                break;

            default:
                return super.onOptionsItemSelected(item);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        total_amount.deleteData();
        total_amount.insertData(total_Amount.getText().toString());
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        total_amount.deleteData();
        total_amount.insertData(total_Amount.getText().toString());
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        total_Amount.setText(total_amount.getTotalLeft().toString());
        super.onStart();
    }
}
