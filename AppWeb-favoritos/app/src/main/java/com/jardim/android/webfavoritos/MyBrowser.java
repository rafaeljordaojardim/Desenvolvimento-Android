package com.jardim.android.webfavoritos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class MyBrowser extends AppCompatActivity {

    TextView mTvUrl;
    WebView mWv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_browser);

        init();
    }//onCreate

    void init(){
        mTvUrl = findViewById(R.id.idTvUrl);
        mWv = findViewById(R.id.idWv);

        receiveAndLoadUrl();
    }//init

    void receiveAndLoadUrl(){
        Intent intentWhoCalled = this.getIntent();

        if(intentWhoCalled != null){
            String strUrlToVisite =
                intentWhoCalled.getStringExtra(MainActivity.KEY_TO_URL);

            mWv.loadUrl(strUrlToVisite);
        }//FIM IF
    }//receiveAndLoadUrl
}//MyBrowser
