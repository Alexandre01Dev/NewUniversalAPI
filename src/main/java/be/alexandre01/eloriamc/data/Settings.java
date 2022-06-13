package be.alexandre01.eloriamc.data;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Settings {
    @Expose private boolean friendRequest;
    @Expose private boolean sendMessage;
    @Expose private boolean notifFriend;
    @Expose private boolean soundMention;
}
