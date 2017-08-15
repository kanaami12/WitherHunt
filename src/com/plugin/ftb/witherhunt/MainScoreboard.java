package com.plugin.ftb.witherhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class MainScoreboard {
	
	public static Main plugin = Main.plugin;
	
	public static int numberOfNether = 0;
	public static int numberOfHigher = 0;
	public static int numberOfLower = 0;
	
	public static World nether;
	
	//スコアボードを登録
	public static void registerScoreboard() {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		
		Objective object = board.getObjective("NumberOfPeople");
		if(object == null) {
			//新規オブジェクトを登録
			object = board.registerNewObjective("NumberOfPeople", "dummy");
			
			//オブジェクトの表示名を設定
			object.setDisplayName("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "≫ Wither Hunt ≪");
			
			//オブジェクトの表示位置を設定
			object.setDisplaySlot(DisplaySlot.SIDEBAR);	
			
			//オブジェクトの登録
			object.getScore("" + ChatColor.RED + ChatColor.BOLD + "ネザーの人  " + ChatColor.RESET + ":   ").setScore(numberOfNether);
			object.getScore("" + ChatColor.RED + ChatColor.BOLD + "地下の人  " + ChatColor.RESET + ":   ").setScore(numberOfLower);
			object.getScore("" + ChatColor.RED + ChatColor.BOLD + "地上の人  " + ChatColor.RESET + ":   ").setScore(numberOfHigher);
		}
		
		//ネザーワールドを取得
		for(World world : Bukkit.getWorlds()) {
			if(world.getEnvironment().equals(Environment.NETHER)){
				nether = world;
				break;
			}
		}
	}
	
	//タスクを開始
	public static void startTask(int second) {
		 new BukkitRunnable() {
            @Override
            public void run() {
            	updateScore();
            }
        }.runTaskTimer(plugin, 0, second * 20);
	}
	
	//スコアを更新
	public static void updateScore() {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Objective object = board.getObjective("NumberOfPeople");
		
		setNumber();
		
		object.getScore("" + ChatColor.RED + ChatColor.BOLD + "ネザーの人  " + ChatColor.RESET + ":   ").setScore(nether.getPlayers().size());
		object.getScore("" + ChatColor.RED + ChatColor.BOLD + "地下の人  " + ChatColor.RESET + ":   ").setScore(numberOfLower);
		object.getScore("" + ChatColor.RED + ChatColor.BOLD + "地上の人  " + ChatColor.RESET + ":   ").setScore(numberOfHigher);
	}
	
	//スコアボードをプレイヤーにセット
	public static void setScoreToPlayer(Player player) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Objective object = board.getObjective("NumberOfPeople");
		player.setScoreboard(board);
	}
	
	//地下、地上の人の人数を数える
	public static void setNumber() {
		
		numberOfHigher = 0;
		numberOfLower = 0;
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.getLocation().getBlock().getBiome() != Biome.HELL) {
				if(player.getLocation().getBlockY() < 62) {
					numberOfLower += 1;
				}
				else {
					numberOfHigher += 1;
				}
			}
		}
	}
}