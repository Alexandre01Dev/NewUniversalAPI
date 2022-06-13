package be.alexandre01.eloriamc.data.punishement;

import be.alexandre01.eloriamc.data.game.Identifier;
import be.alexandre01.eloriamc.utils.TimeUnit;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

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
        LocalDate temps =  Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();
        long diff = time - System.currentTimeMillis();

        Period tempsRestant = Period.between(temps, LocalDate.now());


        //résumé, bien tenté en tout cas, mais c'est pas très joli
        int mois = tempsRestant.getMonths();
        int jours = tempsRestant.getDays();
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
        return mois + " " + TimeUnit.MOIS.getName() + ", " + jours + " " + TimeUnit.JOUR.getName() + ", " + format.format(diff);



        //
    }

}
