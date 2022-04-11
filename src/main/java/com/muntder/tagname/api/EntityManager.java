package com.muntder.tagname.api;

import com.muntder.tagname.api.other.Disableable;
import org.bukkit.entity.ArmorStand;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public interface EntityManager extends Disableable {

    void update(UUID uuid);

    void add(UUID uuid);

    void remove(UUID uuid);

    Optional<ArmorStand> getEntity(UUID uuid);

    HashMap<UUID, ArmorStand> getEntries();

}
