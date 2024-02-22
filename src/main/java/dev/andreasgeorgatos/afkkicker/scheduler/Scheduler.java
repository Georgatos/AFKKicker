package dev.andreasgeorgatos.afkkicker.scheduler;

import dev.andreasgeorgatos.afkkicker.dataholder.AFKPlayers;
import dev.andreasgeorgatos.afkkicker.dataholder.PlayerDataManager;
import dev.andreasgeorgatos.afkkicker.messages.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Scheduler extends BukkitRunnable {

    private final Logger logger = LoggerFactory.getLogger(Scheduler.class);

    private final AFKPlayers afkPlayers;
    private final PlayerDataManager playerDataManager;
    private final Messenger messenger;
    private final FileConfiguration config;

    private final double minimumTPS;
    private final LocalTime startingHour;
    private final LocalTime finishingHour;

    public Scheduler(AFKPlayers afkPlayers, PlayerDataManager playerDataManager, Messenger messenger, FileConfiguration config) {
        this.afkPlayers = afkPlayers;
        this.playerDataManager = playerDataManager;
        this.messenger = messenger;
        this.config = config;

        minimumTPS = getMinimumTps(config);
        startingHour = getHour(config, "startingHour");
        finishingHour = getHour(config, "finishingHour");
    }

    @Override
    public void run() {
        if (!LocalTime.now().isAfter(startingHour) && !LocalTime.now().isBefore(finishingHour)) {
            return;
        }

        if (Bukkit.getTPS()[0] > minimumTPS || Bukkit.getTPS()[1] > minimumTPS) {
            return;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (afkPlayers.isPlayerAFK(player.getUniqueId())) {
                kickPlayer(player);
            } else {
                if (playerDataManager.hasReachedTheLimit(player.getUniqueId(), config)) {
                    kickPlayer(player);
                } else {
                    playerDataManager.addPlayerData(player.getUniqueId(), player.getLocation());
                }
            }
        }
    }

    private void kickPlayer(Player player) {
        playerDataManager.removePlayerData(player.getUniqueId());
        afkPlayers.removePlayer(player.getUniqueId());
        logger.info("Player: " + player.getName() + " has been kicked from the server.");
        player.kick(messenger.getKickMessage());
    }

    //    We are not using the ternary operator for readability, the following code can  be replaced with
//    return config.get(path) == null ? LocalTime.now() : LocalTime.parse(config.getString(path), DateTimeFormatter.ofPattern("HH:mm"));
    private LocalTime getHour(FileConfiguration config, String path) {
        if (config.get(path) == null) {
            return LocalTime.now();
        }
        return LocalTime.parse(config.getString(path), DateTimeFormatter.ofPattern("HH:mm"));
    }

    private double getMinimumTps(FileConfiguration config) {
        return config.get("TPS") != null ? config.getInt("TPS") : 17;
    }
}
