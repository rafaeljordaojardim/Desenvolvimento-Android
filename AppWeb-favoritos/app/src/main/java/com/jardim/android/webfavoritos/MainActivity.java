package com.jardim.android.webfavoritos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_TO_URL = "KEY_TO_URL";
    /*
        PUBLIC - NIVEL  DE ACEESSO +PROMISCUO
        STATIC - MEMORIA PARTILHADA ENTRE TODAS INSTANCIAS
        FINAL - INFICA UMA CONSTANTE
        String - tipo doo dado
     */
    Context mContext;
    Button mBtnUrlMad;
    Button mBtnUrlDn;
    Spinner mSpnHowToNavigate;

    ArrayAdapter<String> mAd;//PROXY/BROKER/INTERMEDIARIO/ ENTRE STRING_ARRAY E A VIEW (USANDO MODEL VIEW CONTROL)
    //SEMPRE CONCENTRAR ONDE FICA O HANDLER DOS OBJETOS

    View.OnClickListener mButtonClickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.idBtnUrlMad:
                    goVisitUrlViaWebViewInApp(getString(R.string.strUrlMad));
                    break;
                case R.id.idBtnUrlDn:
                    goVisitUrlViaWebViewInApp(getString(R.string.strUrlDn));
                    break;
            }//switch
        }//onClick
    };//View.OnClickListener

    //@param pUrl : String which represents the URL to visit
    //@return : void
    void goVisitUrlViaChooser(String pUrl){
        /*
            este metodo é a nossa primeira oportunidade para um
            "Intent explicito"
            vamos indicar a ação (VIEW)
            e os dados sujeitos a ação (o Url)
         */
        Intent intentGoVisitUrl = new Intent(Intent.ACTION_VIEW);
        //try{
            Uri uriUrlToVisit = Uri.parse(pUrl);//criando um Uri pois só aceita data do tipo Uri
        //}catch (Exception e){}
        intentGoVisitUrl.setData(
         //os da dos para Action View
                uriUrlToVisit
        );//setData

        startActivity(intentGoVisitUrl);
    }//goVisitUrlViaChooser

    void goVisitUrlViaWebViewInApp(String pUrl){

        Intent intentGoVisitUrlViaWebViewInApp = new Intent(this, MyBrowser.class);
        intentGoVisitUrlViaWebViewInApp.putExtra(KEY_TO_URL, pUrl);

        startActivity(intentGoVisitUrlViaWebViewInApp);
    }//goVisitUrlViaWebViewInApp
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }//onCreate

    void init(){
        mContext = this;
        //associações
        mBtnUrlMad = findViewById(R.id.idBtnUrlMad);
        mBtnUrlDn = findViewById(R.id.idBtnUrlDn);
        mSpnHowToNavigate = findViewById(R.id.idSpnHowToNavigate);

        mAd = new ArrayAdapter<>(
          mContext,
          android.R.layout.simple_list_item_1,
          mContext.getResources().getStringArray(R.array.alNavigateOptions)

        );
        mSpnHowToNavigate.setAdapter(mAd);
        //comportamentos
        Button[] allTheButtons = {mBtnUrlDn, mBtnUrlMad};
        for (Button b:
             allTheButtons) {
            b.setOnClickListener(mButtonClickHandler);
        }//foreach
    }//init
}//MainActivity
