package co.th.aten.hospital.util;

import co.th.aten.hospital.Constants;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Years;

public class Util {

    public static Date extractFromTrxDat(String fileName) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = fileName.substring(2, 16);
        System.out.println("dateStr=" + dateStr);
        return sdf.parse(dateStr);
    }

    public static Date extractFromFileDat(String fileName) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_FILE_DATETIME);
        String[] token = fileName.split("-");
        return sdf.parse(token[1]);
    }

    public static Date extractFromFileName(String fileName) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmssSSS");
        int idx = fileName.indexOf(".xml");
        String strDate = fileName.substring(idx - 18, idx);
        return sdf.parse(strDate);
    }

    public static double nullSafeParseDouble(String s) {

        try {
            if (s != null && s.length() > 0) {
                return Double.parseDouble(s);
            }
        } catch (NullPointerException e) {
            // System.out.println("parse double error null");
        }
        return 0;
    }

    public static Long nullSafeParseLong(String s) {
        try {
            if (s != null && s.length() > 0) {
                return Long.parseLong(s);
            }
        } catch (NullPointerException e) {
            // System.out.println("parse long error null");
        }
        return null;
    }

    public static Short nullSafeParseShort(String s) {
        try {
            if (s != null && s.length() > 0) {
                return Short.parseShort(s);
            }
        } catch (NullPointerException e) {
            // System.out.println("parse short error null");
        }
        return null;
    }

    public static Short nullSafeParseShort(String s, int radix) {
        try {
            if (s != null && s.length() > 0) {
                return Short.parseShort(s, radix);
            }
        } catch (NullPointerException e) {
            // System.out.println("parse short error null");
        }
        return null;
    }

    public static Integer nullSafeParseInt(String s) {
        try {
            if (s != null && s.length() > 0) {
                return Integer.parseInt(s);
            }
        } catch (NullPointerException e) {
            // System.out.println("parse int error null");
        }
        return null;
    }

    public static Integer nullSafeParseInt(String s, int radix) {
        try {
            if (s != null && s.length() > 0) {
                return Integer.parseInt(s, radix);
            }
        } catch (NullPointerException e) {
            // System.out.println("parse int error null");
        }
        return null;
    }

    public static Float nullSafeParseFloat(String s) {
        try {
            if (s != null && s.length() > 0) {
                return Float.parseFloat(s);
            }
        } catch (NullPointerException e) {
            // System.out.println("parse float error");
        }
        return null;
    }

    public static Date nullSafeParseDate(String s) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_DATETIME, Locale.US);
        return nullSafeParseDate(s, sdf);
    }

    public static Date nullSafeParseDate(String s, String format, Locale locale) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        return nullSafeParseDate(s, sdf);
    }

    public static Date nullSafeParseDate(String s, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return nullSafeParseDate(s, sdf);
    }

    public static Date nullSafeParseDate(String s, SimpleDateFormat sdf) throws ParseException {
        try {
            if (s != null && s.trim().length() > 0) {
                return sdf.parse(s);
            }
        } catch (NullPointerException e) {
            System.out.println("parse date error null");
        }
        return null;
    }

    public static String nullToBlank(String str) {
        return (str == null ? "" : str);
    }

    public static String nullSafeDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return (date == null ? "" : sdf.format(date));
    }

    public static int nullSafeInt(Integer integer) {
        return (integer == null ? 0 : integer);
    }

    public static String nullSafeString(String _str) {
        return (_str == null ? "" : _str);
    }

    public static long nullSafeLong(Long _long) {
        return (_long == null ? 0 : _long);
    }

    public static short nullSafeShort(Short sh) {
        return (sh == null ? 0 : sh);
    }

    public static double nullSafeDouble(Double db) {
        return (db == null ? 0.0 : db);
    }

    public static double nullSafeBigDec(BigDecimal bd) {
        return (bd == null ? 0.0 : bd.doubleValue());
    }

    public static String nullSafeIntToStr(Integer integer) {
        return (integer == null ? "" : integer.toString());
    }

    public static String nullSafeLongToStr(Long _long) {
        return (_long == null ? "" : _long.toString());
    }

    public static String nullSafeShortToStr(Short sh) {
        return (sh == null ? "" : sh.toString());
    }

    public static double excludeVAT(double amount, float vatPercent) {
        double afterVat = amount - ((amount * vatPercent) / (100 + vatPercent));
        return afterVat;
    }

    public static double calVAT(double amount, float vatPercent) {
        double vat = (double) (amount * vatPercent) / (100 + vatPercent);
        return vat;
    }

    public static String getYearMonth(Date startDate, Date endDate) {
        DateTime dt1 = new DateTime(startDate);
        DateTime dt2 = new DateTime(endDate);
        String yearMonth = Years.yearsBetween(dt1, dt2).getYears() != 0 ? Years.yearsBetween(dt1, dt2).getYears() + " years, " : "";
        yearMonth += (Months.monthsBetween(dt1, dt2).getMonths() % 12 + " months ");
        return yearMonth;
    }
    
    public static String getYear(Date startDate, Date endDate) {
        DateTime dt1 = new DateTime(startDate);
        DateTime dt2 = new DateTime(endDate);
        String year = Years.yearsBetween(dt1, dt2).getYears() != 0 ? Years.yearsBetween(dt1, dt2).getYears() + "" : "";
        return year;
    }
}
