package me.zbgrand.mmoitemscollection;

import java.io.File;
import java.sql.*;
import java.util.UUID;
import org.bukkit.plugin.java.JavaPlugin;

public final class TokenStore {
    private final JavaPlugin plugin; private Connection connection;
    public TokenStore(JavaPlugin plugin) { this.plugin = plugin; }
    public void load() {
        try {
            plugin.getDataFolder().mkdirs();
            connection = DriverManager.getConnection("jdbc:sqlite:" + new File(plugin.getDataFolder(), "data.db"));
            try (Statement st = connection.createStatement()) {
                st.executeUpdate("CREATE TABLE IF NOT EXISTS players (uuid TEXT PRIMARY KEY, tokens INTEGER NOT NULL DEFAULT 0)");
            }
        } catch (SQLException e) { throw new IllegalStateException("Could not open SQLite database", e); }
    }
    public int get(UUID uuid) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT tokens FROM players WHERE uuid=?")) {
            ps.setString(1, uuid.toString()); ResultSet rs = ps.executeQuery(); return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
    public void add(UUID uuid, int amount) {
        int next = Math.max(0, get(uuid) + amount);
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO players(uuid,tokens) VALUES(?,?) ON CONFLICT(uuid) DO UPDATE SET tokens=excluded.tokens")) {
            ps.setString(1, uuid.toString()); ps.setInt(2, next); ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
    public boolean take(UUID uuid, int amount) { if (amount < 0 || get(uuid) < amount) return false; add(uuid, -amount); return true; }
    public void save() { try { if (connection != null) connection.close(); } catch (SQLException ignored) {} }
}
