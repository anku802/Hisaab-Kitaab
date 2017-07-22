package com.example.ankush.firstexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ankush on 07-02-2017.
 */
public class Hisaab_Db extends SQLiteOpenHelper {
    public static final String db_name = "HISAAB KITAAB";


    public static final String borrw = "Borrow_Data";
    public static final String Lend = "Lend_Data";
    public static final String name = "name";
    public static final String amount = "amount";

    public static final String total = "total";

    public static final String wishList = "wish_list";


    final static String communication = "Communication_data";
    final static String username = "Name";
    final static String phone_num = "Phone ";

    final static String todayFeeds = "todayFeeds";
    final static String date = "date";
    final static String month = "month";
    final static String year = "year";
    final static String items = "items";

    final static String notes = "notes";
    final static String note = "note";
    final static String hour = "hour";
    final static String minutes = "minutes";




    public Hisaab_Db(Context context) {
        super(context, db_name, null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ borrw + "( "+name + " text Primary key , " + amount + " integer ) ;" );
        db.execSQL("create table "+ communication + "( "+username + " text Primary Key , " + phone_num + " varchar(16) ) ;" );
        db.execSQL("create table "+ Lend + "( "+name + " text Primary Key , " + amount + " integer ) ;" );
        db.execSQL("create table "+ wishList + "( "+name + " text  ) ;" );
        db.execSQL("create table "+ total + "( "+amount + " Real  ) ;" );
        db.execSQL("create table "+ todayFeeds + "( "+date+" numeric,"+month+" numeric,"+year+" numeric,"+items+" text,"+amount+" numeric ) ;" );
        db.execSQL("create table "+ notes + "( "+date+" numeric,"+month+" numeric,"+year+" numeric,"+hour+" numeric,"+minutes+" numeric,"+note+" text ) ;" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
