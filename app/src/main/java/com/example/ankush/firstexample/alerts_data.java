package com.example.ankush.firstexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ankush on 19-02-2017.
 */
public class alerts_data extends Hisaab_Db {
    public alerts_data(Context context) {
        super(context);
    }

    public boolean insertData(int date, int month, int year,int hour,int min, String note  ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(super.date , date);
        cv.put(super.month,month);
        cv.put(super.year,year);
        cv.put(super.hour,hour);
        cv.put(super.minutes,min);
        cv.put(super.note,note);
        final long result = db.insert(super.notes , null ,cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    public ArrayList getNames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + super.notes, null);
        while (cursor.moveToNext()) {
            String s = cursor.getString(0)+"-"+cursor.getString(1)+"-"+cursor.getString(2)+"        TIME:"+cursor.getString(3)+":"+cursor.getString(4);
            arrayList.add(s);
        }
        return arrayList;
    }

    public String getDescription(int dat, int mont, int yea,int hou, int min) {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+super.notes +" where "+super.date +"="+dat+" And "+super.month+"="+mont+" And "+super.year+"="+yea+" And "+super.hour+"="+hou+" And "+super.minutes+"='"+min+"'" , null);
        if(cursor.getCount()>0 && cursor!=null) {
            cursor.moveToNext();
            return cursor.getString(5);
        }
        return "";
    }


    public void deleteData(int a, int b, int c,int h,int m)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+super.notes +" where " + super.date +"="+ a+ " And "+ super.month +"="+ b +" And " + super.year+"="+c+" And "+ super.hour+"="+h+" and " + super.minutes+"='"+m+"'");
    }

}
