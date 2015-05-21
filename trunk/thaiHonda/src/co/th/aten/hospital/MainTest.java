/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.ejb.Local;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Years;
import org.joda.time.base.BasePeriod;

/**
 *
 * @author Atenpunk
 */
public class MainTest {

    public static void main(String[] args) {
        String dateStart = "01/11/2012 09:29:58";
        String dateStop = "02/12/2013 10:31:48";

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            DateTime dt1 = new DateTime(d1);
            DateTime dt2 = new DateTime(d2);

            System.out.print(Years.yearsBetween(dt1, dt2).getYears() + " Years, ");
            System.out.print(Months.monthsBetween(dt1, dt2).getMonths()%12 + " Months, ");
            System.out.print(Days.daysBetween(dt1, dt2).getDays() + " days, ");
            System.out.print(Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, ");
            System.out.print(Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes, ");
            System.out.print(Seconds.secondsBetween(dt1, dt2).getSeconds() % 60 + " seconds.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
