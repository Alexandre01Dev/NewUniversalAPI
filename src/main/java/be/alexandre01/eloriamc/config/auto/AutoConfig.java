package be.alexandre01.eloriamc.config.auto;

import be.alexandre01.eloriamc.config.yaml.YamlUtils;
import be.alexandre01.eloriamc.server.SpigotPlugin;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AutoConfig {
    private HashMap<String,Location > locationHashMap;

    private ArrayList<String> locations;
    @Getter private YamlUtils yamlUtils;
    private JavaPlugin plugin;

    public AutoConfig(String name, JavaPlugin plugin) {
        this.plugin = plugin;
        locationHashMap = new HashMap<>();
        yamlUtils = new YamlUtils(plugin, name);
    }


    public void setDefaultLocs(String... locationName) {
        locations = new ArrayList<>();
        locations.addAll(Arrays.asList(locationName));

        for (String loc : locations) {
            if(yamlUtils.getConfig().contains("locations."+loc)) {
                locationHashMap.put(loc, (Location) yamlUtils.getConfig().get("locations."+loc));
            }
        }
    }

    public void initAutoConfigCommmands(String command) {
        SpigotPlugin.getInstance().registerCommand(command, new BukkitCommand(command) {
            @Override
            public boolean execute(CommandSender sender, String msg, String[] args) {
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    if(player.hasPermission("eloriamc.admin")) {
                        if(args.length < 1) {
                            for (String loc : locations) {
                                player.sendMessage("§b- "+ loc);
                            }
                            return true;
                        }

                        if(args.length > 1) {
                            if(args[0].equalsIgnoreCase("set")) {
                                if(args.length > 2) {
                                    if(locationHashMap.containsKey(args[1])) {
                                        locationHashMap.put(args[1], player.getLocation());
                                        yamlUtils.getConfig().set("locations."+args[1], player.getLocation());
                                        yamlUtils.save();
                                        player.sendMessage("§aLocation set!");
                                    }
                                }
                            }
                        }
                    }
                }

                return false;
            }
        });
    }

    public Location getLocation(String location) {
        return locationHashMap.get(location);
    }
}
