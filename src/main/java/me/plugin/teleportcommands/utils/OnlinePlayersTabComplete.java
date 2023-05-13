package me.plugin.teleportcommands.utils;

import me.plugin.teleportcommands.TeleportCommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class OnlinePlayersTabComplete implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> playerNames = new ArrayList<>();
        for (Player player : TeleportCommands.plugin.getServer().getOnlinePlayers()) {
            playerNames.add(player.getName());
            playerNames.add(player.displayName().toString());
        }
        return playerNames;
    }
}
