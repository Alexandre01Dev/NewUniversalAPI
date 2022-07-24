package be.alexandre01.universal.data.punishement;

import be.alexandre01.universal.data.game.Identifier;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@AllArgsConstructor
@Getter
@Setter
public class Ban extends Identifier {

    @Expose private boolean isBanned = false;
    @Expose private long time = 0;
    @Expose private String reason = "none";
    @Expose private String author = "none";
    @Expose private String date = "none";
    private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");



    public Ban(){
        format.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
    }
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
        return format.format(time);
    }

}
