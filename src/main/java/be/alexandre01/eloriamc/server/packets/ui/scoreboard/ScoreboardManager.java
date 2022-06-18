package be.alexandre01.eloriamc.server.packets.ui.scoreboard;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.player.BasePlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class ScoreboardManager {
    private final ArrayList<BasePlayer> scoreboards;
    private ScheduledFuture glowingTask;
    private ScheduledFuture reloadingTask;
    private ScheduledExecutorService executorMonoThread;
    private  ScheduledExecutorService scheduledExecutorService;
    private int charIndex;
    private int cooldown;


    private int ipCooldown;
    private int ipCharIndex;


    private String[] prefixes = new String[]{"play","mc"};
    private int currentPrefix = 0;

    private SpigotPlugin plugin;

    public ScoreboardManager() {
        scoreboards = new ArrayList<>();
        charIndex = 0;
        ipCooldown = 0;
        cooldown = 0;
        ipCooldown = 0;

        boolean removing = false;

        this.plugin = SpigotPlugin.getInstance();
    }


    public void setupSchedulers(int multiThread, int minThread){
        scheduledExecutorService = Executors.newScheduledThreadPool(multiThread);
        executorMonoThread = Executors.newScheduledThreadPool(minThread);
    }
    public void startGlowingTask(int initialDelay,int period, TimeUnit timeUnit){
        glowingTask = scheduledExecutorService.scheduleAtFixedRate(() ->
        {
            String ip = colorIpAt();
            scoreboards.stream().map(BasePlayer::getPersonalScoreboard).filter(Objects::nonNull).forEach(scoreboard -> {
                executorMonoThread.execute(() -> scoreboard.setLines(ip));
            });
        }, initialDelay, period, timeUnit);
    }
    public void startReloadingTask(int initialDelay,int period, TimeUnit timeUnit){
        reloadingTask = scheduledExecutorService.scheduleAtFixedRate(() ->
        {
            scoreboards.stream().map(BasePlayer::getPersonalScoreboard).filter(Objects::nonNull).forEach(scoreboard -> {
                executorMonoThread.execute(scoreboard::reloadData);
            });
        }, initialDelay, period, timeUnit);
    }

    public void onDisable() {
        this.glowingTask.cancel(true);
        this.reloadingTask.cancel(true);
        scoreboards.stream().map(BasePlayer::getPersonalScoreboard).forEach(PersonalScoreboard::onLogout);
    }

    public void onLogin(BasePlayer player) {
        if (scoreboards.contains(player)) {
            return;
        }
        scoreboards.add(player);
    }

    public void onLogout(BasePlayer player) {
        if (scoreboards.contains(player)) {
            player.getPersonalScoreboard().onLogout();
            scoreboards.remove(player);
        }
    }

    public void update(BasePlayer player) {
        if (scoreboards.contains(player)) {
            player.getPersonalScoreboard().reloadData();
        }
    }

    private String colorIpAt() {
        try {
            //System.out.println("FuckingStart");
            String prefix = calculPrefix();
            String ip = prefix+".eloriamc.net";
            if (cooldown > 0) {
                cooldown--;
                return ChatColor.WHITE + ip;
            }

            StringBuilder formattedIp = new StringBuilder();

            if (charIndex > 0) {
                //System.out.println("isCrashHere ?");
                formattedIp.append(ip.substring(0, charIndex - 1));
                formattedIp.append(ChatColor.YELLOW).append(ip.substring(charIndex - 1, charIndex));
            } else {
              //  System.out.println("OrCrashHere ?");
                formattedIp.append(ip.substring(0, charIndex));
            }

         //   System.out.println("OkCrashHere ?");

            while (charIndex >= ip.length()) {
                charIndex--;
            }
            formattedIp.append(ChatColor.GOLD).append(ip.charAt(charIndex));

            if (charIndex + 1 < ip.length()) {
                //System.out.println("HmmCrashHere ?");
                formattedIp.append(ChatColor.YELLOW).append(ip.charAt(charIndex + 1));

                if (charIndex + 2 < ip.length()){
                //    System.out.println("HmmCrashHere2 ?");
                    if(formattedIp.length() > charIndex + 3){
                        formattedIp.append(ChatColor.WHITE).append(ip.substring(charIndex + 2));
                    }
                }


                charIndex++;
            } else {
                charIndex = 0;
                cooldown = 50;
            }

            return ChatColor.WHITE + formattedIp.toString();
        }catch (Exception e){
            System.out.println(e.getCause().getMessage());
        }

        return "null";
    }

    public String calculPrefix(){
        String prepre = "";
       // System.out.println("FuckOCalcoul");

        if(ipCooldown <= 0) {
            ipCooldown = 150;
        }


        ipCooldown--;
       // System.out.println("Tadaa2!");
        if(ipCooldown <= 0) {
            ipCharIndex = 0;
            if(currentPrefix + 1 < prefixes.length) {
                currentPrefix++;
            } else {
                currentPrefix = 0;
            }
            return "";
        }
       // System.out.println("Tadaa2.5!");
        String prefix = prefixes[currentPrefix];
        if(ipCooldown > 150-10 && ipCooldown <= 150) {
                prefix = prefix.substring(0, ipCharIndex);
                if(ipCharIndex < prefixes[currentPrefix].length()) {
                    ipCharIndex++;
                }
        }
     //   System.out.println("Tadaa3!");
        if(ipCooldown <= 5+prefixes[currentPrefix].length() && ipCooldown > 0) {
            prefix = prefix.substring(0, ipCharIndex);
            if(ipCharIndex != 0){
                ipCharIndex--;
            }
        }

      //  System.out.println("Tadaa!");

        return prefix+prepre;
    }

}