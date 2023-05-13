package me.plugin.teleportcommands.commands;

import me.plugin.teleportcommands.TeleportCommands;
import me.plugin.teleportcommands.utils.TPA;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TPACommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player player = (Player)(commandSender);
        if (strings.length < 2) {
            player.sendMessage(Component.text("Please input valid player name").color(TeleportCommands.ERROR_COLOR));
            player.sendMessage(Component.text("/tpa <player>").color(TeleportCommands.ERROR_COLOR));
        }
        StringBuilder name = new StringBuilder();
        for (int i = 1; i < strings.length - 1; i++) {
             name.append(strings[i] + " ");
        }
        name.append(strings[strings.length - 1]);
        boolean found = false;
        for (Player p : TeleportCommands.plugin.getServer().getOnlinePlayers()) {
            if (name.equals(p.getName()) && name.equals(p.displayName().toString())) {
                TPA tpa = new TPA(player, p, p.getLocation());
                tpa.sendRequestTPA();
                found = true;
            }
        }
        if (!found) {
            player.sendMessage(Component.text("Please input valid player name").color(TeleportCommands.ERROR_COLOR));
            player.sendMessage(Component.text("/tpa <player>").color(TeleportCommands.ERROR_COLOR));
        }
        return true;
    }

}
