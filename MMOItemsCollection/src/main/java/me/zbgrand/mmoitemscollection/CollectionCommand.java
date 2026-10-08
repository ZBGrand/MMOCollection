package me.zbgrand.mmoitemscollection;
import org.bukkit.command.*; import org.bukkit.entity.Player;
public final class CollectionCommand implements CommandExecutor {
    private final CollectionMenu menu; public CollectionCommand(CollectionMenu menu){this.menu=menu;}
    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (!(sender instanceof Player p)){sender.sendMessage("Only players can use this command.");return true;} menu.open(p); return true;
    }
}
