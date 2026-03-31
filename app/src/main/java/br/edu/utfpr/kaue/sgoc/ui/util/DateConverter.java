package br.edu.utfpr.kaue.sgoc.ui.util;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date toDate(Long millis) {
        return millis == null ? null : new Date(millis);
    }
}
