package com.plugin.ftb.witherhunt;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin{
	
	public static String prefix = ChatColor.GRAY + "[ウィザー討伐]" + ChatColor.RESET;
	public static Main plugin;
	
	public static boolean isHard = false;
	
	public static Map<Player, Location> netheryousai = new HashMap<>();
	public static Map<Player, Location> netherportal = new HashMap<>();
	
	@Override
	public void onEnable() {
		plugin = this;
		
		// イベントリスナ登録
		getServer().getPluginManager().registerEvents(new MainListener(), this);
		
		//コマンド登録
		getCommand("ftb28").setExecutor(new FutabaCommand());
		getCommand("nether").setExecutor(new NetherCommandExecutor(this));
		getCommand("portal").setExecutor(new PortalCommandExecutor(this));
		
		//タスク開始
		new MainTask().runTaskTimer(plugin, 0, 20);
		new SpawnTask().runTaskTimer(plugin, 0, 40);
		
		//スコア登録
		MainScoreboard.registerScoreboard();
	
		//2秒おきにスコア更新
		MainScoreboard.startTask(2);
	}
}
