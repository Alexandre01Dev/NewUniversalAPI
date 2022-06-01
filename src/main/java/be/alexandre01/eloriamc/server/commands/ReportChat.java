package be.alexandre01.eloriamc.server.commands;

import be.alexandre01.dreamnetwork.plugins.spigot.api.DNSpigotAPI;
import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.manager.MessageData;
import be.alexandre01.eloriamc.server.manager.ReportChatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ReportChat extends Command {

    private ArrayList<Integer> ids = new ArrayList<>();

    private SpigotPlugin instance = SpigotPlugin.getInstance();

    public ReportChat(String name) {
        super(name);
    }



    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(!(sender instanceof Player)) return false;
            Player player = (Player) sender;
            if (args.length == 1) {
                int id;
                try {
                    id = Integer.parseInt(args[0]);
                }catch (Exception e){
                    player.sendMessage("ID doesn't match");
                    return false;
                }
                if(ids.contains(id)){
                    player.sendMessage("§c§lERREUR§8│ §cLe message a déjà été signaler !");
                    return false;
                }


                MessageData messageData = instance.getMessageData().get(id);
                ReportChatManager reportChat = new ReportChatManager(messageData.getSender(), player, messageData.getMessage(), DNSpigotAPI.getInstance().getProcessName());
                reportChat.reportChatSuccess();
                ids.add(messageData.getId());
                return true;
            }

            player.sendMessage("§c§lERREUR§8│ §cSyntaxe: /reportchat <id>");
            return false;
        }

}
