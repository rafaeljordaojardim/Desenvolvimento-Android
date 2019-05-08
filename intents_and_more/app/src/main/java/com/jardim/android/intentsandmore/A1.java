package com.jardim.android.intentsandmore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class A1 extends AppCompatActivity {
    /*
    public final static String KEY_THAN_UNLOCKS_THE_NAME =
            "KEY_THAN_UNLOCKS_THE_NAME";*/
    EditText mEtUserName;
    Button mBtnSendUserName;


    void handleSendUserName(){


        /*
        1)ler o nome que o utilizador escrever no EditText

        2)fazer um objeto para comunicar com um activity A2

        3)Enviar o nome escrito em A1 para A2, através do objeto de comunicação
        */


        //1
        String strUserName = mEtUserName.getText().toString();
        strUserName= strUserName.trim();//eliminate white spaces to l and to the r

        /*
        //2
        //intent explicito
        Intent intentToBridgeA1ToA2 = new Intent(A1.this, A2.class );

        intentToBridgeA1ToA2.putExtra(
                KEY_THAN_UNLOCKS_THE_NAME, //key to unlock the message
                strUserName//mensagem a passar
        );
        //3
            how to pass the name
            how to pass any data between A1 and A2
            the answer is: vai the intent
        startActivity(
            intentToBridgeA1ToA2

        );
        */
        Intent intentToCallA2SendingITheUserName = A2.getIntentToSendName(this, strUserName);
        startActivity(intentToCallA2SendingITheUserName);


    }//fim handleSendUserName
    Button.OnClickListener mButtonClickHandler = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.idBtnSendName:
                        handleSendUserName();
                    break;
            }//switch
        }//onClick
    };//fim do Button.OnClickListener()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1);

        init();
    }//onCreate

    //Oncreate inicialização -> onStart -> onResume presentation in on resume
    void init(){
        //1 associates
        mEtUserName      = findViewById(R.id.idEtUserName);
        mBtnSendUserName = findViewById(R.id.idBtnSendName);
        mBtnSendUserName.setOnClickListener(mButtonClickHandler);
    }//init
}//class A1
