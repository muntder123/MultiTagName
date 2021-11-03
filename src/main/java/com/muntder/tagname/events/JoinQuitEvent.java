package com.muntder.tagname.events;

import com.muntder.tagname.LootSystemPlugin;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JoinQuitEvent implements Listener {
    @EventHandler
    public void spawn(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation().add(0, 2, 0);
        ArmorStand armorStand = player.getWorld().spawn(location, ArmorStand.class);
        armorStand.setMarker(true);
        armorStand.setCustomName(LootSystemPlugin.color(PlaceholderAPI.setPlaceholders(player,
                LootSystemPlugin.getInstance().getConfig().getString("tag"))));
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        LootSystemPlugin.getInstance().playerArmorStand.put(player.getUniqueId(), armorStand);
    }

    @EventHandler
    public void leave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (LootSystemPlugin.getInstance().playerArmorStand.containsKey(player.getUniqueId())) {
            ArmorStand armorStand = LootSystemPlugin.getInstance().playerArmorStand.get(player.getUniqueId());
            armorStand.remove();
            LootSystemPlugin.getInstance().playerArmorStand.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void death(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = event.getEntity().getKiller();
        if (killer != null) {
            if(LootSystemPlugin.getInstance().playerArmorStand.containsKey(player.getUniqueId())){
                LootSystemPlugin.getInstance().playerArmorStand.get(player.getUniqueId()).remove();
            }
        }
    }
    @EventHandler
    public void respawn(PlayerRespawnEvent e){
        Player player = e.getPlayer();
            if(LootSystemPlugin.getInstance().playerArmorStand.containsKey(player.getUniqueId())){
            Location location = player.getLocation().add(0, 2, 0);
            ArmorStand armorStand = player.getWorld().spawn(location, ArmorStand.class);
            armorStand.setMarker(true);
            armorStand.setCustomName(LootSystemPlugin.color(PlaceholderAPI.setPlaceholders(player,
                    LootSystemPlugin.getInstance().getConfig().getString("tag"))));
            armorStand.setCustomNameVisible(true);
            armorStand.setGravity(false);
            armorStand.setVisible(false);
            LootSystemPlugin.getInstance().playerArmorStand.replace(player.getUniqueId(), armorStand);
        }
    }
}
