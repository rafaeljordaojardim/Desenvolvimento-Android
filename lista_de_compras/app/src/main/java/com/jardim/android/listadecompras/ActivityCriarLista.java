package com.jardim.android.listadecompras;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityCriarLista extends AppCompatActivity {

    //declaração de contexto
    Context mContext;

    //nome da lista
    String mNomeLista;
    //Declaração dos mebros de dados
    EditText mEtNomeProduto;
    TextView mTvNomeLista;
    Button mBtnAdicionaProduto;
    Button mBtnSalvarLista;

    //Declaração para ListView
    ListView mLvListaProdutos;
    ArrayAdapter<String> mAdListaProdutos;
    ArrayList<String> mListaProdutos;

    // Classe para salvar e recuperar a lista
    SalvarLista mListadb = new SalvarLista();


    //handler para a list view com finalidade de segurar em um item e apaga-lo
    ListView.OnItemLongClickListener mHandlerLongItemListener = new ListView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            removerItemDaListView(position);
            return false;
        }//onItemLongClick
    };//mHandlerLongItemListener
    //Handler para click no botao, cada botão tem uma ação
    Button.OnClickListener mHandlerClickButton = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.idBtnAdicionaProduto:
                        adicionarProduto();
                    break;
                case R.id.idBtnSalvarLista:
                        salvarLista();
                    break;
            }//switch
        }//onClick
    };//mHandlerClickButton

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_lista);

        init();
    }//onCreate

    private void iniciandoListView(){
        mLvListaProdutos = findViewById(R.id.idLvListaProdutos);
        mListaProdutos = new ArrayList<>();
        mAdListaProdutos = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, mListaProdutos);
        mLvListaProdutos.setAdapter(mAdListaProdutos);
        mLvListaProdutos.setOnItemLongClickListener(mHandlerLongItemListener);
    }//iniciandoListView

    private void associandoHandlerAosBotoes(){
        Button[] buttons = {mBtnSalvarLista, mBtnAdicionaProduto};
        for (Button b : buttons) b.setOnClickListener(mHandlerClickButton);
    }//associandoHandlerAosBotoes


    void init(){
        //atribuindo o contexto
        mContext = this;
        //Associando java ao XML
        mEtNomeProduto = findViewById(R.id.idEtNomeProduto);
        mEtNomeProduto.requestFocus();
        mBtnAdicionaProduto = findViewById(R.id.idBtnAdicionaProduto);
        mBtnSalvarLista = findViewById(R.id.idBtnSalvarLista);
        mTvNomeLista = findViewById(R.id.idTvNomeLista);

        //associando handler aos botoes
        associandoHandlerAosBotoes();
        //inicando o ArrayList, ArrayAdapter e ListView
        this.iniciandoListView();

        //recebendo o intent da outra activity
        this.recebendoIntentActivityPrincipal();
    }//init

    //Recebendo produto da intent com o nome da lista da activity principal
    void recebendoIntentActivityPrincipal(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mNomeLista = bundle.getString(ActivityPrincipal.CHAVE);
        mTvNomeLista.setText(mNomeLista);
        mTvNomeLista.setTextSize(30);
    }//recebendoIntentActivityPrincipal

    //metodo ao dar um click longo em um item do listView
    void removerItemDaListView(int position){
       if (mListaProdutos.contains(mListaProdutos.get(position))){
           String nomeProduto = mListaProdutos.get(position);
           String strMsg = String.format(getResources().getString(R.string.strProdutoExcluido), nomeProduto);
           mListaProdutos.remove(position);
           mAdListaProdutos.notifyDataSetChanged();
           ClasseAuxiliar.mostrarMensagem(mContext, strMsg);
       }//if

    }//removerItemDaListView

    //metodos que respondem ao clicar no botao
   void adicionarProduto(){
        String s = mEtNomeProduto.getText().toString().trim();
        if (!s.equals("")){
            mListaProdutos.add(0,mEtNomeProduto.getText().toString());
            mEtNomeProduto.setText("");
            mEtNomeProduto.requestFocus();
            mAdListaProdutos.notifyDataSetChanged();
        }else{
            Toast.makeText(mContext, getResources().getText(R.string.strCampoVazio), Toast.LENGTH_SHORT).show();
            mEtNomeProduto.setCursorVisible(true);
        }
   }//adicionarProduto


    void salvarLista(){
        boolean bDeuCerto, bVerificaSeAListaExiste;
        bVerificaSeAListaExiste = mListadb.verificaSeAListaExiste(mContext,mNomeLista);
        if(!bVerificaSeAListaExiste){
            bDeuCerto = mListadb.salvarLista(mListaProdutos, mNomeLista, mContext);
            if(bDeuCerto){
                ClasseAuxiliar.mostrarMensagem(mContext, getResources().getString(R.string.strDeuCerto));
                finish();
            }

        }//if(!bVerificaSeAListaExiste)
    }//

}
