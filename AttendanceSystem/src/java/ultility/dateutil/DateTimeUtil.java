package ultility.dateutil;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeUtil {

    public LocalDate parseSqlDate(Date date) {
        LocalDate localDate = null;
        try {
            if(date == null) return null;
            localDate = LocalDate.parse(date.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localDate;
    }

    public LocalTime parseSQLTime(Time time) {
        LocalTime localTime = null;
        try {
            if(time == null) return null;
            localTime = LocalTime.parse(time.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localTime;
    }

}
