package com.jardim.android.listadecompras;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityComprando extends AppCompatActivity {

    public static final String KEY_BUNDLE = "KEY_BUNDLE";
    int salvarUltimaPosicao;
    //handler para botão fecharCompra
   /* Button.OnClickListener mHandlerClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.idBtnFecharCompra:
                        fecharCompra();
                    break;
            }//switch
        }//onClick
    };*/

    //Handler para ListView
    ListView.OnItemLongClickListener mHandlerClickListenerColor = new ListView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(mListaProdutosBoolean[position]){
                    parent.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                    mListaProdutosBoolean[position] = false;
                    int quantidade = atualizaItensSelecionados();
                }else{
                    parent.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    mListaProdutosBoolean[position] = true;
                   int quantidade = atualizaItensSelecionados();
                }
            return true;
        }//onItemLongClick
    };//mHandlerClickListener

    //declarando SalvarLista
    SalvarLista salvarLista = new SalvarLista();
    //Declarando contexto
    Context mContext;
    //Declarando membros de dados
    TextView mTvQuantidadeDeItens;//mostra quantos itens há na lista
    TextView mTvQuandidadeDeItensSelecionados;//mostra na tela quantos itens estão selecionados
    Button mBtnFecharCompra;

    boolean[] mListaProdutosBoolean;
    ArrayList<String> mListaProdutos;
    ListView mLvComprando;
    ArrayAdapter<String> mAdLvComprando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprando);

        init(savedInstanceState);
    }//onCreate

    void iniciandoListView(){
        mLvComprando = findViewById(R.id.idLvComprando);
        mLvComprando.setOnItemLongClickListener(mHandlerClickListenerColor);
        mListaProdutos = new ArrayList<>();
        mAdLvComprando = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, mListaProdutos);
        mLvComprando.setAdapter(mAdLvComprando);

    }//iniciandoListView
    void init(Bundle pBundle){
        mContext = this;//contexto
        mTvQuantidadeDeItens = findViewById(R.id.idTvQuantidadeDeItens);//associando java ao xml
        mTvQuandidadeDeItensSelecionados = findViewById(R.id.idTvQuantidadeDeItensSelecionados);
        //mBtnFecharCompra = findViewById(R.id.idBtnFecharCompra);
       // mBtnFecharCompra.setOnClickListener(mHandlerClickListener);
        //iniciando listView
        this.iniciandoListView();
        this.receberIntentVerListas();
    }//init

    void receberIntentVerListas(){
       Intent intent = getIntent();
       String nomeLista = intent.getStringExtra(ActivityVerListas.CHAVE_VER_LISTAS);
       ArrayList<String> pLista = salvarLista.resgatarLista(nomeLista, mContext);

       mListaProdutos.addAll(pLista);
       mListaProdutosBoolean = new boolean[mListaProdutos.size()];

       String strMsg = String.format(
               getResources().getString(R.string.strQuantidadeDeItens),
               mListaProdutos.size());

       mTvQuantidadeDeItens.setText(strMsg);

       strMsg = String.format(getResources().getString(R.string.strQuantidadeQueFaltaComprar), mListaProdutos.size());
       mTvQuandidadeDeItensSelecionados.setText(strMsg);
    }//receberIntentVerListas

    void fecharCompra(){
            int quantidade = atualizaItensSelecionados();
        if (quantidade > 0){
                String strMsg = String.format(getResources().getString(R.string.strQuantidadeQueFaltaComprar), quantidade );
                ClasseAuxiliar.mostrarMensagem(mContext, strMsg);
        }else{
            Intent intent = new Intent(mContext, ActivityPrincipal.class);
            finish();
            startActivity(intent);
        }

    }//fecharCompra

    //metodo que atualiza a quantidade de itens selecionados
    private int atualizaItensSelecionados(){
        int quantidade = 0;
        for(int i = 0; i < mListaProdutosBoolean.length; i++){
            if(!mListaProdutosBoolean[i])quantidade++;
        }//for
        String strMsg = String.format(getResources().getString(R.string.strQuantidadeQueFaltaComprar),quantidade);
        mTvQuandidadeDeItensSelecionados.setText(strMsg);
        return quantidade;
    }//atualizaItensSelecionados

    //colocando menu

    @Override
    public boolean onCreateOptionsMenu(Menu pMenu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity_comprando, pMenu);
        return super.onCreateOptionsMenu(pMenu);
    }//onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        switch (pItem.getItemId()){
            case R.id.idMenuFecharCompra:
                this.fecharCompra();
                break;
            case R.id.idMenuTelaInicial:
                   irParaTelaInicial();
                break;
        }//switch
        return super.onOptionsItemSelected(pItem);
    }

    //mentodo par air a tela inicial
    void irParaTelaInicial(){
        Intent intent = new Intent(mContext, ActivityPrincipal.class);
        finish();
        startActivity(intent);
    }//irParaTelaInicial
}//fim da classe
