package com.jardim.android.distancebetweendates;

import android.widget.CalendarView;

import java.util.Calendar;

public class RjjCalendar {
    Calendar mCal;
    //membros de dados para representar em permanencia o ano, o mes e o dias
    private int mYear, mMonth, mDay;

    public int getYear(){return this.mYear;}
    public int getMonth(){return this.mMonth;}
    public int getDay(){return this.mDay;}

    //default baseado em calendar instances
    public RjjCalendar(){
        //calendario corrente do sistema
        this.mCal = Calendar.getInstance();
        this.mYear = mCal.get(Calendar.YEAR);
        //OS MESES DE OBJETOS CALENDAR SAO BASEADAS EM ZERO
        //OU SEJA JANEIRO 0 E 11 PARA DEZEMBRO
        this.mMonth = mCal.get(Calendar.MONTH)+1;//+1 para tornar mais natural o mes
        this.mDay = mCal.get(Calendar.DAY_OF_MONTH);
    }//com.jardim.android.distancebetweendates.RjjCalendar

    //um Construtor alternativo
    public RjjCalendar(CalendarView pCv){
        //a calendar view representa as daras como referencias em
        //ms ao "unix epoch" (1970-01-01)
        long ms = pCv.getDate();
        Calendar calendario = Calendar.getInstance();

        //como que converter de ms para calendar
        calendario.setTimeInMillis(ms);

        //temos que comsiderar os nossos membros de dados
        this.mCal = (Calendar) calendario.clone();
        this.mYear = mCal.get(Calendar.YEAR);
        //OS MESES DE OBJETOS CALENDAR SAO BASEADAS EM ZERO
        //OU SEJA JANEIRO 0 E 11 PARA DEZEMBRO
        this.mMonth = mCal.get(Calendar.MONTH)+1;//+1 para tornar mais natural o mes
        this.mDay = mCal.get(Calendar.DAY_OF_MONTH);
    }//com.jardim.android.distancebetweendates.RjjCalendar


    /*
        v1 que nao assegura uma consistencia de apresentacao dass datas
     */
    public String ymd1(){
        return String.format("%d-%s%d-@s%d", mYear, mMonth, mDay);
    }//ymd

    public String ymd(){
        String strYear, strMonth, strDay;
        strYear = numberWithXDigits(this.mYear, 4);
        strMonth = numberWithXDigits(this.mMonth, 2);
        strDay = numberWithXDigits(this.mDay, 2);
        return String.format("%s-%s-%s",strYear, strMonth, strDay);
    }//ymd


    String numberWithXDigits(
            int pNumero,//numero que eu quero apresentar
            int pDigitos){//com x digitos
        String strN = String.valueOf(pNumero);
        int iQuantosSimbolos = strN.length();

        Boolean bPossivelApresentar = pDigitos >= iQuantosSimbolos;

        if (bPossivelApresentar){
            while ((iQuantosSimbolos=strN.length()) < pDigitos){
                //ir concatenando a esquerda ate
                //apresentação para o n respeitar a quantidade de
                //pDigitos requisitada
                strN = "0"+strN;
            }//while
            return strN;
        }else
        {
            //nao foi ppssivel apresentar com n difigtos
            //porque isso apresentaria uma invertade matematica
            //po isso retornamos o numero sem alterações
            return strN;
        }//else
    }
}//com.jardim.android.distancebetweendates.RjjCalendar
