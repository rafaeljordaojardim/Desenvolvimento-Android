package com.jardim.android.listadecompras;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class ClasseAuxiliar {

    public static void mostrarMensagem(Context pContext, String pMensagem){
        if(pContext != null && pMensagem != null)
            Toast.makeText(pContext, pMensagem, Toast.LENGTH_SHORT).show();
    }//mostrarMensagem


}//ClasseAuxiliar
