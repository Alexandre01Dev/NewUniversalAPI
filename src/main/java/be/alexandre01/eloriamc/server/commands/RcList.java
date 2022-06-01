package be.alexandre01.eloriamc.server.commands;

import be.alexandre01.eloriamc.data.mysql.Mysql;
import be.alexandre01.eloriamc.manager.RankManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class RcList extends Command {

    public RcList(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if(player.hasPermission("HELPER")) {

            rcList(player);
            return true;
        }

        player.sendMessage("§C§LERREUR§8│ §cVous n'avez la permissions requis (§bHelper§c)");
        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10f, 10f);
        return false;
    }

    private void rcList(Player player){
        Mysql.query("SELECT * FROM `chat` ORDER BY `id`", (rs) -> {
            try {
                if (rs.last()) {
                    player.sendMessage("§7§m-----------------------------------------");
                    player.sendMessage("§7  ");
                    String nameReport = rs.getString("nameReport");
                    String author = rs.getString("author");
                    RankManager rankManagerAuthor = new RankManager(author);
                    RankManager rankManagervictim = new RankManager(nameReport);
                    player.sendMessage("§7Signalement de §c" + rankManagerAuthor.getRankPrefix() + author);
                    player.sendMessage("  §8➠ " + rankManagervictim.getRankPrefix() + nameReport + " §8» §f" + rs.getString("message"));
                    player.sendMessage("  §8➠ §7Serveur: §b" + rs.getString("server"));
                    player.sendMessage("  §8➠ §7ID: §b" + rs.getString("id"));
                    player.sendMessage("§8  ");
                    TextComponent msgReport = new TextComponent(TextComponent.fromLegacyText("§7Voulez-vous sanctionner §B" + rankManagervictim.getRankPrefix() + nameReport + " §7? §c[SANCTIONNER]"));
                    msgReport.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,  (new ComponentBuilder("§8│ §eClique gauche pour sanctionner le joueur !").create())));
                    msgReport.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/ss "+ nameReport));
                    player.spigot().sendMessage(msgReport);
                    player.sendMessage("§7  ");
                    player.sendMessage("§7§m-----------------------------------------");
                    Mysql.update("DELETE FROM `chat` WHERE `id`= " + rs.getInt("id"));
                    return;
                }

                player.sendMessage("§7§m-----------------------------------------");
                player.sendMessage("§7  ");

                player.sendMessage("§8➠ §7Il y a actuellement §caucun signalement§7 !");

                player.sendMessage("§7  ");
                player.sendMessage("§7§m-----------------------------------------");
            } catch (SQLException var3) {
                var3.printStackTrace();
            }

        });
    }
}
