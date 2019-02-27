package com.jardim.android.listadecompras; // trocar a package quando for pro projeto final

import android.content.Context;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;



public class SalvarLista {
    //private static String SALVO_COM_SUCESSO = "A lista foi salva com sucesso";
    private static String QUEBRA_DE_LINHA = "\n";
    private  static  String TAG = "SalvaLista";

    public boolean verificaSeAListaExiste(Context pContext,String pNomeDoArquivo){
        File verArquivos = pContext.getFilesDir(); // /Cria uma variável para manipular o diretório
    //    ArrayList<String> arListas = new ArrayList<>();
        String[] strListaArquivos = verArquivos.list();
        for(String str : strListaArquivos){
            if(str.equals(pNomeDoArquivo)){
                return true;
            }
        }
        return false;
    }//verificaSeAListaExiste
    public boolean salvarLista(ArrayList<String> pStringsASalvar, String pNomeDoArquivo , Context pContext){ // Não se esqueça de utilizar verificaSeAListaExiste na activiy
       // PrintWriter printWriter;
        FileOutputStream outputStream; // Objeto usado para salvar os dados no arquivo
        String strAListaInteira = ""; // String onde o ArrayList será formatado para ser salvo;
            for (int i = 0; i < pStringsASalvar.size(); i++) {
                strAListaInteira = strAListaInteira + pStringsASalvar.get(i) + "\n";
            }
            try {
                outputStream = pContext.openFileOutput(pNomeDoArquivo, pContext.MODE_PRIVATE); // Abre o arquivo a ser escrito, de moto privado para que apenas esta aplicação tenha aces
                outputStream.write(strAListaInteira.getBytes());
                outputStream.close();
                return true;

            } catch (Exception e) {
                e.printStackTrace();
                return false;

            }

    }

    public ArrayList<String> resgatarLista(String pNomeDoArquivo, Context pContext){
        ArrayList<String> alreturn = new ArrayList<>();

        try {
            FileInputStream fin = pContext.openFileInput(pNomeDoArquivo);
            int iTamanhoDoArquivo;
            String strNovoItem = "";
            String strAuxiliar;

            // Lê dentro do arquivo até ele chegar ao fim (-1)
            while ((iTamanhoDoArquivo = fin.read()) != -1) {
              strAuxiliar =  Character.toString((char) iTamanhoDoArquivo);
              if(!strAuxiliar.equals(QUEBRA_DE_LINHA)){ // verifica se é uma quebra de linha, que signica uma nova palavra;
                    // add & append content
                    strNovoItem += Character.toString((char) iTamanhoDoArquivo);
              }else{
                  alreturn.add(strNovoItem);
                  strNovoItem = "";
              }
            }
            fin.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return alreturn;
    }

    public ArrayList<String> verListas(Context pContext){
        File verArquivos = pContext.getFilesDir(); // /Cria uma variável para manipular o diretório
        ArrayList<String> arListas = new ArrayList<>();
        String[] strListaArquivos = verArquivos.list();
        if(strListaArquivos == null){
            Log.e(TAG, "Diretório não encontrado");
        }else if(strListaArquivos.length != 0){
            for(String str : strListaArquivos) {
                arListas.add(str);

            }
            return arListas; // caso exista pelo menos um arquivo, retorna todos os arquivos
        }//for
        return  arListas; // Case seja null ( diretório inexistente)
    }//verListas

    public boolean deletarLista(Context pContext, String pNomeDoArquivo){
        File verArquivos = new File(pContext.getFilesDir(),pNomeDoArquivo); // /Cria uma variável para manipular o diretório
        boolean bCheck;
        bCheck = verArquivos.delete(); // Deleta o arquivo
        return bCheck;
    }

}//SalvarLista



