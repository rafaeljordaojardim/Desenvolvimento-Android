package com.jardim.android.distancebetweendates;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;

public class ExemploNumberPicker extends AppCompatActivity {

    NumberPicker mNpYear,mNpMonth, mNpDay;
    RjjCalendar mCal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exemplo_number_picker);
        init();
    }//onCreate

    void init(){
        mNpMonth = findViewById(R.id.idNpMonth);
        mCal = new RjjCalendar();
        mNpYear = findViewById(R.id.idNpYear);
        mNpDay = findViewById(R.id.idNpDay);

        mNpYear.setMinValue(1500);
        mNpYear.setMaxValue(2100);
        mNpYear.setValue(mCal.getYear());

        mNpMonth.setMinValue(1);
        mNpMonth.setMaxValue(12);
        mNpMonth.setValue(mCal.getMonth());

        mNpDay.setMinValue(1);
        mNpDay.setMaxValue(31);
        mNpDay.setValue(mCal.getDay());

    }//init



}//ExemploNumberPicker
