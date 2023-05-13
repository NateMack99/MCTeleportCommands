package me.plugin.teleportcommands.utils;
import me.plugin.teleportcommands.TeleportCommands;
import me.plugin.teleportcommands.events.CustomTeleportEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.ArrayList;

public class TPA extends Teleporter{
    private static ArrayList<TPA> pendingTPA = new ArrayList<>();
    private static ArrayList<TPA> pendingTPHere = new ArrayList<>();
    private Player receiver;

    public TPA() {}
    public TPA(Player player, Player receiver, Location location) {
        super(player, location, "tpa");
        this.receiver = receiver;
    }

    public Player getReceiver() {
        return receiver;
    }

    public void sendRequestTPA() {
        receiver.sendMessage(player.getDisplayName() + " would like to teleport you you" );
        receiver.sendMessage( "-\"/tpaccept\" to accept");
        receiver.sendMessage( "-\"/tpdeny\" to deny");
        deletePendingTPA(player);
        deletePendingTPHere(receiver);
        pendingTPA.add(this);
    }

    public void sendRequestTPHERE(){
        player.sendMessage(receiver.getDisplayName() + " would like you to teleport to them" );
        player.sendMessage( "-\"/tpaccept\" to accept");
        player.sendMessage( "-\"/tpdeny\" to deny");
        deletePendingTPA(player);
        deletePendingTPHere(receiver);
        pendingTPHere.add(this);
    };

    public static void acceptTPA(Player p) {
        TPA tpa = null;
        for (int i = 0; i < pendingTPA.size(); i++) {
            if (p.equals(pendingTPA.get(i).getReceiver())) {
                tpa = pendingTPA.get(i);
                break;
            }
        }
        for (int i = 0; i < pendingTPHere.size(); i++) {
            if (p.equals(pendingTPHere.get(i).getReceiver())) {
                tpa = pendingTPHere.get(i);
                break;
            }
        }
        if (tpa == null) {
            p.sendMessage(ChatColor.RED + "Teleport request no longer valid");
            return;
        }
        tpa.execute();

    }
    public static void denyTPA(Player p) {
        TPA tpa = null;
        for (int i = 0; i < pendingTPA.size(); i++) {
            if (p.equals(pendingTPA.get(i).getReceiver())) {
                tpa = pendingTPA.get(i);
                break;
            }
        }
        for (int i = 0; i < pendingTPHere.size(); i++) {
            if (p.equals(pendingTPHere.get(i).getReceiver())) {
                tpa = pendingTPHere.get(i);
                break;
            }
        }
        if (tpa == null) {
            p.sendMessage(ChatColor.RED + "Teleport request no longer valid");
            return;
        }
        tpa.execute();

    }

    public void execute() {
        CustomTeleportEvent event = new CustomTeleportEvent(player, receiver.getLocation(), this);
        Bukkit.getScheduler().scheduleSyncDelayedTask(TeleportCommands.plugin, () -> {
            if (!event.isCancelled()) {
                player.teleport(receiver.getLocation());
                deletePendingTPA(player);
                deletePendingTPHere(receiver);
            }
        }, 20 * 5);
    }

    private void deletePendingTPA(Player player) {
        for (int i = 0; i < pendingTPA.size(); i++) {
            if (player.equals(pendingTPA.get(i).getPlayer())) {
                pendingTPA.remove(i);
            }
        }
    }
    private void deletePendingTPHere(Player receiver_method) {
        for (int i = 0; i < pendingTPA.size(); i++) {
            if (receiver_method.equals(pendingTPA.get(i).getReceiver())) {
                pendingTPA.remove(i);
            }
        }
    }
}
