package be.alexandre01.universal.data;

import be.alexandre01.universal.data.game.Identifier;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Settings extends Identifier {
    @Expose private boolean friendRequest = true;
    @Expose private boolean sendMessage = true;
    @Expose private boolean notifFriend = true;
    @Expose private boolean soundMention = true;
    @Expose private boolean speedLobby = true;
    @Expose private boolean joinMessage = true;
    @Expose private String deplacementLobby = "false";
    @Expose private String chatSymbole = "§8»";
}
