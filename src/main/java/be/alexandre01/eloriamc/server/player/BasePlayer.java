package be.alexandre01.eloriamc.server.player;

import be.alexandre01.eloriamc.API;
import be.alexandre01.eloriamc.chat.ChatConfiguration;
import be.alexandre01.eloriamc.chat.ChatOptions;
import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.packets.injector.PacketInjector;
import be.alexandre01.eloriamc.server.session.players.PlayerDamager;
import com.mojang.authlib.GameProfile;
import lombok.Getter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.Achievement;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class BasePlayer extends CraftPlayer implements TitleImpl, NameTagImpl {
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    @Getter
    PacketInjector packetInjector;
    @Getter PlayerDamager damagerUtils = new PlayerDamager();
    SpigotPlugin spigotPlugin;
    API api;
    Player player;

    public BasePlayer(Player player) {
        super(((CraftServer) player.getServer()), ((CraftPlayer) player).getHandle());
        this.player = player;
        this.spigotPlugin = SpigotPlugin.getInstance();
        this.api = API.getInstance();
        this.packetInjector = new PacketInjector(player);
    }


    @Override
    public Player getPlayer() {
       return this.player;
    }
    /**
     * Send packet to Player
     * @param packet
     */
    public void sendPacket(Packet<?> packet){
        getHandle().playerConnection.sendPacket(packet);
    }

    /**
     * Send Message To Player
     * @param chatOptions
     * @param message
     * */
    public void sendMessage(ChatOptions chatOptions, String message){
        sendMessage(api.getChatConfiguration().getChatTextBuilder(chatOptions.getName()),message);
    }
    public void sendMessage(ChatOptions chatOptions, BaseComponent... components){
        sendMessage(api.getChatConfiguration().getChatTextBuilder(chatOptions.getName()),components);
    }
    public void sendMessage(String name, String message){
        sendMessage(api.getChatConfiguration().getChatTextBuilder(name),message);
    }
    public void sendMessage(String name, BaseComponent... components){
        sendMessage(api.getChatConfiguration().getChatTextBuilder(name),components);
    }
    public void sendMessage(ChatConfiguration.ChatTextBuilder chatTextBuilder, String message){
        spigot().sendMessage(chatTextBuilder.build(this,message));
    }
    public void sendMessage(ChatConfiguration.ChatTextBuilder chatTextBuilder, BaseComponent... components){
        spigot().sendMessage(chatTextBuilder.build(this,components));
    }
    public void sendMessage(BaseComponent... baseComponent){
        this.spigot().sendMessage(baseComponent);
    }
    public void sendMessage(Collection<String> message){
        for(String s : message){
            sendMessage(s);
        }
    }
    public void sendMessage(ArrayList<String> message){
        for(String s : message){
            sendMessage(s);
        }
    }
    public void sendMessage(LinkedList<String> message){
        for(String s : message){
            sendMessage(s);
        }
    }
    public void sendMessage(List<String> message){
        for(String s : message){
            sendMessage(s);
        }
    }
    public void sendMessage(String[] message){
        for(String s : message){
            sendMessage(s);
        }
    }

    /**
     * Send Title and ActionBar to player
     * @param fadeIn
     * @param stay
     * @param fadeOut
     * @param message
     */
    @Override
    public void sendTitle(Integer fadeIn, Integer stay, Integer fadeOut, String message) {
        TitleImpl.super.sendTitle(this,fadeIn,stay,fadeOut,message);
    }

    @Override
    public void sendSubtitle(Integer fadeIn, Integer stay, Integer fadeOut, String message) {
        TitleImpl.super.sendSubtitle(this,fadeIn,stay,fadeOut,message);
    }

    @Override
    public void sendFullTitle(Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        TitleImpl.super.sendFullTitle(this,fadeIn,stay,fadeOut,title,subtitle);
    }

    @Override
    public void sendTitle(Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        TitleImpl.super.sendTitle(this,fadeIn,stay,fadeOut,title,subtitle);
    }

    @Override
    public void sendTabTitle(String header, String footer) {
        TitleImpl.super.sendTabTitle(this,header,footer);
    }

    @Override
    public void sendActionBar(String message) {
        TitleImpl.super.sendActionBar(this,message);
    }



    //UNUSED METHODS

    @Override
    public void setNameTag(Player player, String teamName, String prefix, String suffix) {
        NameTagImpl.super.setNameTag(player,teamName,prefix,suffix);
    }

    @Override
    public void removeNameTag(Player player, String teamName) {
        NameTagImpl.super.removeNameTag(player, teamName);
    }

    public BasePlayer(CraftServer server, EntityPlayer entity) {
        super(server, entity);
    }
}
