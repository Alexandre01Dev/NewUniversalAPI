package be.alexandre01.universal.server.player;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public interface TitleImpl {
    void sendTitle(Integer fadeIn, Integer stay, Integer fadeOut, String message);
    void sendSubtitle(Integer fadeIn, Integer stay, Integer fadeOut, String message);
    void sendFullTitle(Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle);
    void sendTitle( Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle);
    void sendTabTitle(String header, String footer);
    void sendActionBar(String message);

    @Deprecated
    default void sendSubtitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message) {
        sendTitle(player, fadeIn, stay, fadeOut, null, message);
    }

    @Deprecated
    default void sendFullTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title,
                                     String subtitle) {
        sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
    }
    default void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message) {
        sendTitle(player, fadeIn, stay, fadeOut, message, null);
    }
    default void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title,
                                 String subtitle) {

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, (IChatBaseComponent) null,
                fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
        connection.sendPacket(packetPlayOutTimes);
        IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
        PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(
                PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
        connection.sendPacket(packetPlayOutSubTitle);

        IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
        PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
                titleMain);
        connection.sendPacket(packetPlayOutTitle);

    }

    default void sendTabTitle(Player player, String header, String footer) {
        if (header == null) {
            header = "";
        }
        header = ChatColor.translateAlternateColorCodes('&', header);
        if (footer == null) {
            footer = "";
        }
        footer = ChatColor.translateAlternateColorCodes('&', footer);

        header = header.replaceAll("%player%", player.getDisplayName());
        footer = footer.replaceAll("%player%", player.getDisplayName());

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
        IChatBaseComponent tabFoot = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);
        try {
            Field field = headerPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headerPacket, tabFoot);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.sendPacket(headerPacket);
        }
    }

    default void sendActionBar(Player player, String message){
        CraftPlayer p = (CraftPlayer) player;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc,(byte) 2);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
    }
}
