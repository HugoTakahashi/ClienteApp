package br.com.diaristaslimpo.limpo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 20/02/2016.
 */
public class DateUtil {

    public static Date getDate(int year, int monthOfYear, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        Date date = calendar.getTime();

        return date;
    }


    public static String dateToString(int year, int monthOfYear, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        Date date = calendar.getTime();

        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String dt = format.format(date);

        return dt;
    }

    public static String dateToJson(int year, int monthOfYear, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        Date date = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dt = format.format(date);

        return dt;
    }

    public static String dateToString(Date date) {


        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String dt = format.format(date);

        return dt;
    }
}
