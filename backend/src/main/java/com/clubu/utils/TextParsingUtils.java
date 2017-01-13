package com.clubu.server.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TextParsingUtils {
    
    public static Date parseDate(String date) {
        if (date == null) {
            return null;
        }
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("America/Toronto"));
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return null; 
        }
    }

}
