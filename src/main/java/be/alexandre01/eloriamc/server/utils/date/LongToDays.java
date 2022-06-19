package be.alexandre01.eloriamc.server.utils.date;

import java.util.concurrent.TimeUnit;

public class LongToDays {
    public static String format(long l){
        int day = (int) TimeUnit.MILLISECONDS.toDays(l);
        long hours = TimeUnit.MILLISECONDS.toHours(l) - (day *24);
        long minute = TimeUnit.MILLISECONDS.toMinutes(l) - (TimeUnit.MILLISECONDS.toHours(l)* 60);
        long second = TimeUnit.MILLISECONDS.toSeconds(l) - (TimeUnit.MILLISECONDS.toMinutes(l) *60);
        StringBuilder sb = new StringBuilder();
        if(day > 0){
            sb.append(day).append(" jour(s) ");
        }
        if(hours > 0){
            sb.append(hours).append(" heure(s) ");
        }
        if(minute > 0){
            sb.append(minute).append(" minute(s) ");
        }
        sb.append(second).append(" seconde(s)");
        return sb.toString();
        //return this.format.format(l);
    }

}
