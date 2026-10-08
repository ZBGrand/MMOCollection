package me.zbgrand.mmoitemscollection;

import java.util.*;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class CollectionMenu implements Listener {
    private final JavaPlugin plugin; private final TokenStore tokens; private final MmoItemService items; private final Map<Integer,Reward> rewards=new HashMap<>();
    public CollectionMenu(JavaPlugin p,TokenStore t,MmoItemService i){plugin=p;tokens=t;items=i;rebuild();}
    public void rebuild(){
        rewards.clear(); ConfigurationSection s=plugin.getConfig().getConfigurationSection("items"); if(s==null)return;
        for(String key:s.getKeys(false)){String path="items."+key; int slot=plugin.getConfig().getInt(path+".slot"); rewards.put(slot,new Reward(
          color(plugin.getConfig().getString(path+".display-name",key)),plugin.getConfig().getString(path+".type"),plugin.getConfig().getString(path+".id"),plugin.getConfig().getInt(path+".cost")));}
    }
    public void open(Player p){
        int size=Math.max(9,Math.min(54,plugin.getConfig().getInt("settings.menu-size",27)));
        Inventory inv=Bukkit.createInventory(null,size,color(plugin.getConfig().getString("settings.menu-title")));
        ItemStack token=new ItemStack(Material.GOLD_NUGGET); ItemMeta tm=token.getItemMeta(); tm.setDisplayName(color(plugin.getConfig().getString("settings.token-name"))+ChatColor.GRAY+" x"+tokens.get(p.getUniqueId())); token.setItemMeta(tm); inv.setItem(4,token);
        for(var e:rewards.entrySet()){
            Reward r=e.getValue(); ItemStack item=items.create(r.type,r.id);
            if(item==null){item=new ItemStack(Material.BARRIER); ItemMeta m=item.getItemMeta();m.setDisplayName(ChatColor.RED+"Invalid MMOItem: "+r.type+":"+r.id);item.setItemMeta(m);}
            ItemMeta m=item.getItemMeta(); List<String> lore=new ArrayList<>(); lore.add(""); lore.add(ChatColor.GRAY+"Cost: "+ChatColor.GOLD+r.cost); lore.add(ChatColor.YELLOW+"Click to redeem"); m.setLore(lore); item.setItemMeta(m); inv.setItem(e.getKey(),item);
        } p.openInventory(inv);
    }
    @EventHandler public void click(InventoryClickEvent e){
        if(!(e.getWhoClicked() instanceof Player p))return; String title=color(plugin.getConfig().getString("settings.menu-title")); if(!e.getView().getTitle().equals(title))return; e.setCancelled(true);
        Reward r=rewards.get(e.getRawSlot()); if(r==null)return;
        if(!tokens.take(p.getUniqueId(),r.cost)){p.sendMessage(ChatColor.RED+"You do not have enough Collection Tokens.");return;}
        ItemStack item=items.create(r.type,r.id); if(item==null){tokens.add(p.getUniqueId(),r.cost);p.sendMessage(ChatColor.RED+"MMOItem not found: "+r.type+":"+r.id);return;}
        Map<Integer,ItemStack> left=p.getInventory().addItem(item); left.values().forEach(x->p.getWorld().dropItemNaturally(p.getLocation(),x)); p.sendMessage(ChatColor.GREEN+"Redeemed "+r.displayName+ChatColor.GREEN+".");
    }
    private static String color(String s){return ChatColor.translateAlternateColorCodes('&',s==null?"":s);}
    private record Reward(String displayName,String type,String id,int cost){}
}
