package dev.andreasgeorgatos.afkkicker.events;

import dev.andreasgeorgatos.afkkicker.dataholder.AFKPlayers;
import dev.andreasgeorgatos.afkkicker.dataholder.PlayerDataManager;
import dev.andreasgeorgatos.afkkicker.messages.Messenger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnPlayerMoveEvent implements Listener {

    private final Messenger messenger;
    private final AFKPlayers afkPlayers;
    private final PlayerDataManager playerDataManager;

    public OnPlayerMoveEvent(AFKPlayers afkPlayers, PlayerDataManager playerDataManager, Messenger messenger) {
        this.afkPlayers = afkPlayers;
        this.playerDataManager = playerDataManager;
        this.messenger = messenger;
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        if (afkPlayers.isPlayerAFK(e.getPlayer().getUniqueId())) {
            afkPlayers.removePlayer(e.getPlayer().getUniqueId());
            playerDataManager.removePlayerData(e.getPlayer().getUniqueId());

            e.getPlayer().sendMessage(messenger.getNoLongerAFKMessage());
        }
        if (playerDataManager.hasPlayerData(e.getPlayer().getUniqueId())) {
            playerDataManager.removePlayerData(e.getPlayer().getUniqueId());
        }
    }
}
