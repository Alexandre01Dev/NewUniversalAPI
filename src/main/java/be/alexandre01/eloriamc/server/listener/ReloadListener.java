package be.alexandre01.eloriamc.server.listener;

import be.alexandre01.dreamnetwork.plugins.spigot.DNSpigot;
import be.alexandre01.eloriamc.config.yaml.YamlUtils;
import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.modules.Module;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class ReloadListener implements Listener {
    @EventHandler
    public void onReload(PlayerCommandPreprocessEvent event){
        String cmd = event.getMessage();
        if(cmd.equalsIgnoreCase("/reload") || cmd.equalsIgnoreCase("/rl") || cmd.equalsIgnoreCase("/reloads") ){
            if(!event.getPlayer().hasPermission("eloria.reload")){
                event.getPlayer().sendMessage("§cVous n'avez pas la permission d'accèder à cette commande.");
                event.setCancelled(true);
                return;
            }
            SpigotPlugin.getInstance().isReloading = true;
            event.getPlayer().sendMessage("§e>> Eloria: §cLa commande /reload n'est pas conseillé, elle peut rentrer en conflit avec d'autres plugins et causer des fuites de mémoire! A utiliser avec précaution");
            YamlUtils yamlUtils = new YamlUtils(SpigotPlugin.getInstance(),"cache.yml");
            for (Module module : SpigotPlugin.getInstance().getModuleLoader().getModules()){
                String json = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(module);
                yamlUtils.getConfig().set("modules."+module.getModuleName(),json);
            }
            yamlUtils.save();
        }
    }

    @EventHandler
    public void onReload(ServerCommandEvent event){
        System.out.println("Command -> "+ event.getCommand());
        if(event.getCommand().equalsIgnoreCase("reload") || event.getCommand().equalsIgnoreCase("rl") || event.getCommand().equalsIgnoreCase("reloads")){
            SpigotPlugin.getInstance().isReloading = true;
            YamlUtils yamlUtils = new YamlUtils(SpigotPlugin.getInstance(),"cache.yml");
            for (Module module : SpigotPlugin.getInstance().getModuleLoader().getModules()){
                String json = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(module);
                yamlUtils.getConfig().set("modules."+module.getModuleName(),json);
            }
            yamlUtils.save();
        }
    }

}
