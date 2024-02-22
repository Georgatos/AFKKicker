package dev.andreasgeorgatos.afkkicker.messages;

import com.google.gson.JsonObject;
import dev.andreasgeorgatos.afkkicker.files.FileManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

public class Messenger {
    private final FileManager fileManager;

    public Messenger(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public TextComponent getKickMessage() {
        JsonObject jsonObject = fileManager.getJson();

        if (!jsonObject.has("kickMessage")) {
            return Component.text("You have been kicked from the server!");
        }

        JsonObject onlyPlayers = jsonObject.getAsJsonObject("kickMessage");

        return getMessage(onlyPlayers);
    }


    public TextComponent getNowAFKMessage() {
        JsonObject jsonObject = fileManager.getJson();

        if (!jsonObject.has("nowAFK")) {
            return Component.text("You are now AFK!");
        }

        JsonObject onlyPlayers = jsonObject.getAsJsonObject("nowAFK");

        return getMessage(onlyPlayers);
    }

    public TextComponent getNoLongerAFKMessage() {
        JsonObject jsonObject = fileManager.getJson();

        if (!jsonObject.has("noLongerAFK")) {
            return Component.text("You are no longer AFK!");
        }

        JsonObject onlyPlayers = jsonObject.getAsJsonObject("noLongerAFK");

        return getMessage(onlyPlayers);
    }

    public TextComponent getOnlyPlayersMessage() {
        JsonObject jsonObject = fileManager.getJson();

        if (!jsonObject.has("onlyPlayers")) {
            return Component.text("Only players can run this command!");
        }

        JsonObject onlyPlayers = jsonObject.getAsJsonObject("onlyPlayers");

        return getMessage(onlyPlayers);
    }

    @NotNull
    private TextComponent getMessage(JsonObject onlyPlayers) {
        TextComponent component = Component.empty();

        if (onlyPlayers.has("text")) {
            if (onlyPlayers.has("text")) {
                component.append(Component.text(onlyPlayers.get("text").getAsString()));
            }
            if (onlyPlayers.has("chatColor")) {
                component.color(TextColor.fromCSSHexString(onlyPlayers.get("chatColor").getAsString()));
            }
        }
        return component;
    }
}
