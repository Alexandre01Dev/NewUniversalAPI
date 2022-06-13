package be.alexandre01.eloriamc.data.punishement;

import be.alexandre01.eloriamc.data.game.Identifier;
import be.alexandre01.eloriamc.utils.TimeUnit;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.*;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ban extends Identifier {

    @Expose private boolean isBanned = false;
    @Expose private long time = 0;
    @Expose private String reason = "none";
    @Expose private String author = "none";
    @Expose private String date = "none";
    private final Format format = new SimpleDateFormat("hh:mm:ss");



    public void checkBan() {
        if (isBanned) {
            if (time < System.currentTimeMillis()) {
                isBanned = false;
                time = 0;
            }
        }
    }


    public String getTimeLeft() {
        if (!isBanned())
            return "Non banni";
        if (time == -1L)
            return "§cPermanent";
     //   LocalDate temps =  Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();
        long diff = time - System.currentTimeMillis();
        LocalDate temps = new LocalDate(time);
        LocalDateTime dateTime = new LocalDateTime(diff);//LocalDateTime.fromDateFields(new Date(diff));
        LocalDateTime now = new LocalDateTime(System.currentTimeMillis());
        //DateTime dateTime = new DateTime(temps);


        //LocalDate now = new LocalDate(2015, 7, 30);
       // Period tempsRestant = Period.between(LocalDate.now(), temps);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(diff));

        //résumé, bien tenté en tout cas, mais c'est pas très joli
        int years = dateTime.getYear()-now.getYear();
        int mois = dateTime.getMonthOfYear()-1;
        int jours = dateTime.getDayOfMonth()-1;
        int heures =  dateTime.getHourOfDay()-1;
        int minutes = dateTime.getMinuteOfHour();
        int secondes = dateTime.getSecondOfMinute();

        StringBuilder sb = new StringBuilder();

        if(years > 0 &&  mois > 0){
            sb.append(years).append(" années ");

                sb.append(mois).append(" mois ");

            if(jours > 0)
                sb.append(jours).append(" jours ");

            return sb.toString();
        }
        if(jours > 0){
            sb.append(jours).append(" jours ");
            if(heures > 0)
                sb.append(heures).append(" heures ");
            if(minutes > 0)
                sb.append(minutes).append(" minutes ");
            sb.append(secondes).append(" secondes ");
            return sb.toString();
        }
        if(heures > 0)
            sb.append(heures).append(" heures ");
        sb.append(minutes).append(" minutes ");
        sb.append(secondes).append(" secondes ");


       /* while (tempsRestant >= TimeUnit.MOIS.getToSecond()) {
            mois++;
            tempsRestant -= TimeUnit.MOIS.getToSecond();
        }
        while (tempsRestant >= TimeUnit.JOUR.getToSecond()) {
            jours++;
            tempsRestant -= TimeUnit.JOUR.getToSecond();
        }
        while (tempsRestant >= TimeUnit.HEURE.getToSecond()) {
            heures++;
            tempsRestant -= TimeUnit.HEURE.getToSecond();
        }
        while (tempsRestant >= TimeUnit.MINUTE.getToSecond()) {
            minutes++;
            tempsRestant -= TimeUnit.MINUTE.getToSecond();
        }
        while (tempsRestant >= TimeUnit.SECONDE.getToSecond()) {
            secondes++;
            tempsRestant -= TimeUnit.SECONDE.getToSecond();
        }*/
        return sb.toString();



        //
    }

}
