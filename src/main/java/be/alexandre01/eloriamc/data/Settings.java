package be.alexandre01.eloriamc.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Settings {

    private boolean friendRequest;
    private boolean sendMessage;
    private boolean notifFriend;
    private boolean soundMention;
}
