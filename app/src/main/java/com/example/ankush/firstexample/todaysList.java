package com.example.ankush.firstexample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class todaysList extends AppCompatActivity {

EditText list;
    wishlist_data Wlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_list);
        list=(EditText)findViewById(R.id.editText2);
        Wlist = new wishlist_data(this);
        list.setText(Wlist.getList());
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(list, InputMethodManager.SHOW_IMPLICIT);
        // TODO Auto-generated method stub
        return super.onTouchEvent(event);
    }

    @Override
    protected void onStop() {
        Wlist.deleteData();
        boolean flag=Wlist.insertData(list.getText().toString());
        super.onStop();
    }

}
