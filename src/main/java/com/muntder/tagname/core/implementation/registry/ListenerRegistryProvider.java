package com.muntder.tagname.core.implementation.registry;

import com.muntder.tagname.api.registry.ListenerRegistry;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@RequiredArgsConstructor
public class ListenerRegistryProvider implements ListenerRegistry {

    private final JavaPlugin plugin;

    @Override
    public void register(Listener... types) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        Arrays.stream(types).forEach(listener -> pluginManager.registerEvents(listener, plugin));
    }
}
