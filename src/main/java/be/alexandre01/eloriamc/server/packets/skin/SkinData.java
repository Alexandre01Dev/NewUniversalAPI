package be.alexandre01.eloriamc.server.packets.skin;

import lombok.Getter;

@Getter
public class SkinData {
    private String texture;
    private String signature;

    public SkinData(String texture, String signature) {
        this.texture = texture;
        this.signature = signature;
    }

}
