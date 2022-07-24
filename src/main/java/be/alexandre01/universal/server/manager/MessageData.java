package be.alexandre01.universal.server.manager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Getter
@Setter
public class MessageData {

    private int id;
    private Player sender;
    private String message;
}
