package be.alexandre01.eloriamc.server.player;

import be.alexandre01.eloriamc.API;
import be.alexandre01.eloriamc.chat.ChatConfiguration;
import be.alexandre01.eloriamc.chat.ChatOptions;
import be.alexandre01.eloriamc.server.SpigotPlugin;
import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class BasePlayer extends CraftPlayer implements TitleImpl, NameTagImpl {
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    SpigotPlugin spigotPlugin;
    API api;
    Player player;

    public BasePlayer(Player player) {
        super(((CraftServer) player.getServer()), ((CraftPlayer) player).getHandle());
        this.player = player;
        this.spigotPlugin = SpigotPlugin.getInstance();
        this.api = API.getInstance();
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
}
