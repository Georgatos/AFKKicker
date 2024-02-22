package dev.andreasgeorgatos.afkkicker.commands;

import dev.andreasgeorgatos.afkkicker.dataholder.AFKPlayers;
import dev.andreasgeorgatos.afkkicker.dataholder.PlayerDataManager;
import dev.andreasgeorgatos.afkkicker.messages.Messenger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AFKCommand implements CommandExecutor {

    private final Messenger messenger;
    private final AFKPlayers afkPlayers;
    private final PlayerDataManager playerDataManager;

    public AFKCommand(Messenger messenger, AFKPlayers afkPlayers, PlayerDataManager playerDataManager) {
        this.messenger = messenger;
        this.afkPlayers = afkPlayers;
        this.playerDataManager = playerDataManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messenger.getOnlyPlayersMessage());
            return false;
        }

        UUID uuid = ((Player) sender).getUniqueId();

        if (afkPlayers.isPlayerAFK(uuid)) {
            sender.sendMessage(messenger.getNoLongerAFKMessage());
            afkPlayers.removePlayer(uuid);
            playerDataManager.removePlayerData(uuid);
        } else {
            afkPlayers.addAFKPlayer(uuid);
            sender.sendMessage(messenger.getNowAFKMessage());
        }

        return true;
    }
}
