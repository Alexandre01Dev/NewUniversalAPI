package be.alexandre01.eloriamc.server.commands;


import be.alexandre01.dreamnetwork.plugins.bungeecord.objects.PlayerManagement;
import be.alexandre01.eloriamc.API;
import be.alexandre01.eloriamc.data.PlayerData;
import be.alexandre01.eloriamc.data.PlayerDataManager;
import be.alexandre01.eloriamc.utils.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ban extends Command {

    public Ban(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (label.equalsIgnoreCase("ban")) {
            if (!sender.hasPermission("SMOD")) {
                sender.sendMessage("§cErreur: Vous n'avez pas les droits d'utilisation.");
                return false;
            }
            if (args.length < 3) {
                helpMessage(sender);
                return false;
            }
            String targetName = args[0];
            if(Bukkit.getPlayer(targetName) == null) {
                sender.sendMessage("§cErreur: Ce joueur n'est pas connecté.");
                return false;
            }
            String reason = "";

            for (int i = 2; i < args.length; i++)
                reason = reason + args[i] + " ";
            if (args[1].equalsIgnoreCase("perm")) {

                sender.sendMessage("§c§lSANCTION §f❙ §aVous avez banni §c" + targetName +  " §apendant une durée §binderterminé §apour §e" + reason + "§a.");
                return false;
            }
            if (!args[1].contains(":")) {
                helpMessage(sender);
                return false;
            }
            int duration = 0;
            try {
                duration = Integer.parseInt(args[1].split(":")[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage("§cErreur: La valeur doit être un nombre.");
                return false;
            }
            if (!TimeUnit.existFromShortcut(args[1].split(":")[1])) {
                sender.sendMessage("§cErreur: L'unité de temps n'existe pas.");
                for (TimeUnit units : TimeUnit.values())
                    sender.sendMessage("§f❙ §e" + units.getName() + ":" + units.getShortcut());
                return false;
            }
            TimeUnit unit = TimeUnit.getFromShortcut(args[1].split(":")[1]);
            long banTime = unit.getToSecond() * duration;

            long endToMillis = banTime * 1000L;
            long end = endToMillis + System.currentTimeMillis();

            PlayerData playerData = API.getInstance().getPlayerDataManager().getPlayerData(targetName);
            playerData.getBan().setBanned(true);
            playerData.getBan().setTime(end);
            playerData.getBan().setReason(reason);
            playerData.savePlayerCache();

            Bukkit.getPlayer(targetName).kickPlayer("§8>§8§m-------§8( §f» §c§LBANNISSEMENT §f« §8)§8§m-------§8<\n" +
                    "\n" +
                    "§8» §eDate d'expiration: §b" + playerData.getBan().getTimeLeft() + "\n" +
                    "§8» §eRaison: §b" + playerData.getBan().getReason() + "\n" +
                    "§8» §eAuteur: §b" + playerData.getBan().getAuthor() + "\n" +
                    "\n" +
                    "§8» §ePour toutes réclamations:\n" +
                    "§b§neloriamc.fr/forum\n" +
                    "\n" +
                    "§8>§8§m---------------------------------§8<"
                    );

            sender.sendMessage("§c§lSANCTION §f❙ §aVous avez banni §c" + targetName +  " §apendant §b "+ playerData.getBan().getTimeLeft() + "§a pour §e" + reason + "§a.");
            return false;
        }
        return false;
    }

    private void helpMessage(CommandSender sender) {
        sender.sendMessage("§cSyntaxe: /ban <joueur> <perm/dur> <raison>");
    }
}
