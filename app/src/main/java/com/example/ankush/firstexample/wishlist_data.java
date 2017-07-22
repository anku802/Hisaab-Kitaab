package com.example.ankush.firstexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Ankush on 28-01-2017.
 */

public class wishlist_data extends Hisaab_Db {
    public wishlist_data(Context context) {
        super(context);
    }

    public boolean insertData(String name ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(super.name , name);
        final long result = db.insert(super.wishList , null ,cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + super.wishList , null);
        return res;
    }

    public String getList() throws CursorIndexOutOfBoundsException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + super.wishList, null);
        if(cursor.moveToFirst())
             return cursor.getString(0);
        return " ";
    }

    public void deleteData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ super.wishList);
    }

}
