package com.muntder.tagname;

import com.muntder.tagname.events.JoinQuitEvent;
import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class LootSystemPlugin extends JavaPlugin implements Listener {
    public final HashMap<UUID, ArmorStand> playerArmorStand = new HashMap<>();
    @Getter
    private static LootSystemPlugin instance;
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        saveConfig();

        getServer().getPluginManager().registerEvents(new JoinQuitEvent(),this);
        new BukkitRunnable() {
            @Override
            public void run() {


                playerArmorStand.entrySet().stream().filter(entry ->
                        Bukkit.getPlayer(entry.getKey()) != null).forEach(entry -> {
                            Player player = Bukkit.getPlayer(entry.getKey());
                            if (entry.getValue() == null) {
                                return;
                            }
                         entry.getValue().setCustomName(color(PlaceholderAPI.setPlaceholders(player,
                                    getConfig().getString("tag"))));
                         entry.getValue().teleport(player.getLocation().add(0, 2, 0));
                        });
            }
        }.runTaskTimer(this,0,1);

    }

    @Override
    public void onDisable() {
        playerArmorStand.forEach((uuid, armorStand) -> {
            armorStand.remove();
        });
        playerArmorStand.clear();
    }
    public static String color(String msg) {
        if (msg == null) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static List<String> color(List<String> msg) {
        if (msg == null || msg.isEmpty()) {
            return Collections.emptyList();
        }
        return msg.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
    }
}
