package com.example.ankush.firstexample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class notes extends AppCompatActivity {

    alerts_data notes;
    EditText note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        note = (EditText)findViewById(R.id.notes);
        notes = new alerts_data(this);
        note.setText(notes.getDescription(Alerts.day_x,Alerts.month_x,Alerts.year_x,Alerts.hour,Alerts.min));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(note, InputMethodManager.SHOW_IMPLICIT);
        // TODO Auto-generated method stub
        return super.onTouchEvent(event);
    }

    @Override
    protected void onStop() {
        String st=note.getText().toString();
        st=st.replaceAll("\\s","");
        if(st.equals("")){}
        else {
            notes.deleteData(Alerts.day_x,Alerts.month_x,Alerts.year_x,Alerts.hour,Alerts.min);
            String s = ""+Alerts.day_x+"-"+Alerts.month_x+"-"+Alerts.year_x+"       Time: "+Alerts.hour+":"+Alerts.min;
            notes.insertData(Alerts.day_x, Alerts.month_x, Alerts.year_x, Alerts.hour, Alerts.min, note.getText().toString());
            if(Alerts.flag==true) {
                Alerts.list_of_notes.add(s);
                Alerts.note_adapter.notifyDataSetChanged();
                Alerts.notes.setAdapter(Alerts.note_adapter);
            }
        }
        super.onStop();
    }
}
