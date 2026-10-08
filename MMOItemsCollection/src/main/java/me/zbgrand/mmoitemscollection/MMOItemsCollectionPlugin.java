package me.zbgrand.mmoitemscollection;

import org.bukkit.plugin.java.JavaPlugin;

public final class MMOItemsCollectionPlugin extends JavaPlugin {
    private TokenStore tokenStore;
    private CollectionMenu menu;

    @Override public void onEnable() {
        saveDefaultConfig();
        tokenStore = new TokenStore(this);
        tokenStore.load();
        MmoItemService itemService = new MmoItemService(this);
        menu = new CollectionMenu(this, tokenStore, itemService);
        getCommand("collection").setExecutor(new CollectionCommand(menu));
        getCommand("collectionadmin").setExecutor(new AdminCommand(this, tokenStore));
        getServer().getPluginManager().registerEvents(menu, this);
        getLogger().info("MMOItemsCollection enabled.");
    }
    @Override public void onDisable() { if (tokenStore != null) tokenStore.save(); }
    public void reloadPlugin() { reloadConfig(); menu.rebuild(); }
}
