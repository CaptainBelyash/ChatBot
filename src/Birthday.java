import java.util.Calendar;

public class Birthday {
    private static int day;
    private static int month;
    private static int year;

    public Birthday(){
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);
    }
    //TODO: метод получения возраста

    public static String getBirthday(){
        return day + "." + month + "." + year;
    }
}
