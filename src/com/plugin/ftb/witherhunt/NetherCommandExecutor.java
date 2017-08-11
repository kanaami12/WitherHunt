package com.plugin.ftb.witherhunt;

import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

public class NetherCommandExecutor implements CommandExecutor {

	private Plugin plugin;

	public NetherCommandExecutor(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage(ChatColor.GREEN + "===== " + ChatColor.DARK_RED + "ネザー要塞を見つけたプレイヤー" + ChatColor.GREEN + " =====");
		for (Entry<Player, Location> en : Main.netheryousai.entrySet()) {
			Player p = en.getKey();
			Location loc = en.getValue();
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			sender.sendMessage(
					ChatColor.BOLD + p.getName() + " " + ChatColor.RESET + "(座標: " + x + ", " + y + ", " + z + ")");
		}
		sender.sendMessage(ChatColor.GREEN + "===== " + ChatColor.DARK_RED + "ネザー要塞を見つけたプレイヤー" + ChatColor.GREEN + " =====");

		return true;
	}
}
