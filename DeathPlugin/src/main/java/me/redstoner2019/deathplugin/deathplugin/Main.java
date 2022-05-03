package me.redstoner2019.deathplugin.deathplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.math.BigDecimal;


public final class Main extends JavaPlugin implements Listener {
    public HashMap<Player, Location> positions = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginCommand("death").setExecutor(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        player.sendMessage(ChatColor.AQUA + "To get back to death location do /death !");
        // player.sendMessage(ChatColor.DARK_RED + "You died!");
        // player.sendMessage(ChatColor.AQUA + "Your death-coordinates are: " + ChatColor.GOLD +  player.getLocation().getBlockX() + " " + player.getLocation().getBlockY() + " " + player.getLocation().getBlockZ() + " ");
        Bukkit.broadcastMessage(ChatColor.RED + event.getDeathMessage());
        event.setDeathMessage("");
        positions.put(player, player.getLocation());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(player.hasPermission("back.back") || player.isOp()) {
                if (positions.containsKey(player)) {
                        player.teleport(positions.get(player));
                        player.sendMessage(ChatColor.GOLD + "You got teleported to your last death point!");
                        positions.remove(player);
                    }else{
                        player.sendMessage(ChatColor.DARK_RED + "You don't have a death position!");
                    }
                }else{
                player.sendMessage(ChatColor.DARK_RED + "You don't have permission to use this command!");
            }
        }
        return true;
    }
}
