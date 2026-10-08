package me.zbgrand.mmoitemscollection;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.inventory.ItemStack;

public final class MmoItemService {
    private final MMOItemsCollectionPlugin plugin;
    public MmoItemService(MMOItemsCollectionPlugin plugin) { this.plugin = plugin; }
    public ItemStack create(String typeName, String id) {
        Type type = MMOItems.plugin.getTypes().get(typeName);
        if (type == null) { plugin.getLogger().warning("Unknown MMOItems type: " + typeName); return null; }
        ItemStack item = MMOItems.plugin.getItem(type, id);
        return item == null ? null : item.clone();
    }
}
