package com.example.wikipedia;

import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeTester {

    public static int Smsec;
    public static int Ssec;
    public static int Emsec;
    public static int Esec;

    public static void startTime(){
        Calendar calendar = new GregorianCalendar();
        Smsec = calendar.get(Calendar.MILLISECOND);
        Ssec = calendar.get(Calendar.SECOND);
        Log.d("_TIME_", " START " + Ssec + " "+ Smsec + " ");
    }

    public static void endTime(){
        Calendar calendar = new GregorianCalendar();
        Emsec = calendar.get(Calendar.MILLISECOND);
        Esec = calendar.get(Calendar.SECOND);
        Log.d("_TIME_", " End " + Esec + " "+ Emsec + " ");
        myTime();
    }

    private static void myTime(){

        int sec = Esec - Ssec;
        if(sec < 0){
            sec = 60-sec;
        }

        Emsec = Emsec + sec*1000;
        int msec = Emsec - Smsec;

        if(Emsec < Smsec){
            msec = 1000-msec;
        }


        Log.d("_TIME_",  " "+ msec + " ");
    }

}
