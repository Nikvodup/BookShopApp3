package com.example.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.Util.DatePattern.*;
import static com.example.Util.DatePattern.DATE_SHORT_TIME_PATTERN;

/**
 * Cast  String to Date
 *
 * @author РРІР°РЅ РЎС‚СЂРµР»СЊС†РѕРІ
 */
public class StringToDateFormatter {

    public static final SimpleDateFormat DATE_SHORT_TIME_FORMAT = new SimpleDateFormat(DATE_SHORT_TIME_PATTERN);

    private StringToDateFormatter() {
    }

    public static Date formatToDate(String date) {
        try {
            return DATE_SHORT_TIME_FORMAT.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }
}

