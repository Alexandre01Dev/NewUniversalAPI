package be.alexandre01.eloriamc.server.utils.exp;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class Experience {


    private String prefix = "§6§l[";
    private String suffix = "§6§l]";
    private String separator = "§6§l|";

    @Getter private int level = 0;
    @Getter private float totalExp = 0;

    private int beginExp = 100;
    private float multiplier = 1.0f;

    private int division = 500;
    private float expToNextLevel;
    private GiveExp giveExp;

    private RefreshExp refreshExp;






    public void giveExp(float exp) {
        exp *= this.multiplier;
        giveExp.giveExp(exp);
    }
    public float getExpFromLevel(int level) {
        if(level == 0) return 0;
        System.out.println(division);
        int d = division/beginExp;
        //100 y+((y^(2))/(5)) = x         5 = division/begin     y = level
        return (float) (beginExp*level+(((Math.pow(level, 2) / d))));
    }

    public int getLevelFromExp(float exp){
        int d = division/beginExp;

        final double a = Math.pow(d,2)*beginExp+Math.pow(beginExp,2);
        final double b = Math.pow(d,2)*Math.sqrt(beginExp);

        return (int) (Math.sqrt(d*(exp+a))-b);
    }




    public void refresh(float totalExp){
        this.totalExp = totalExp;
        this.level = getLevelFromExp(totalExp);
     //   System.out.println("Level: " + getExpFromLevel(level+1));
        // System.out.println("Total Exp: " + totalExp);
        this.expToNextLevel = totalExp - getExpFromLevel(level+1);

        //this.expToNextLevel = totalExp- getExpFromLevel(level);
       // System.out.println("Exp to next level: " + expToNextLevel);
    }

    public void refresh(){
        refresh(refreshExp.refresh());
    }

    public int getExp(){
        return (int) (this.totalExp - (this.level * 100));
    }


    public String animateExp() {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        float xp =  totalExp- getExpFromLevel(level);
        float dif =  (getExpFromLevel(level+1)-getExpFromLevel(level));
        int percent = (int) ((xp*100)/dif);
        System.out.println("Percent: " + percent);
        sb.append("§a");
        for (int i = 0; i < (int) (percent/7); i++) {
            sb.append("|");
        }
        sb.append("§7");
        for (int i = (percent/7); i < (int) (100/7); i++) {
            sb.append("|");
        }
        System.out.println(percent);
        sb.append(suffix);
        //sb.append(" ").append(percent).append("%");

        System.out.println(sb.toString());
        return sb.toString();
    }

    //Test
    public static void main(String[] args) {
        int n = 5;
        Experience exp = new Experience();
        System.out.println(exp.getExpFromLevel(5));
        System.out.println("Level ? "+ exp.getLevelFromExp(94));
        exp.setTotalExp(232);

        exp.refresh(exp.totalExp);
        System.out.println("Level "+exp.getLevel());
        float f = exp.getExpFromLevel(n);
        System.out.println(exp.getExpFromLevel(n));
        System.out.println(exp.getExpFromLevel(n)/5);
        System.out.println(exp.getLevelFromExp(f));

        System.out.println(exp.animateExp());

    }
    public interface GiveExp {
        void giveExp(float exp);
    }
    public interface RefreshExp {
        float refresh();
    }
}
