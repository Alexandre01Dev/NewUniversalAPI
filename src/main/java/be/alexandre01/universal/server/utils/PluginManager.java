package be.alexandre01.universal.server.utils;

import be.alexandre01.universal.server.SpigotPlugin;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;

public class PluginManager {

    public void sendPlayerTo(Player player, String server) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(SpigotPlugin.getInstance(), "BungeeCord", out.toByteArray());
    }
}
