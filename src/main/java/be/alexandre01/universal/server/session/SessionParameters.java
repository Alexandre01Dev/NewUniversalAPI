package be.alexandre01.universal.server.session;

import lombok.Getter;
import lombok.Setter;

import javax.xml.stream.Location;

@Getter @Setter
public class SessionParameters {
    private boolean isTemporary;
    private String newWorld;
    private int maxPlayers;
    private Session redirection;
    private Location tpRedirect;
}
