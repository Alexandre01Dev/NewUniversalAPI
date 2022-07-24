package be.alexandre01.universal.chat;


import lombok.Builder;
import lombok.Setter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.HashMap;


public class ChatConfiguration {
    private final HashMap<String,ChatTextBuilder> index = new HashMap<>();
    public ChatTextBuilder getChatTextBuilder(String name){
        return index.get(name);
    }
    public void addTextBuilder(ChatOptions chatOptions, ChatTextBuilder chatTextBuilder){
        addTextBuilder(chatOptions.getName(),chatTextBuilder);
    }
    public void addTextBuilder(String name,ChatTextBuilder chatTextBuilder){
        index.put(name,chatTextBuilder);
    }
    public void createSimpleTextBuilder(ChatOptions chatOptions,ChatTextType chatTextType,BaseComponent... components){
        createSimpleTextBuilder(chatOptions.getName(),chatTextType,components);
    }
    public void createSimpleTextBuilder(ChatOptions chatOptions,ChatTextType chatTextType,String text){
        createSimpleTextBuilder(chatOptions.getName(),chatTextType,TextComponent.fromLegacyText(text));
    }
    public void createSimpleTextBuilder(String name,ChatTextType chatTextType,String text){
        createSimpleTextBuilder(name,chatTextType,TextComponent.fromLegacyText(text));
    }
    public void createSimpleTextBuilder(String name,ChatTextType chatTextType,BaseComponent... components){
        switch (chatTextType){
            case PREFIX:
                index.put(name,ChatTextBuilder.builder().prefixChat(components).build());
                break;
            case SUFIX:
                index.put(name,ChatTextBuilder.builder().suffixChat(components).build());
        }
    }
    public void createSimpleTextBuilder(ChatOptions chatOptions,BaseComponent[] prefix,BaseComponent[] suffix){
        createSimpleTextBuilder(chatOptions.getName(),prefix,suffix);
    }
    public void createSimpleTextBuilder(ChatOptions chatOptions,String prefix,String suffix){
        createSimpleTextBuilder(chatOptions.getName(),TextComponent.fromLegacyText(prefix),TextComponent.fromLegacyText(suffix));
    }
    public void createSimpleTextBuilder(String name,String prefix,String suffix){
      createSimpleTextBuilder(name,TextComponent.fromLegacyText(prefix),TextComponent.fromLegacyText(suffix));
    }
    public void createSimpleTextBuilder(String name,BaseComponent[] prefix,BaseComponent[] suffix){
        index.put(name,ChatTextBuilder.builder().prefixChat(prefix).suffixChat(suffix).build());
    }
    public void createDynamicTextBuilder(ChatOptions chatOptions, ChatTextType chatTextType, ChatTextGeneration chatTextGeneration){
        createDynamicTextBuilder(chatOptions.getName(),chatTextType,chatTextGeneration);
    }
    public void createDynamicTextBuilder(String name, ChatTextType chatTextType, ChatTextGeneration chatTextGeneration){
        switch (chatTextType){
            case PREFIX:
                index.put(name,ChatTextBuilder.builder().dynamicPrefixChat(chatTextGeneration).build());
                break;
            case SUFIX:
                index.put(name,ChatTextBuilder.builder().dynamicSuffixChat(chatTextGeneration).build());
        }
    }
    public void createDynamicTextBuilder(ChatOptions chatOptions, ChatTextType chatTextType, ChatTextGeneration prefix,ChatTextGeneration suffix){
        createDynamicTextBuilder(chatOptions.getName(),chatTextType,prefix,suffix);
    }
    public void createDynamicTextBuilder(String name, ChatTextType chatTextType, ChatTextGeneration prefix,ChatTextGeneration suffix){
        index.put(name,ChatTextBuilder.builder().dynamicPrefixChat(prefix).dynamicSuffixChat(suffix).build());
    }

    @Builder
    public static class ChatTextBuilder{
        private BaseComponent[] prefixChat = null;
        @Setter private ChatTextGeneration dynamicPrefixChat = null;
        private BaseComponent[] suffixChat = null;
        @Setter private ChatTextGeneration dynamicSuffixChat = null;
        public BaseComponent build(Player player, BaseComponent... text){
            System.out.println(text);
            BaseComponent baseComponent = new TextComponent("");
            if(prefixChat != null){
                for (BaseComponent component : prefixChat) {
                    baseComponent.addExtra(component);
                }
            }
            if(dynamicPrefixChat != null){
                for (BaseComponent component : dynamicPrefixChat.dynamicText(player)) {
                    baseComponent.addExtra(component);
                }
            }
            for (BaseComponent component : text) {
                baseComponent.addExtra(component);
            }
            if(suffixChat != null){
                for (BaseComponent component : suffixChat) {
                    baseComponent.addExtra(component);
                }
            }
            if(dynamicSuffixChat != null){
                for (BaseComponent component : dynamicSuffixChat.dynamicText(player)) {
                    baseComponent.addExtra(component);
                }
            }
            return baseComponent;
        }
        public BaseComponent build(Player player,String text){
           return build(player,TextComponent.fromLegacyText(text));
        }

        public void setSuffixChat(String basicText) {
             suffixChat = TextComponent.fromLegacyText(basicText);
        }
        public void setSuffixChat(BaseComponent... basicText) {
            suffixChat = basicText;
        }

        public void setPrefixChat(String basicText) {
            prefixChat = TextComponent.fromLegacyText(basicText);
        }
        public void setPrefixChat(BaseComponent... basicText) {
            prefixChat = basicText;
        }
    }
    public enum ChatTextType{
        PREFIX,SUFIX;
    }
    public interface ChatTextGeneration{
        BaseComponent[] dynamicText(Player player);
    }
}
