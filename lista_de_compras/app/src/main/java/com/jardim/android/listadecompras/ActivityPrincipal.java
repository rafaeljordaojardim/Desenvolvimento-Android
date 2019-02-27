package com.jardim.android.listadecompras;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityPrincipal extends AppCompatActivity {

    //Constantes
    public static final String CHAVE = "CHAVE";

    //Declarando contexto
    Context mContext;
    //Declarando membros de dados
    Button mBtnChamarCriarLista;
    Button mBtnChamarVerListas;
    Button mBtnSair;
    //chamar salvarLista
    SalvarLista salvarLista ;
    String mNomeLista;

    Button.OnClickListener mHandlerClickButton = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.idBtnChamarCriarLista:
                        chamaCriarLista();
                    break;
                case R.id.idBtnChamarVerListas:
                        chamaVerListas();
                    break;
                case R.id.idBtnSair:
                        chamaSair();
                    break;
            }//Switch
        }//onClick
    };//mHandlerClickButton

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }//onCreate

      /*
        Este método Associa os botões em java ao XML
      */
    void iniciarBotoes(){
        mBtnChamarCriarLista = findViewById(R.id.idBtnChamarCriarLista);
        mBtnChamarVerListas = findViewById(R.id.idBtnChamarVerListas);
        mBtnSair = findViewById(R.id.idBtnSair);
        Button[] buttons = {mBtnChamarCriarLista, mBtnChamarVerListas, mBtnSair};
        for (Button button: buttons) button.setOnClickListener(mHandlerClickButton);
    }//iniciar botoes

    void init(){
        salvarLista = new SalvarLista();
        mContext = this;
        mNomeLista = "";
        //Associando variaveis do java ao XML
        this.iniciarBotoes();
    }//init


    //Alert dialog para perguntar o nome que tera na lista
    void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nome da Lista");

        // Set up the input
        final EditText input = new EditText(mContext);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.requestFocus();
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton(getResources().getText(R.string.strOk), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mNomeLista = input.getText().toString().trim();
                if(mNomeLista.equals("")){
                    ClasseAuxiliar.mostrarMensagem(mContext, getResources().getString(R.string.strCampoVazio));
                    chamaCriarLista();
                }else if(salvarLista.verificaSeAListaExiste(mContext, mNomeLista)){
                    ClasseAuxiliar.mostrarMensagem(mContext, getResources().getString(R.string.strListaExistente));
                    chamaCriarLista();
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString(CHAVE, mNomeLista);
                    Intent intent = new Intent(mContext, ActivityCriarLista.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }//else
            }//Onclick
        });
        builder.setNegativeButton(getResources().getText(R.string.strCancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }//dialog


    //Metodos que são chamados quando algum botão é clicado

   void chamaCriarLista(){
        this.dialog();
   }//chamaCriarLista


    void chamaVerListas(){
        Intent intent = new Intent(mContext, ActivityVerListas.class);
        startActivity(intent);
    }//chamaVerListas

    void chamaSair(){
        //chama um intent para home, fecha o aplicativo
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);//ver como faz pra sair da aplicação
    }//chamaSair


}//ActivityPrincipal
