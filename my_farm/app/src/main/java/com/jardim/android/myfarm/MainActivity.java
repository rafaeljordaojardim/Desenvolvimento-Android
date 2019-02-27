package com.jardim.android.myfarm;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MessageDB mDB; //NEW MESSAGESdb(mContext)
    Context mContext;
    ImageButton mIBtnCow, mIBtnDuck, mIBtnSheep;
    ListView mLvMsgs;
    ArrayList<String> mAlMsgs;
    ArrayAdapter<String> mAdMsgs;
    MediaPlayer mPlayer;

    ImageButton.OnClickListener mIbClickListener =
            new ImageButton.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.idIBtnCow:
                            playSound(R.raw.cow, "cow is mooing");
                            break;
                        case R.id.idIBtnDuck:
                            playSound(R.raw.duck, "duck is quacking");
                            break;
                        case R.id.idIBtnSheep:
                            playSound(R.raw.sheep, "sheep is meeing");
                            break;
                    }//switch
                }//onClick
            };//mIbClickListener
    // recursos sao todos inteiros
    void playSound(int piSounfResourceId, String pStrMsg){
        Boolean bMediaPlayerInUse = mPlayer!=null;

        if(bMediaPlayerInUse){
            boolean bMediaPlayerInPlaying = mPlayer.isPlaying();

            if (bMediaPlayerInPlaying)
            {
                mPlayer.stop();
            }
            mPlayer.release();
        }

        mPlayer = MediaPlayer.create(mContext, piSounfResourceId);
        //to Listing
        mPlayer.seekTo(0);
        mPlayer.start();

        fbMsg("Will try to play:"+pStrMsg);
    }//playSound

    void fbMsg(String pMsg){
        int iHowManyMsgs = mLvMsgs.getCount()+1;
        String strMsg = String.format("#%d : %s", iHowManyMsgs, pMsg);
        mAlMsgs.add(0, strMsg);
        mDB.addMessage(strMsg);//18-12-2018
        mAdMsgs.notifyDataSetChanged();
    }//fbMsg
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.first_layout_3_imagebuttons);
        //setContentView(R.layout.layout_3_buttons);
        setContentView(R.layout.rl_half_ibuttons_half_lv);

        init(savedInstanceState);
    }

    /*
        for state keeping/restoring, some perations could also be done here
     */
    void init(Bundle pBundleComOsDadosPreservadosAoLongoDaExistenciaDaActivity){
       mContext = this;
       mDB = new MessageDB(mContext);//18-12-2018
       mPlayer = null;
       mIBtnCow = findViewById(R.id.idIBtnCow);
       mIBtnDuck = findViewById(R.id.idIBtnDuck);
       mIBtnSheep = findViewById(R.id.idIBtnSheep);

       mLvMsgs = findViewById(R.id.idLvMsg);
       mAlMsgs = new ArrayList<>();
       mAdMsgs = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, mAlMsgs);
       mLvMsgs.setAdapter(mAdMsgs);
      // readAllMessagesFromDatabaseToDataMember();
        recuperarDados(pBundleComOsDadosPreservadosAoLongoDaExistenciaDaActivity);
       ImageButton[] teamWithTheSameHandler = {
               mIBtnCow, mIBtnDuck, mIBtnSheep
       };

        for (ImageButton ib :
                teamWithTheSameHandler) {
            ib.setOnClickListener(mIbClickListener);
        }
    }//init

    /*
        oportunidade para preservar o que decidimos preservar
        neste exercicio: as mensagens na listView

        =>memoria  nao volatil =>{sharedPreferences, ficheiros, sqlite}

        opportunity to save message, so they can be later restore
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
       // writeAllMessagesFromDataMemberToDatabase();
       /*
        exemplo de como preservar os dados numa estrutura dinamica do tipo bundle,
        qua anda a ser comnicada entre momentos do ciclo
        de existencia da activity
        */
        if(outState!=null){
            outState.putStringArrayList("KEY_MSGS", mAlMsgs);
        }//if
        super.onSaveInstanceState(outState);
    }

    /*
        oporutnidade de recuoerar os dados preservaldos
        neste caso, as mensagens
        de onde? SQLite
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //readAllMessagesFromDatabaseToDataMember();
        recuperarDados(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }//onRestoreInstanceState

   void recuperarDados(Bundle pBundle){
        if(pBundle != null){
            ArrayList<String> all = pBundle.getStringArrayList("KEY_MSGS");
            mAlMsgs.clear();
            if (all != null){
                for (String s : all) mAlMsgs.add(s);
                mAdMsgs.notifyDataSetChanged();
            }
        }//if
   }//recuperarDados
    /*
        esta ferramenta, é a ferramenta que usamos quando a recuperação de dasdos é para ser feita a partir de sqlite
     */

    void readAllMessagesFromDatabaseToDataMember(){
        ArrayList<String> all = mDB.selectAllMessages();
        mAlMsgs.clear();
        if (all != null){
            for (String s : all) mAlMsgs.add(s);
            mAdMsgs.notifyDataSetChanged();
        }

    }//readAllMessagesFromDatabaseToDataMember

    void writeAllMessagesFromDataMemberToDatabase(){
        if (mAlMsgs != null){
           long quantasMsgEscritas =  mDB.writeAllMessages(mAlMsgs);
          //  Toast.makeText(mContext,"GRAVADAS NO BANCO "+quantasMsgEscritas, Toast.LENGTH_LONG).show();
        }

    }//writeAllMessagesFromDataMemberToDatabase
}//
