package com.plugin.ftb.witherhunt;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnTask extends BukkitRunnable{

	@Override
	public void run() {
		for(Player player : MainScoreboard.nether.getPlayers()) {
			Location loc = player.getLocation().add(-0.5, -0.5, -0.5);
			
			for(int x=-16; x<16; x++) {
				if(Math.abs(x) <= 6) continue;		//6マス以内にはわかないように
				for(int z=-16; z<16; z++) {
					if(Math.abs(z) <= 6) continue;	//6マス以内にはわかないように
					//0.4%の確率でスポーン
					if(new Random().nextInt(1000) < 4) {
						Location searchLoc = loc.clone().add(x, 0, z);
					
						if(searchLoc.getBlock().getType().equals(Material.NETHER_BRICK)) {
							if(searchLoc.clone().add(0, 1, 0).getBlock().getType().equals(Material.AIR)) {
								if(searchLoc.clone().add(0, 2, 0).getBlock().getType().equals(Material.AIR)) {
									if(searchLoc.clone().add(0, 3, 0).getBlock().getType().equals(Material.AIR)) {
										MainScoreboard.nether.spawnEntity(searchLoc.clone().add(0, 1, 0), EntityType.WITHER_SKELETON);
									}
								}
							}
						}
					}
				}
			}
		}
	}

}
