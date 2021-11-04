package com.muntder.tagname.core.implementation;

import com.muntder.tagname.api.EntityManager;
import com.muntder.tagname.api.TagNameAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class TagAPIProvider extends TagNameAPI {

    private final EntityManager entityManager;

    public TagAPIProvider(JavaPlugin plugin) {
        entityManager = new EntityManagerProvider(plugin);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void onDisable() {
        entityManager.onDisable();
    }
}
