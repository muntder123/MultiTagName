package com.muntder.tagname.core.implementation;

import com.muntder.tagname.api.EntityManager;
import com.muntder.tagname.core.utils.Utils;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class EntityManagerProvider implements EntityManager {

    private final HashMap<UUID, ArmorStand> entities = new HashMap<>();
    private final String tag;

    public EntityManagerProvider(JavaPlugin plugin) {
        tag = plugin.getConfig().getString("tag");

        new EntityUpdateTask().runTaskTimerAsynchronously(plugin, 0, 1);
    }

    @Override
    public void update(UUID uuid) {
        Optional<ArmorStand> armorStand = Optional.ofNullable(entities.get(uuid));
        if (armorStand.isPresent()) {
            Utils.getPlayer(uuid).ifPresent(player -> {
                ArmorStand stand = armorStand.get();
                stand.setCustomName(Utils.colorPlaceholders(player, tag));
                stand.teleport(player.getLocation().add(0, 2, 0 ));
            });
        } else {
            // The armorstand doesn't exist
            Utils.getPlayer(uuid).ifPresent(player -> {
                ArmorStand stand = spawn(player);
                entities.replace(uuid, stand);
            });
        }
    }

    @Override
    public void add(UUID uuid) {
        if (getEntity(uuid).isPresent())
            return;

        Utils.getPlayer(uuid).ifPresent(player -> {
            ArmorStand armorStand = spawn(player);
            entities.put(player.getUniqueId(), armorStand);
        });
    }

    @Override
    public void remove(UUID uuid) {
        getEntity(uuid).ifPresent(Entity::remove);
        entities.remove(uuid);
    }

    @Override
    public Optional<ArmorStand> getEntity(UUID uuid) {
        return entities.entrySet().stream()
                .filter(uuidArmorStandEntry -> uuidArmorStandEntry.getKey().equals(uuid))
                .findFirst().map(Map.Entry::getValue);
    }

    @Override
    public HashMap<UUID, ArmorStand> getEntries() {
        return entities;
    }

    private ArmorStand spawn(Player player) {
        Location location = player.getLocation().clone().add(0, 2, 0);
        ArmorStand armorStand = player.getWorld().spawn(location, ArmorStand.class);
        armorStand.setMarker(true);
        armorStand.setCustomName(Utils.colorPlaceholders(player, tag));
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setSmall(true);
        armorStand.setBasePlate(false);

        return armorStand;
    }

    @Override
    public void onDisable() {
        entities.forEach((uuid, armorStand) -> armorStand.remove());
    }


    private class EntityUpdateTask extends BukkitRunnable {

        @Override
        public void run() {
            List<UUID> invalidEntries = new ArrayList<>();
            // Streams through all the entries to check if the entries' players are online
            // if not, add to the invalidEntries list
            entities.entrySet().stream()
                    .filter(uuidArmorStandEntry -> !Utils.getPlayer(uuidArmorStandEntry.getKey()).isPresent())
                    .forEach(uuidArmorStandEntry -> invalidEntries.add(uuidArmorStandEntry.getKey()));

            // Remove all invalid entries
            invalidEntries.forEach(EntityManagerProvider.this::remove);

            entities.forEach((uuid, armorStand) -> Utils.getPlayer(uuid).ifPresent(player -> {
                armorStand.setCustomName(Utils.colorPlaceholders(player, tag));
                armorStand.teleport(player.getLocation().add(0, 2, 0 ));
            }));
        }
    }
}
