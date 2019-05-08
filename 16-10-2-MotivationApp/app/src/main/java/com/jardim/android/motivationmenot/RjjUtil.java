package com.jardim.android.motivationmenot;

import android.app.Activity;
import android.widget.Toast;

import java.util.Random;

/*
    noutra class
    sendo metodo estatico
    int iQueroUmInteiro = rjjUltil.
 */
public class RjjUtil {

    /*
    qualauqer metodo utilitario que venha a necessitar de uma chamada android
    precisara de ser informado sobre em que Activity ocorrera a chamada.Por isso,
    uma class como este que disponibilize meotdos como ese, tem que estar informado sobre a activity
    em que os metodos irao funcionar, tendo-a como membro de dados recebendo-a como parametros
     */
    Activity mActivity;
    /*
    Constructor method
    receives the calling Activity as parameter
    this will be needed for any Android specific code
    NOT needed for generic Java needs, such as ramdomInt
     */
    public RjjUtil(Activity pA){
        this.mActivity = pA;
    }//fim do RjjUtil

    /*
        Dynamic method => will require an instance of RjjUtil to bem called
    */
    public void displayMessage(String pStrMessageToBeDisplayed){
        Toast t = Toast.makeText(
                    this.mActivity, //Contexto
                        pStrMessageToBeDisplayed,//mensagem que aparecerá
                            Toast.LENGTH_SHORT);//tempo de permanencia
        t.show();
    }//fim de metodo displayMessage
    public static int randomInt(int piMin, int piMax){
        //implementação
        /*
            piMin = a P de parameto I de inteiro
           a amplitude é feita tirando o maximo pelo minino mais um;
         */
        Random r = new Random();
        int iAmplitude = piMax-piMin+1;
        int iJump = r.nextInt(iAmplitude);
        int iLand = piMin + iJump;
        return iLand;

    }//fim randomInt
}//fim da classe RjjUtil
