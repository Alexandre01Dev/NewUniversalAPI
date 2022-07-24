package be.alexandre01.universal.data.commands;

import be.alexandre01.universal.API;
import be.alexandre01.universal.data.PlayerData;
import be.alexandre01.universal.utils.Tuple;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PlayerDataModifier extends Command {
    public PlayerDataModifier(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {


        if(sender instanceof Player){
            Player player = (Player) sender;

            if(!player.hasPermission("eloriaapi.pdata.admin")) return false;

            if(args.length == 0){
                sender.sendMessage("§c/pd get <player> <Object>");
                sender.sendMessage("§c/pd set <player> <Object> <value>");
                return true;
            }

            if(args.length > 0){
                if(args[0].equalsIgnoreCase("get")){
                    if(args.length < 2){
                        sender.sendMessage("§c/pd get <player> <Object>");
                        return true;
                    }
                    if(Bukkit.getPlayer(args[1]) == null){
                        sender.sendMessage("§cPlayer not found");
                        return true;
                    }
                    PlayerData playerData = API.getInstance().getPlayerDataManager().getLocalPlayerData(Bukkit.getPlayer(args[1]));

                    if(playerData == null){
                        sender.sendMessage("§cPlayerData not found");
                        return true;
                    }

                    if(args.length < 3){
                        for(Field field : playerData.getClass().getDeclaredFields()){
                            field.setAccessible(true);
                            BaseComponent[] b = getChatComponentsFromField(field, playerData, args[1],"");
                            player.spigot().sendMessage(b);

                        }
                        return true;
                    }


                        String[] split = args[2].split(":");




                        Tuple<Object,Field> o = getObjectFromRefractorField(playerData, split,null,0);
                        System.out.println(o);
                    System.out.println("Wtf");
                        System.out.println(o.a());
                        List<BaseComponent[]> b = refractorFields(o.a(),args[0],args[1]);
                        System.out.println(b);
                        for(BaseComponent[] bc : b){
                            player.spigot().sendMessage(bc);
                        }





                }
                if(args[0].equalsIgnoreCase("set")){
                    if(args.length < 2){
                        sender.sendMessage("§c/pd get <player> <Object>");
                        return true;
                    }
                    if(Bukkit.getPlayer(args[1]) == null){
                        sender.sendMessage("§cPlayer not found");
                        return true;
                    }
                    PlayerData playerData = API.getInstance().getPlayerDataManager().getLocalPlayerData(Bukkit.getPlayer(args[1]));

                    if(playerData == null){
                        sender.sendMessage("§cPlayerData not found");
                        return true;
                    }

                    if(args.length < 3){
                        sender.sendMessage("§c/pd set <player> <Object> <value>");
                        return true;
                    }

                        String[] split = args[2].split(":");


                        Tuple<Object,Field> o = getObjectFromRefractorField(playerData, split,null,0);
                        if(o.a() == null || o.b() == null){
                            sender.sendMessage("§cObject not found");
                            return true;
                        }

                        if(o.b().getType().isAssignableFrom(String.class) ){
                            try {
                                o.b().set(o.a(),args[3]);
                            } catch (IllegalAccessException e) {
                                System.out.println("IllegalAccessException");
                            }
                            return true;
                        }

                        if(o.b().getType().isAssignableFrom(int.class)){
                            try {
                                Integer i = Integer.parseInt(args[3]);

                                o.b().set(o.a(),i);
                            } catch (Exception e) {
                                e.printStackTrace();
                                player.sendMessage("IllegalArgumentException or Number incorrect");
                            }
                            return true;
                        }
                    if(o.b().getType().isAssignableFrom(Integer.class)){
                        try {
                            Integer i = Integer.parseInt(args[3]);

                            o.b().set(o.a(),i);
                        } catch (Exception e) {
                            e.printStackTrace();
                            player.sendMessage("IllegalArgumentException or Number incorrect");
                        }
                        return true;
                    }

                        if(o.b().getType().isAssignableFrom(double.class) || o.b().getType().isAssignableFrom(Double.class)){
                            try {
                                o.b().set(o.a(),Double.parseDouble(args[3]));
                            } catch (Exception e) {
                                player.sendMessage("IllegalArgumentException or Number incorrect");
                            }
                            return true;
                        }

                        if(o.b().getType().isAssignableFrom(float.class) || o.b().getType().isAssignableFrom(Float.class)){
                            try {
                                o.b().set(o.a(),Float.parseFloat(args[3]));
                            } catch (Exception e) {
                                e.printStackTrace();
                                player.sendMessage("IllegalArgumentException or Number incorrect");
                            }
                            return true;
                        }

                        if(o.b().getType().isAssignableFrom(boolean.class) || o.b().getType().isAssignableFrom(Boolean.class)){
                            try {
                                o.b().set(o.a(),Boolean.parseBoolean(args[3]));
                            } catch (Exception e) {
                                player.sendMessage("IllegalArgumentException or Boolean incorrect");
                            }
                            return true;
                        }
                        player.sendMessage("§cObject as not a assignable type, Please modify the value in the DB");
                        player.sendMessage("§cObject is a " + o.b().getType().getSimpleName());

                }
                if(args[0].equalsIgnoreCase("save")){
                    return true;
                }
            }
        }
        return false;
    }

    public Tuple<Object,Field> getObjectFromRefractorField(Object base, String[] splitter,Field indexedField,int index){
        Object o;
        System.out.println("Start from index: " + index);
        if(index == splitter.length){
            System.out.println("End");
            return new Tuple<>(base,indexedField);
        }
        try {
            Field field = base.getClass().getDeclaredField(splitter[index]);

            field.setAccessible(true);
            o = field.get(base);
            System.out.println(o);
            return getObjectFromRefractorField(o,splitter,field,index+1);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

    }
    public List<BaseComponent[]> refractorFields(Object base, String arg1, String arg2){
        System.out.println("NewRefractor for "+ base);
        List<Tuple<Field,Object>> newEntry = new ArrayList<>();
        List<BaseComponent[]> baseComponents = new ArrayList<>();

        for(Field field : base.getClass().getDeclaredFields()){
            System.out.println(field.getName());
            field.setAccessible(true);
            try {
                newEntry.add(new Tuple<>(field, field.get(base)));;
                System.out.println(base);
                System.out.println("Field: " + field.getName());
                baseComponents.add(getChatComponentsFromField(field, field.get(base), arg1,arg2));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return refractorFields(newEntry,baseComponents,arg1,arg2,base);
    }
    private List<BaseComponent[]> refractorFields(List<Tuple<Field,Object>> newEntry, List<BaseComponent[]> baseComponents, String arg1, String arg2,Object base){
        System.out.println("Shit alors");
        if(newEntry.size() == 0){
            return baseComponents;
        }
        List<Tuple<Field,Object>> newFields = new ArrayList<>();
        for(Tuple<Field,Object> field : newEntry){
            for (Field f : field.a().getType().getDeclaredFields()) {
                f.setAccessible(true);
                try {
                    if(field.b() != null){
                        newFields.add(new Tuple<>(f, f.get(field.b())));
                        baseComponents.add(getChatComponentsFromField(field.a(),base, arg1, arg2));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Ignore errors");
                    continue;
                }
            }
        }
        return refractorFields(newFields, baseComponents,  arg1, arg2,base);
    }
    


    public BaseComponent[] getChatComponentsFromField(Field f,Object object,String arg1,String arg2){
        TextComponent get = new TextComponent(" §7[§aGET§7]");
        if(arg2.length() != 0) {
            arg2 += ":";
        }
        get.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/pd get " + arg1 + " " + arg2+f.getName()));
        get.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent("§7Click to get the value")}));

        TextComponent set = new TextComponent(" §7[§cSET§7]");
        String getterObj = "null";
        try {
            if(f.get(object) == null) {
                getterObj = "null";
            }
            else {
                getterObj = f.get(object).toString();
            }
        }catch (Exception e){
            
        }
        
     
        set.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/pd set " + arg1 + " " + arg2+ f.getName()+" "));
        set.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent("§7Click to set the value")}));
        try {
            return new BaseComponent[]{
                    new TextComponent("§a" + f.getName() + ": "),
                    new TextComponent("§a" + getterObj),
                    get,
                    set,
            };
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
