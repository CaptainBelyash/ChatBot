import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Birthday {
    private static int day;
    private static int month;
    private static int year;
    private static Date date;

    public Birthday() {
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);
        date = new Date();
    }

    public String getBirthday() {
        return day + "." + month + "." + year;
    }

    public int getAge() {
        Date now = new Date();
        long milliseconds = now.getTime() - date.getTime();
        return (int) (milliseconds / (24 * 60 * 60 * 1000));
    }
}
