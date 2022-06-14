package be.alexandre01.eloriamc.server.network;

import be.alexandre01.dreamnetwork.api.objects.RemoteService;
import be.alexandre01.dreamnetwork.api.objects.server.DNServer;
import org.bukkit.entity.Player;

public class EloriaServer {
    private final RemoteService remoteService;
    private int max;

    public EloriaServer(RemoteService remoteService,int max) {
        this.remoteService = remoteService;
        this.max = max;
    }

    public void sendServer(Player player){
        if(remoteService.getServers().isEmpty()){
            player.sendMessage("§cLe serveur sur lequel vous avez été envoyé n'est pas disponible");
            return;
        }

        for (DNServer server : remoteService.getServers().values()) {
            if(server.getPlayers().size() < max){
                player.sendMessage("§aLe serveur §6"+server.getName()+" §aest disponible");

                return;
            }else{
                player.sendMessage("§cLe serveur §6"+server.getName()+" §cest plein");
            }
        }
    }
}
