package be.alexandre01.eloriamc.data.game;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Madness extends Identifier{
    @Expose private int kill = 0;
    @Expose private int death = 0;
    @Expose private int bestks = 0;
    @Expose private float elo = 0;
    @Expose private String division = "§6§lBronze IV";

    @Expose private int[] killMsg = {0};
    @Expose private int[] killAnim = {0};
}
