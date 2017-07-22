package com.example.ankush.firstexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Ankush on 08-02-2017.
 */
public class todayfeeds_data extends Hisaab_Db {
    public todayfeeds_data(Context context) {
        super(context);
    }

    public boolean insertData(int date, int month, int year, String Item , int amount ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(super.date , date);
        cv.put(super.month,month);
        cv.put(super.year,year);
        cv.put(super.items,Item);
        cv.put(super.amount,amount);
        final long result = db.insert(super.todayFeeds , null ,cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    public ArrayList getDescription(int date, int month, int year) {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + super.todayFeeds+" where "+super.date +"="+date+" And "+super.month +"="+month+" And "+super.year +"="+year+"", null);
        if(cursor.getCount()>0 && cursor!=null)
            while (cursor.moveToNext()) {
                arrayList.add(cursor.getString(3));
            }
        return arrayList;
    }

    public void update(String s,String x){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("Update "+super.todayFeeds+" SET "+super.amount+"= "+x+" where "+super.items+"='"+s+"'");
    }

    public String getAmount(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+super.todayFeeds +" where "+super.items +"='"+name+"'" , null);
        if(res.moveToFirst())
            return res.getString(4);
        return " ";

    }

    public void delete(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+super.todayFeeds+" where "+super.items+"='"+name+"'");

    }

}
