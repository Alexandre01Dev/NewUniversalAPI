package be.alexandre01.universal.config;


import be.alexandre01.universal.chat.ChatOptions;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class APIConfig {
    private String defaultPrefix = "[Def_§aPrefix§f_§bAPI§f] ";
    private String defaultWarn = "[Def_§eWarn§f_§fAPI§f] ";
    private String defaultError = "[Def_§cError§f_§fAPI§f] ";

    private String commandPermissionMessage = "§cVous n'avez pas la permission d'exécuter cette commande";
    private ChatOptions commandPermissionChatOptions = ChatOptions.ERROR;
    private String commandArgsBegin = "*---------------------*";
    private String commandArgsSentensePrefix = "§e- §c";
    private String commandArgsSentenseMid = " - ";
    private String commandArgsSentenceSuffix = "";
    private String commandArgsEnd = "*---------------------*";



}
