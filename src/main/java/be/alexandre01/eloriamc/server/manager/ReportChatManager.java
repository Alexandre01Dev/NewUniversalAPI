package be.alexandre01.eloriamc.server.manager;

import be.alexandre01.eloriamc.data.mysql.Mysql;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Getter
@Setter
public class ReportChatManager {

    private Player nameReport;
    private Player author;
    private String message;
    private String server;


    public void reportChatSuccess() {
        Mysql.update("INSERT INTO chat (nameReport, author, message, server) VALUES ('" + this.nameReport.getName() + "', '" + this.author.getName() + "', '" + this.message + "', '" + this.server + "')");
        author.sendMessage("§6§LREPORT§8│ §aLe report a été envoyé à l'équipe de modération, il sera traité prochainement !");
    }

}
