package com.muntder.tagname;

import com.muntder.tagname.api.TagNameAPI;
import com.muntder.tagname.api.registry.ListenerRegistry;
import com.muntder.tagname.core.implementation.TagAPIProvider;
import com.muntder.tagname.core.implementation.registry.ListenerRegistryProvider;
import com.muntder.tagname.listeners.PlayerTagDeathListener;
import com.muntder.tagname.listeners.PlayerTagInitializer;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class MultiTagNamePlugin extends JavaPlugin {

    @Getter
    private static MultiTagNamePlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        saveConfig();

        new TagAPIProvider(this);

        ListenerRegistry listenerRegistry = new ListenerRegistryProvider(this);
        listenerRegistry.register(
                new PlayerTagInitializer(),
                new PlayerTagDeathListener()
        );

    }

    @Override
    public void onDisable() {
        TagNameAPI.getInstance().onDisable();
    }

}
