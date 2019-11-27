import java.util.Calendar;
import java.util.Date;

public class Birthday {
    private int day; //убрать статик //разобраться
    private int month;
    private int year;
    private Date date;

    public Birthday() {
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);
        date = new Date(); //не использоват (рекомендация)
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
