package com.plugin.ftb.witherhunt;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class MainScoreboard {
	
	public static Main plugin = Main.plugin;
	
	public static int numberOfNether = 0;
	
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
		
		object.getScore("" + ChatColor.RED + ChatColor.BOLD + "ネザーの人  " + ChatColor.RESET + ":   ").setScore(nether.getPlayers().size());
	}
	
	//スコアボードをプレイヤーにセット
	public static void setScoreToPlayer(Player player) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Objective object = board.getObjective("NumberOfPeople");
		player.setScoreboard(board);
	}
}