package com.plugin.ftb.witherhunt;

import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin{
	
	public static String prefix = ChatColor.GRAY + "[エンドラ討伐]" + ChatColor.RESET;
	public static Main plugin;
	
	public static boolean isHard = false;
	
	@Override
	public void onEnable() {
		plugin = this;
		
		// イベントリスナ登録
		getServer().getPluginManager().registerEvents(new MainListener(), this);
		
		//コマンド登録
		getCommand("ftb28").setExecutor(new FutabaCommand());
		
		//タスク開始
		new MainTask().runTaskTimer(plugin, 0, 20);
	}
}