package be.alexandre01.eloriamc.server.commands;

import be.alexandre01.dreamnetwork.api.NetworkBaseAPI;
import be.alexandre01.dreamnetwork.api.objects.RemoteService;
import be.alexandre01.dreamnetwork.plugins.spigot.api.DNSpigotAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Hub extends Command {
    DNSpigotAPI networkBaseAPI;
    protected Hub(String name) {
        super(name);
        networkBaseAPI = DNSpigotAPI.getInstance();
    }

    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {
        if(sender instanceof Player){
            RemoteService remoteService = networkBaseAPI.getByName("Hub");
            remoteService.getServers().get(0);
        }
        return false;
    }
}
