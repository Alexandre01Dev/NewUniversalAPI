package be.alexandre01.eloriamc.data.profile;

import be.alexandre01.eloriamc.data.game.Identifier;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Profile extends Identifier {
    @Expose private String skinUUID = null;
    @Expose private String nickedName = null;
    @Expose private String fakePrefix = null;
}
