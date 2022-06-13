package be.alexandre01.eloriamc.server.packets.ui.scoreboard;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.player.BasePlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
    private final ScheduledFuture glowingTask;
    private final ScheduledFuture reloadingTask;
    private int ipCharIndex;
    private int cooldown;

    private SpigotPlugin plugin;

    public ScoreboardManager() {
        scoreboards = new ArrayList<>();
        ipCharIndex = 0;
        cooldown = 0;

        this.plugin = SpigotPlugin.getInstance();


        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(16);
        ScheduledExecutorService executorMonoThread = Executors.newScheduledThreadPool(1);
        glowingTask = scheduledExecutorService.scheduleAtFixedRate(() ->
        {
            String ip = colorIpAt();
            scoreboards.stream().map(BasePlayer::getPersonalScoreboard).forEach(scoreboard -> {
                executorMonoThread.execute(() -> scoreboard.setLines(ip));
            });
        }, 80, 80, TimeUnit.MILLISECONDS);

        reloadingTask = scheduledExecutorService.scheduleAtFixedRate(() ->
        {
            scoreboards.stream().map(BasePlayer::getPersonalScoreboard).forEach(scoreboard -> {
                executorMonoThread.execute(scoreboard::reloadData);
            });
        }, 1, 1, TimeUnit.SECONDS);
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
        String ip = "play.serveur.fr";

        if (cooldown > 0) {
            cooldown--;
            return ChatColor.YELLOW + ip;
        }

        StringBuilder formattedIp = new StringBuilder();

        if (ipCharIndex > 0) {
            formattedIp.append(ip.substring(0, ipCharIndex - 1));
            formattedIp.append(ChatColor.GOLD).append(ip.substring(ipCharIndex - 1, ipCharIndex));
        } else {
            formattedIp.append(ip.substring(0, ipCharIndex));
        }

        formattedIp.append(ChatColor.RED).append(ip.charAt(ipCharIndex));

        if (ipCharIndex + 1 < ip.length()) {
            formattedIp.append(ChatColor.GOLD).append(ip.charAt(ipCharIndex + 1));

            if (ipCharIndex + 2 < ip.length())
                formattedIp.append(ChatColor.YELLOW).append(ip.substring(ipCharIndex + 2));

            ipCharIndex++;
        } else {
            ipCharIndex = 0;
            cooldown = 50;
        }

        return ChatColor.YELLOW + formattedIp.toString();
    }

}