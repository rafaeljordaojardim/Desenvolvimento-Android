package com.jardim.android.motivationmenot;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //declaração de membro de dados

    RjjUtil mUtil;
    SeekBar mSbProbPositiveMsg;
    Button mBtnGetMsg;
    TextView mTvFeedback;
    TextView mTvPorcent;

    /*
    tring[] mPositiveMessages = new String[]{
          "Just do it.",
            "Tomorrow is another day.",
          "Don\'t worry, there is a solution" ,
            "Take it easy, don\'t strees out",
            "You can easily do it!"
    };

    String[] mNegativeMessages = new String[]{
            "You should have worked more.",
            "Now you pay for your mistakes!",
            "If you only have listened to me.",
            "Bad things will happen today.",
            "I see back skies ahead."
    };
     */


    //just declare java arrays fir the positive message and negative messages
    String[] mPositiveMessages, mNegativeMessages;

    //Membro de dados para o listener
    //1 - Declarar os objetos para quais temos uma ideia
    //ter um tratador para qualquer botao
    Button.OnClickListener mButtonClickHandler;
    //objetivo: ter um objeto tratador de mudanças em qq SB
    SeekBar.OnSeekBarChangeListener mSeekBarChangeHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motivate_me_not);

        init();
    }//fim on create

   void init(){
        mUtil = new RjjUtil(this);

        mPositiveMessages = getResources().getStringArray(R.array.arrayPositiveSentences);
        mNegativeMessages = getResources().getStringArray(R.array.arrayNegativeSentences);

        //1 - associates de elementos XML a variaveis do java
        mBtnGetMsg = (Button) findViewById(R.id.idBtnGetMsg);
        mSbProbPositiveMsg = (SeekBar) findViewById(R.id.idSbProbPositive);
        mTvFeedback = (TextView) findViewById(R.id.idTvFeedback);
        mTvPorcent = (TextView) findViewById(R.id.idTvPorcent);//setando mais um campo para mostrar a porcentagem
       // 2- bahaviors via listeners atribuidos as variaveis do java
       /*Responder a touchees/clicks/ toques no mBtnGetMsg
       Nao é obvio, nemd tinha que ser feito, mas faremos
       vamos conferir alguns comportamentos à seekBar MSbprobPositiveMsg
       tudo para recordar que ha um padrao de comportamento
       1) pensar no(s) Listener(s) adequados(s)
       2) instanciar o listener -> obriga fazer override de metodos
       3)associar o listener aos objetos compativeis que queiramos que se comportem de acordo com essa instancia
       */
       //colocando a progress na hora que inicializa
       String mSbProgress = String.valueOf(mSbProbPositiveMsg.getProgress());
       mTvPorcent.setText(mSbProgress + "%");//fim de colocar
       //2 - intanciação de listeners
       mButtonClickHandler = new Button.OnClickListener() {
           @Override
           //view representa o metodo button
           public void onClick(View v) {
               int iWhoWasClicked = v.getId();

               switch (iWhoWasClicked){
                   case R.id.idBtnGetMsg:
                       //produzirMensagemAleatoriaRespeitandoProbabilidade();
                       pickRandomMessageAndDisplay();
                       break;
                 /*  case R.id.idoutrobotao;

                        break;
                    case R.id.outrobotao;

                        break;*/
               }//fim do switch
           }//fim do metodo onClick
       };//fim do lisntener

       /*
         String strMessage = String.format("Tracking started at value %d", //format expression
                         seekBar.getProgress());
                mUtil.displayMessage(strMessage);
        */
       mSeekBarChangeHandler = new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged
           (SeekBar seekBar, int progress, boolean fromUser) {

               String porcent = String.valueOf(progress);
               mTvPorcent.setText(porcent + "%");
           }//onProgressChanged

           @Override
           public void onStartTrackingTouch
           (SeekBar seekBar) {

           }// onStartTrackingTouch

           @Override
           public void onStopTrackingTouch
           (SeekBar seekBar) {
                //display some message qhen change stops
               String strMessage = String.format("Tracking Stoped at value %d", //format expression
                       seekBar.getProgress());//parametros do progresso da barra
               mUtil.displayMessage(strMessage);
           }//onStopTrackingTouch
       };//fim do listenrt SeekBar

       //passo3
       //momento de assosciar listeners instanciados a objetos que queiramos
       //que se comportem de acordo com o programado nesses listeners
       mBtnGetMsg.setOnClickListener(mButtonClickHandler);
       mSbProbPositiveMsg.setOnSeekBarChangeListener(mSeekBarChangeHandler);
   } //fim do init

    /*metodos com nome diferente, fazendo exatamente o mesmo ie, um chamando o outro;

     */
    void genetareRandomMessageRespectingOdds(){
        produzirMensagemAleatoriaRespeitandoProbabilidade();//chamando o metodo de baixo
    }//fim do genetareRandomMessageRespectingOdds()
    void  produzirMensagemAleatoriaRespeitandoProbabilidade(){
       // consultar a probabilidade q fiou escolhida na SeekBar
        int iUserSelectedProbabilityOfPositiveMessage = mSbProbPositiveMsg.getProgress();

        //Sabendo esta probabilidade, gerar uma mensagem positiva, ou negativa, aleatoriamente
        //sortear de onde? onde esta o saco das mensagens?
        String strRandomMessageRespectingOdds = getRandomMessage(iUserSelectedProbabilityOfPositiveMessage);

        //tendo sorteado a mensagem, faze-la aparecer na zona de feedback
        mTvFeedback.setText(strRandomMessageRespectingOdds);
    }//fim  produzirMensagemAleatoriaRespeitandoProbabilidade()



    void pickRandomMessageAndDisplay(){
         /*
      1 - have messages positive and negative, from where to randowmly pick one
      2 - pick one
      3 - display it
       */

         int iOddsOfPositiveMessage = mSbProbPositiveMsg.getProgress();

         int iRandom0to100 = RjjUtil.randomInt(0, 100);

         boolean bShouldPickPositive = iRandom0to100 <= iOddsOfPositiveMessage;

         String strRandomMsg = "";
         if(bShouldPickPositive){
             strRandomMsg = mPositiveMessages[RjjUtil.randomInt(0, mPositiveMessages.length - 1)];
         }else{
             strRandomMsg = mNegativeMessages[RjjUtil.randomInt(0, mNegativeMessages.length - 1)];
         }//fimmm

        //3 - final moment
        String strPrevious = mTvFeedback.getText().toString();
        mTvFeedback.setText(strRandomMsg+"\n"+strPrevious);
    }//fimm pickRandomMessageAndDisplay
    String getRandomMessage(int iReqProbOfPositiveMessage){
        String strRet = "";

        //helper for the generation of pesudo(pois usa o relogio do computador) random values
        //Random r = new Random();

        int iRandom0to100 = RjjUtil.randomInt(0, 100);

        /*TODO
        Temos a probabilidade solicitada de uma mensagem positiva
        temos que, de acordo com a probabilidade, ir fazer uma seleção de ensagem, + ou -, a um espaço de mensagens
        que ainda nao temos
        passo seguinte: criat sacolas de mensagens positivas e negativas
         */
        //100 <= 99 se o possoa solicitar de 0 a 100
        Boolean bPickPositive = iRandom0to100 <= iReqProbOfPositiveMessage;


        if(bPickPositive){
            int iRamdom = RjjUtil.randomInt(0, mPositiveMessages.length - 1);
            strRet = mPositiveMessages[iRamdom];
        }else{
            int iRamdom = RjjUtil.randomInt(0, (mNegativeMessages.length - 1));
            strRet = mNegativeMessages[iRamdom];
        }//fim do if
        return strRet;
    }//fim do metodo getRandomMessage


    /*random que gera numero de 0 a 100
    int randomInt(int inicio, int fim){
        Random r = new Random();
        int retorno = inicio + r.nextInt(fim);
        return retorno;
    }*/
}//fim da classe
