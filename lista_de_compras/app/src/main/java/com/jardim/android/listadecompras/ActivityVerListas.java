package com.jardim.android.listadecompras;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ActivityVerListas extends AppCompatActivity {

    public static final String CHAVE_VER_LISTAS = "CHAVE_VER_LISTAS";
    //Listener Do botao
   /* Button.OnClickListener mHandlerCLickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.idBtnVoltar:
                    finish();
                    break;
            }
        }
    };*/
    //Listener da LvListaCompras
    ListView.OnItemLongClickListener mHandlerItemLongClickListener = new ListView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            // ClasseAuxiliar.mostrarMensagem(mContext, "item "+mListaCompras.get(position));
           // String nomeLista = mListaCompras.get(position);
            //deletarLista(nomeLista, position);
            //Cria o gerador do AlertDialog
            final int posicao = position;
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityVerListas.this);
            //define o titulo
            builder.setTitle("Excluir / Compartilhar");
            //define a mensagem
            builder.setMessage("O que quer fazer com "+ mListaCompras.get(posicao) + "?");
            //define um botão como positivo
            builder.setPositiveButton("Compartilhar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    String nomeLista = mListaCompras.get(posicao);
                    compartilharLista(nomeLista, posicao);
                }
            });
            //define um botão como negativo.
            builder.setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    String nomeLista = mListaCompras.get(posicao);
                    deletarLista(nomeLista, posicao);
                }
            });
            //cria o AlertDialog
            AlertDialog alerta = builder.create();
            //Exibe
            alerta.show();
            return true;
        }//onItemLongClick
    };//mHandlerIteLongClickListener

    ListView.OnItemClickListener mHandlerItemClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            chamarComprando(position);
        }//onItemClick
    };//mHandlerItemClickListener

    Context mContext;//contexto
    SalvarLista salvaLista = new SalvarLista();
    Button mBtnVoltar;//botão para voltar

    //ListView para ver as listas de compras
    ListView mLvListaCompras;
    ArrayAdapter<String> mAdListaCompras;
    ArrayList<String> mListaCompras;
    //declarando membro SalvarLista



    //Declarando mebro de dados
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_listas);

        init();
    }//onCreate

    void init(){
        mContext = this;
       // mBtnVoltar = findViewById(R.id.idBtnVoltar);
       // mBtnVoltar.setOnClickListener(mHandlerCLickListener);
        this.iniciandoListView();
    }//init

    private void iniciandoListView(){
        mListaCompras = salvaLista.verListas(mContext);
        if (mListaCompras.size() == 0){
            ClasseAuxiliar.mostrarMensagem(mContext, getResources().getString(R.string.strNaoHaCompras));
            finish();
        }else{
            mLvListaCompras = findViewById(R.id.idLvListaCompras);
            mLvListaCompras.setOnItemLongClickListener(mHandlerItemLongClickListener);
            mLvListaCompras.setOnItemClickListener(mHandlerItemClickListener);
            mAdListaCompras = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, mListaCompras);
            mLvListaCompras.setAdapter(mAdListaCompras);
        }//else
    }//iniciandoListView

    //função chamada quando o usuario da um longo click sobre um item da lista
    void deletarLista(String pNomeLista, int pPosition){
        boolean bCheckExiste = salvaLista.verificaSeAListaExiste(mContext,pNomeLista);
        if (bCheckExiste){
            boolean bCheck = salvaLista.deletarLista(mContext,pNomeLista);
            if(bCheck){
                ClasseAuxiliar.mostrarMensagem(mContext, String.format(getResources().getString(R.string.strListaExcluida), pNomeLista));
            }//IF
            mListaCompras.remove(pPosition);
            mAdListaCompras.notifyDataSetChanged();
        }//IF
    }//deletarLista
    //metodo para chamar AC comprando
    void chamarComprando(int pPosition){
        String nomeLista = mListaCompras.get(pPosition);
        Intent intent = new Intent(mContext, ActivityComprando.class);
        intent.putExtra(ActivityVerListas.CHAVE_VER_LISTAS, nomeLista);
        finish();
        startActivity(intent);
    }//chamarComprando

    //fazendo inflate do menu

    @Override
    public boolean onCreateOptionsMenu(Menu pMenu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity_ver_listas, pMenu);
        return super.onCreateOptionsMenu(pMenu);
    }//onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        switch (pItem.getItemId()){
            case R.id.idMenuVoltar:
                finish();
                break;
        }//switch
        return super.onOptionsItemSelected(pItem);
    }
    //metodo para compartilhar a lista
    void compartilharLista(String pNomeLista, int pPosicao){
        boolean bCheckExiste = salvaLista.verificaSeAListaExiste(mContext,pNomeLista);
        if (bCheckExiste) {
            ArrayList<String> lista = salvaLista.resgatarLista(pNomeLista, mContext);

            if(lista != null){
                String str = "Lista de Compras\nComprar: \n";
                for (String l : lista){
                    str += l +"\n";
                }
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, str);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,pNomeLista));
            }
        }
    }//compartilharLista
}//ActivityVerListas
