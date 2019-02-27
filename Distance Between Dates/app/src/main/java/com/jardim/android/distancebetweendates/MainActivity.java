package com.jardim.android.distancebetweendates;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.sql.RowId;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    CalendarView mCv;
    RjjCalendar mCal;
    TextView mTvFeedback;
    /*
        para observar o nao comportamento do Calendar View
        face
     */
    CalendarView.OnClickListener mCalendarViewClickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mTvFeedback.setText("houve um click!");
        }
    };

    CalendarView.OnDateChangeListener mCalendarChangeHandler = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(
            CalendarView calendarView,
            int year,
            int month,
            int dayOfMonth) {
          //  mCal = new RjjCalendar(calendarView);
            mTvFeedback.setText("houve mudança de data");
        }//fim de metodo

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    void init(){
        mCv = findViewById(R.id.idCv);
        mCv.setOnDateChangeListener(mCalendarChangeHandler);
        mTvFeedback = findViewById(R.id.idTvFeedback);
        mCal = new RjjCalendar(mCv);
        mTvFeedback.setText("Data escolhida: "+mCal.ymd());

    }
}
