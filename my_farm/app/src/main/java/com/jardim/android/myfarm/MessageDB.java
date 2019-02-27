package com.jardim.android.myfarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MessageDB extends SQLiteOpenHelper {
    /*
        sqlite.org
     */

    public final static String DATABASE_NAME = "MESSAGES.DB";
    public final static int DATABASE_VERSION = 1;
    //not so good 1st version
   /* public final String SQLITE_CREATE_MESSAGES_TABLE =
            "create table if not exists t_messages ("+
            "_id integer not null autoincrement, "+
            "col_msg text )";*/
    public final static String TABLE_MESSAGES = "t_messages";
    public final static String COL_ID = "_id";
    public final static String COL_MESSAGE = "col_msg";
    public final String SQLITE_CREATE_MESSAGES_TABLE =
            "create table if not exists "+ TABLE_MESSAGES +"("+
                    COL_ID+ " integer primary key autoincrement, "+
                    COL_MESSAGE+ " )";
    public String getSQLiteStatementForTableMessagesCreation(){
        String ret  = "";
        ret = String.format(
          "CREATE TABLE IF NOT EXISTS %s ("+
          "%s INTEGER PRIMARY KEY AUTOINCREMENT, "+
          "%s TEXT )",
          TABLE_MESSAGES,COL_ID,COL_MESSAGE
        );

        return ret;
    }//getSQLiteStatementForTableMessagesCreation

   /* public final static String SQLITE_DROP_TABLE_MESSAGES =
            "drop table if exists t_messages";*/

    public final static String SQLITE_DROP_TABLE_MESSAGES =
            "drop table if exists " + TABLE_MESSAGES;

    public String getSQLiteStatementForTableMessagesDestruction(){
        String ret = "";
        ret = String.format("DROP TABLE IF EXISTS %s", TABLE_MESSAGES);

        return ret;
    }
    public MessageDB(Context pContext){
        this(pContext, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public MessageDB(
             Context context,
             String name,//nome do banco
             SQLiteDatabase.CursorFactory factory,//null
             int version//versao do banco
    ){
        super(context, name, factory, version);
    }

    /*
        nunca chamaremos explicitamente
        este metodo sera chamado automaticamente pelo sistema android, quando
        houver a orumeira necessidade qualquer sobre
        a base de dadoss, por exemplo, um primeiro select, um primeiro insert

        this the method that must create the database indrastructure
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        installDB(db);
    }//onCreate

    /*
        este metodo sera automaticamente chamado pelo android
        quando houve mudanças de versao de base de dados
        neste momento deveriamos destruir a infraestrutura
        (na melhor pratica, antes da destruição fazer um backup dos dados anteriores)
        e deois construir a nova versao
    */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Boolean querFazerUpgrade = newVersion > oldVersion;
        if (querFazerUpgrade){
            unistallDB(db);
            installDB(db);
        }
    }//onUpgrade

    public static final String TAG = "@MessagesDB";
   void installDB(
       SQLiteDatabase pDB //
   ){
        if (pDB != null){
               String sql = getSQLiteStatementForTableMessagesCreation();
           try{
               pDB.execSQL(sql);
           }//try
           catch (Exception e){
               Log.e(TAG ,e.getMessage());
           }//catch

        }//if
   }//unistallDB

    void unistallDB(
            SQLiteDatabase pDB //
    ){
        if (pDB != null){
            try{
                pDB.execSQL(getSQLiteStatementForTableMessagesDestruction());
            }//try
            catch (Exception e){
                Log.e(TAG ,e.getMessage());
            }//catch

        }//if
    }//unistallDB

    public long addMessage(String pMsg){
       SQLiteDatabase dbw = this.getWritableDatabase();
       long retorno = -2;
       ContentValues values = new ContentValues();
       values.put(COL_MESSAGE, pMsg);
       if (dbw!=null){
          retorno = dbw.insert(TABLE_MESSAGES,//nome da tabela
                   null,//quando quer permitir que algum campi seja nulo
                    values);
          dbw.close();
       }
        return retorno;
    }//addMessage

    public ArrayList<String> selectAllMessages(){
       ArrayList<String> ret = new ArrayList<>();
       SQLiteDatabase dbr = this.getReadableDatabase();

       if (dbr != null){
           //String strQueryTemplate = "SELECT %s, %s FROM %s";
           String strQuety = String.format("SELECT * FROM "+TABLE_MESSAGES);
           String[] filters = null;
           Cursor results =  dbr.rawQuery(strQuety, filters);

           int ihowManyResults = results.getCount();
           String[] straCoumnNames = results.getColumnNames();

           if (results!=null){
               results.moveToFirst();
               for (int i = 0; i < ihowManyResults; i++){
                   int id =  results.getInt(results.getColumnIndex(COL_ID));
                   String strMsg = results.getString(results.getColumnIndex(COL_MESSAGE));
                   ret.add(strMsg);
                   results.moveToNext();
               }
           }//if
           dbr.close();
       }//if
        return ret;
    }//selectAllMessages

    public void deleteAllMessages(){
            SQLiteDatabase dbw = this.getWritableDatabase();

            dbw.delete(TABLE_MESSAGES, null, null);

    }//deleteAllMessages
    public long writeAllMessages(ArrayList<String> pMsgs){
       this.deleteAllMessages();
       long retorno = -2;

           for (int i = 0; i < pMsgs.size(); i++)
                this.addMessage(pMsgs.get(i));

       return retorno;
    }
}//MessageDB
