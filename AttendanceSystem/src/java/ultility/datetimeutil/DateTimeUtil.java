package ultility.datetimeutil;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;

public class DateTimeUtil {

    final static ZoneId ZONE_ID_VIETNAM = ZoneId.of("Asia/Ho_Chi_Minh");

    public LocalDate parseSqlDate(Date date) {
        LocalDate localDate = null;
        try {
            if (date == null) {
                return null;
            }
            localDate = LocalDate.parse(date.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localDate;
    }

    public LocalTime parseSQLTime(Time time) {
        LocalTime localTime = null;
        try {
            if (time == null) {
                return null;
            }
            localTime = LocalTime.parse(time.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localTime;
    }

    public LocalDateTime parseSqlDateTime(Timestamp date) {
        LocalDateTime localDate = null;
        try {
            if (date == null) {
                return null;
            }
            localDate = date.toLocalDateTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localDate;
    }

    public LocalDate parseUtilDate(java.util.Date Utildate) {
        LocalDate date = Utildate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return date;
    }

    public java.sql.Date parseUtilToSqlDate(java.util.Date Utildate) {
        java.sql.Date sqlDate = new java.sql.Date(Utildate.getTime());
        return sqlDate;
    }

    public LocalDate getVNLocalDateNow() {
        return LocalDate.now(ZONE_ID_VIETNAM);
    }

    public ArrayList<LocalDate> getCalendar(int year, int month) {
        ArrayList<LocalDate> list = new ArrayList<>();
        LocalDate localDate = LocalDate.of(year, month, 1);
        System.out.println(localDate.getDayOfWeek().getValue());
        System.out.println(localDate.getDayOfWeek().getValue() % 7);
        localDate = localDate.minusDays(localDate.getDayOfWeek().getValue() % 7);

        int i = 0;
        while (i != 42) {
            list.add(localDate);
            i++;
            localDate = localDate.plusDays(1);
        }
        return list;
    }

    public static void main(String[] args) {
        DateTimeUtil dateTimeUtil = new DateTimeUtil();
        int year = dateTimeUtil.getVNLocalDateNow().getYear();
        int month = dateTimeUtil.getVNLocalDateNow().getMonthValue();

        System.out.println("year: " + year);
        System.out.println("month: " + month);
    }
}
