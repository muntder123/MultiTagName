package com.muntder.tagname.listeners;

import com.muntder.tagname.api.EntityManager;
import com.muntder.tagname.api.TagNameAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerTagInitializer implements Listener {

    private final EntityManager entityManager;

    public PlayerTagInitializer() {
        entityManager = TagNameAPI.getInstance().getEntityManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        entityManager.add(player.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        entityManager.remove(player.getUniqueId());
    }
}
