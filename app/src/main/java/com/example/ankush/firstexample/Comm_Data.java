package com.example.ankush.firstexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Swati on 14-10-2016.
 */
public class Comm_Data extends Hisaab_Db {
    public Comm_Data(Context context) {
        super(context);
    }


    public boolean insertData(String name , String phon_num){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(super.username , name);
        cv.put(super.phone_num ,phon_num);
        final long result = db.insert(super.communication , null ,cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    public String getPhoneNumber(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+super.communication +" where "+super.username +"='"+name+"'" , null);
        if(res.moveToFirst())
            return res.getString(1);
        return " ";

    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + super.communication , null);
        return res;
    }
    public ArrayList getNames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + super.communication, null);
        while (cursor.moveToNext()) {
            arrayList.add(cursor.getString(0));
        }
        return arrayList;
    }
    public void delete(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+super.communication+" where "+super.username+"='"+name+"'");

    }
}
