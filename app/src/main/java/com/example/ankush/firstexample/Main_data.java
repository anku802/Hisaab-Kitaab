package com.example.ankush.firstexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ankush on 08-02-2017.
 */
public class Main_data extends Hisaab_Db {
    public Main_data(Context context) {
        super(context);
    }

    public boolean insertData(String total){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(super.amount , Integer.parseInt(total));
        final long result = db.insert(super.total ,null, cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    public String getTotalLeft() throws CursorIndexOutOfBoundsException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + super.total, null);
        if(cursor.moveToFirst())
            return cursor.getString(0);
        return ""+0;
    }

    public void deleteData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ super.total);
    }

}
