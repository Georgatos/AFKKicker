package dev.andreasgeorgatos.afkkicker.dataholder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AFKPlayers {
    private final Set<UUID> players;

    public AFKPlayers() {
        players = new HashSet<>();
    }

    public void addAFKPlayer(UUID player) {
        players.add(player);
    }

    public void removePlayer(UUID player) {
        players.removeIf(uuid -> uuid.equals(player));
    }

    public boolean isPlayerAFK(UUID uuid) {
        return players
                .stream()
                .anyMatch(player -> player.equals(uuid));
    }

    public void clearData() {
        players.clear();
    }
}
