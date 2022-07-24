package be.alexandre01.universal.config.auto;

import be.alexandre01.universal.config.yaml.YamlUtils;
import be.alexandre01.universal.server.SpigotPlugin;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AutoConfig {
    @Getter private HashMap<String,Location > locationHashMap;

    @Getter private ArrayList<String> locations;
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
                                String pre = "§c";
                                if(locationHashMap.containsKey(loc)) {
                                    pre = "§a";
                                }
                                for(String key : locationHashMap.keySet()) {
                                    System.out.println(key);
                                }
                                player.sendMessage(pre+"- "+ loc);
                            }
                            return true;
                        }

                            if(args[0].equalsIgnoreCase("set")) {
                                if(args.length > 1) {
                                    if(locations.contains(args[1])) {
                                        locationHashMap.put(args[1], player.getLocation());
                                        yamlUtils.getConfig().set("locations."+args[1], player.getLocation());
                                        yamlUtils.save();
                                        player.sendMessage("§aLocation set!");
                                        return false;
                                    }
                                    player.sendMessage("§cLocation not found!");
                                    return false;
                                }
                                player.sendMessage("§cUsage: /"+command+" set <location>");
                            }

                            if(args[0].equalsIgnoreCase("tp")){
                                if(args.length > 1) {
                                    if(locations.contains(args[1])) {
                                        player.teleport(locationHashMap.get(args[1]));
                                        player.sendMessage("§aTeleported to location!");
                                        return false;
                                    }
                                    player.sendMessage("§cLocation not found!");
                                    return false;
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

    public boolean containsLocation(String location) {
        return locationHashMap.containsKey(location);
    }

    public void setLocation(String locationName, Location location) {
        locationHashMap.put(locationName, location);
    }
}
