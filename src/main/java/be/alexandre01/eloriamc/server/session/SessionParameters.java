package be.alexandre01.eloriamc.server.session;

import lombok.Data;

@Data
public class SessionParameters {
    private boolean isTemporary;
    private String newWorld;
}
