package dev.andreasgeorgatos.afkkicker.events;

import dev.andreasgeorgatos.afkkicker.dataholder.AFKPlayers;
import dev.andreasgeorgatos.afkkicker.dataholder.PlayerDataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerQuitEvent implements Listener {

    private final AFKPlayers afkPlayers;
    private final PlayerDataManager playerDataManager;

    public OnPlayerQuitEvent(AFKPlayers afkPlayers, PlayerDataManager playerDataManager) {
        this.afkPlayers = afkPlayers;
        this.playerDataManager = playerDataManager;
    }


    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        afkPlayers.removePlayer(e.getPlayer().getUniqueId());
        playerDataManager.removePlayerData(e.getPlayer().getUniqueId());

    }
}
