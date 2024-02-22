package dev.andreasgeorgatos.afkkicker.dataholder;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {
    private final Map<UUID, PlayerData> playerDataMap;

    public PlayerDataManager() {
        playerDataMap = new HashMap<>();
    }

    public boolean hasPlayerData(UUID uuid) {
        return playerDataMap.containsKey(uuid);
    }

    public boolean hasReachedTheLimit(UUID uuid, FileConfiguration config) {
        if (playerDataMap.containsKey(uuid)) {
            if (config.get("locationLimit") == null) {
                return playerDataMap.get(uuid).getCount() >= 3;
            } else {
                return playerDataMap.get(uuid).getCount() >= config.getInt("locationLimit");
            }
        }
        return false;
    }

    public void addPlayerData(UUID uuid, Location location) {
        if (!playerDataMap.containsKey(uuid)) {
            PlayerData playerData = new PlayerData(location);
            playerDataMap.put(uuid, playerData);
        } else {
            PlayerData playerData = playerDataMap.get(uuid);
            playerData.increaseCount();
        }
    }

    public void removePlayerData(UUID uuid) {
        playerDataMap.remove(uuid);
    }

    public void clearData() {
        playerDataMap.clear();
    }
}
