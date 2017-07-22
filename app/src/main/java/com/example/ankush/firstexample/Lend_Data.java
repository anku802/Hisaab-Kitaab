package com.example.ankush.firstexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Ankush on 29-10-2016.
 */
public class Lend_Data extends Hisaab_Db {

    public Lend_Data(Context context) {
        super(context);
    }

    public boolean insertData(String name1 , String amount1){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(name , name1);
        cv.put(amount , Integer.parseInt(amount1));
        final long result = db.insert(super.Lend ,null, cv);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + super.Lend , null);
        return res;
    }
    public ArrayList getNames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + super.Lend, null);
        while (cursor.moveToNext()) {
            arrayList.add(cursor.getString(0));
            //arrayList.add(cursor.getString(1));
        }
        return arrayList;
    }

    public String getAmount(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+super.Lend +" where "+super.name +"='"+name+"'" , null);
        if(res.moveToFirst())
            return res.getString(1);
        return " ";
    }

    public void update(String s,String x){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("Update "+super.Lend+" SET "+super.amount+"= "+x+" where "+super.name+"='"+s+"'");
    }

    public void delete(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+super.Lend+" where "+super.name+"='"+name+"'");

    }
}
