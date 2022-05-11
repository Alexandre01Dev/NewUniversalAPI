package be.alexandre01.eloriamc.chat;

public enum ChatOptions {
    PREFIX("prefix"),WARNING("warn"),ERROR("error");

    private String name;
    ChatOptions(String s){
        this.name = s;
    }

    public String getName() {
        return name;
    }

    public static ChatConfiguration.ChatTextBuilder getCustom(String i){

        return null;
    }
}
