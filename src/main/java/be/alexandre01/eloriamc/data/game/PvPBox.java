package be.alexandre01.eloriamc.data.game;

import com.google.gson.annotations.Expose;

public class PvPBox extends Identifier {

    @Expose
    private int kill = 0;
    @Expose private int death = 0;
    @Expose private int bestKs = 0;
    @Expose private float elo = 0;
    @Expose private String division = "§6§lBronze IV";
    @Expose private String kitSelect = "§7Guerrier";
    @Expose private boolean ranked = false;

}
