package dev.andreasgeorgatos.afkkicker;

import dev.andreasgeorgatos.afkkicker.commands.AFKCommand;
import dev.andreasgeorgatos.afkkicker.dataholder.AFKPlayers;
import dev.andreasgeorgatos.afkkicker.dataholder.PlayerDataManager;
import dev.andreasgeorgatos.afkkicker.events.OnPlayerMoveEvent;
import dev.andreasgeorgatos.afkkicker.events.OnPlayerQuitEvent;
import dev.andreasgeorgatos.afkkicker.files.FileManager;
import dev.andreasgeorgatos.afkkicker.messages.Messenger;
import dev.andreasgeorgatos.afkkicker.scheduler.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AFKKicker extends JavaPlugin {

    private final Logger logger = LoggerFactory.getLogger(AFKKicker.class);
    private FileManager fileManager;
    private Messenger messenger;
    private AFKPlayers afkPlayers;
    private PlayerDataManager playerDataManager;
    private BukkitTask bukkitTask;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        logger.info("The plugin AFK Kicker has been successfully loaded.");
        initialize();
        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
        if (bukkitTask != null) {
            bukkitTask.cancel();
        }
        playerDataManager.clearData();
        afkPlayers.clearData();
        logger.info("The plugin AFK Kicker has been successfully disabled.");
    }

    private void registerCommands() {
        this.getCommand("afk").setExecutor(new AFKCommand(messenger, afkPlayers, playerDataManager));
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new OnPlayerQuitEvent(afkPlayers, playerDataManager), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerMoveEvent(afkPlayers, playerDataManager, messenger), this);
    }

    private void initialize() {
        afkPlayers = new AFKPlayers();
        playerDataManager = new PlayerDataManager();
        fileManager = new FileManager(this.getDataFolder());
        messenger = new Messenger(fileManager);

        long delay = getConfig().get("delay") != null ? getConfig().getLong("delay") : 5L;

        bukkitTask = new Scheduler(afkPlayers, playerDataManager, messenger, this.getConfig()).runTaskTimer(this, (60 * 20) * delay, (60 * 20) * delay);
    }
}