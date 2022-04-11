package com.muntder.tagname.listeners;

import com.muntder.tagname.api.EntityManager;
import com.muntder.tagname.api.TagNameAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerTagDeathListener implements Listener {

    private final EntityManager entityManager;

    public PlayerTagDeathListener() {
        entityManager = TagNameAPI.getInstance().getEntityManager();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        entityManager.remove(player.getUniqueId());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        entityManager.add(player.getUniqueId());
    }
}
