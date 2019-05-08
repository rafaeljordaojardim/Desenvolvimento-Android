package com.jardim.android.intentsandmore;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class A2 extends AppCompatActivity {

    public final static String KEY_THAN_UNLOCKS_THE_NAME =
            "KEY_THAN_UNLOCKS_THE_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2);


       init();
    }

    void init(){
        receiveTheNameAndSayHelloToTheUser();
    }//fim  de init

    void receiveTheNameAndSayHelloToTheUser(){

        Intent intentWhoCalledMe = this.getIntent();
        if(intentWhoCalledMe != null){
            //recebendo mensagem da outra Activity
            String strUserName =
            intentWhoCalledMe.getStringExtra(//" KEY_THAN_UNLOCKS_THE_NAME"//ugly => value should be constraint
                    KEY_THAN_UNLOCKS_THE_NAME);

           String strHelloLeft = getResources().getString(R.string.strHelloLeft);
           String strHelloRight = getResources().getString(R.string.strHelloRight);

           String strHello = strHelloLeft+" "+strUserName+strHelloRight;
            Toast t = Toast.makeText(
                    getApplicationContext(),
                    strHello,
                    Toast.LENGTH_SHORT);
            t.show();

        }//fim de if

    }//receiveTheNameAndSayHelloToTheUser

    /*este metodo ensina qualquer activity chmadora
    * a chamar A2, para envio de um nome*/
    public static Intent getIntentToSendName(Activity pCaller, String pStrName){
        Intent ret = new Intent(pCaller, A2.class);
        ret.putExtra(KEY_THAN_UNLOCKS_THE_NAME, pStrName);

        return ret;
    }//getIntentToSendName
}//fim de metodo
