package com.plugin.ftb.witherhunt;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class MainTask extends BukkitRunnable {

	private ArrayList<UUID> arrived = new ArrayList<>();
	
	@Override
	public void run() {
		for(Player player : MainScoreboard.nether.getPlayers()) {
			Location loc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
			//ネザーにいる場合
			for(int x=-3; x<3; x+=2) {
				for(int y=-3; y<3; y+=2) {
					for(int z=-3; z<3; z+=2) {
						Location searchLoc = new Location(loc.getWorld(), loc.getX()+x, loc.getY()+y, loc.getZ()+z);
						if(searchLoc.getBlock().getType().equals(Material.NETHER_BRICK)) {
							if(!arrived.contains(player.getUniqueId())) {
								int x1 = loc.getBlockX();
								int y1 = loc.getBlockY();
								int z1 = loc.getBlockZ();
								Bukkit.broadcastMessage("[" + ChatColor.DARK_RED + "ネザー" + ChatColor.RESET
										+ "]" + " " + ChatColor.BOLD + player.getName() + ChatColor.RESET + "さんが"
										+ ChatColor.DARK_RED + "ネザー要塞" + ChatColor.RESET + "を見つけた(座標: " + x1
										+ ", " + y1 + ", " + z1 + ")");
								arrived.add(player.getUniqueId());
								Main.netheryousai.put(player, new Location(player.getWorld(), x1, y1, z1));
							}
						}
					}
				}
			}
		}
	}
}
