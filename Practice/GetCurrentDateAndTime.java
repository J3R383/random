import java.util.*;

class GetCurrentDateAndTime {
    public static void main(String[] args) {
        int second, minute, hour;
        GregorianCalendar date = new GregorianCalendar();
        int day, month, year;
        day = date.get(Calendar.DAY_OF_MONTH);
        month = date.get(Calendar.MONTH);
        year = date.get(Calendar.YEAR);
        second = date.get(Calendar.SECOND);
        minute = date.get(Calendar.MINUTE);
        hour = date.get(Calendar.HOUR_OF_DAY);
        System.out.println("Current date is " + day + "/" + (month + 1) + "/" + year);
        System.out.println("Current time is " + hour + " : " + minute + " : " + second);
    }
}