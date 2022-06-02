package be.alexandre01.eloriamc.data.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Madness implements Identifier{
    private int kill = 0;
    private int death = 0;
    private int bestks = 0;
    private float elo = 0;
    private String division = "§6§lBronze IV";

    private int[] killMsg = {0};
    private int[] killAnim = {0};
}
