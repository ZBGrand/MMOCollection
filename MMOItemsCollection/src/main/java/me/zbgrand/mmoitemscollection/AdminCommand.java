package me.zbgrand.mmoitemscollection;
import org.bukkit.Bukkit; import org.bukkit.ChatColor; import org.bukkit.command.*; import org.bukkit.entity.Player; import org.bukkit.plugin.java.JavaPlugin;
public final class AdminCommand implements CommandExecutor {
 private final JavaPlugin plugin; private final TokenStore tokens; public AdminCommand(JavaPlugin p,TokenStore t){plugin=p;tokens=t;}
 @Override public boolean onCommand(CommandSender s,Command c,String l,String[] a){
  if(a.length==1&&a[0].equalsIgnoreCase("reload")){plugin.reloadConfig();s.sendMessage(ChatColor.GREEN+"Configuration reloaded.");return true;}
  if(a.length>=3&&(a[0].equalsIgnoreCase("give")||a[0].equalsIgnoreCase("take"))){
   Player p=Bukkit.getPlayerExact(a[1]); if(p==null){s.sendMessage(ChatColor.RED+"Player not found.");return true;}
   int n; try{n=Integer.parseInt(a[2]);}catch(NumberFormatException e){s.sendMessage(ChatColor.RED+"Amount must be an integer.");return true;}
   if(n<0){s.sendMessage(ChatColor.RED+"Amount must be positive.");return true;}
   if(a[0].equalsIgnoreCase("give")){tokens.add(p.getUniqueId(),n);s.sendMessage(ChatColor.GREEN+"Given "+n+" tokens to "+p.getName()+".");}
   else{tokens.take(p.getUniqueId(),n);s.sendMessage(ChatColor.GREEN+"Removed up to "+n+" tokens from "+p.getName()+".");} return true;
  }
  s.sendMessage(ChatColor.YELLOW+"/collectionadmin give <player> <amount>");s.sendMessage(ChatColor.YELLOW+"/collectionadmin take <player> <amount>");s.sendMessage(ChatColor.YELLOW+"/collectionadmin reload");return true;
 }
}
