package be.alexandre01.eloriamc.server.utils.date;

import java.text.SimpleDateFormat;

public class LongToDate {
    private final SimpleDateFormat format;

    public LongToDate(){
        format =  new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    public LongToDate(String pattern){
        format = new SimpleDateFormat(pattern);
    }

    public String format(long l){
        return format(l);
    }

}
