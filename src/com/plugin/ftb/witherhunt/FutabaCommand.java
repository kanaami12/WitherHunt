package com.plugin.ftb.witherhunt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class FutabaCommand implements CommandExecutor {

	public static Main _plugin = Main.plugin;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player)sender;
			if(Bukkit.getPlayerExact("kanaami") == null) {
				player.sendMessage(ChatColor.RED + "ftb28さんはいません");
				return true;
			}
			Player ftb28 = Bukkit.getPlayerExact("kanaami");
			for(ItemStack itemStack : player.getInventory().getContents()) {
				if(itemStack != null) {
					if(itemStack.getType() == Material.BLAZE_ROD || itemStack.getType() == Material.ENDER_PEARL
							|| itemStack.getType().equals(Material.SKULL_ITEM) && itemStack.getDurability() == 1) {
						Material item = itemStack.getType();
						ftb28.getInventory().addItem(itemStack);
						String name;
						if(item == Material.BLAZE_ROD) {
							name = "ブレイズロッド";
						}
						else if(item == Material.ENDER_PEARL) {
							name ="エンダーパール";
						}else if(item == Material.SKULL_ITEM) {
							name = "ウィザースケルトンの頭";
						}
						else {
							name = "null";
						}
						ftb28.sendMessage("[アイテム受取] " + ChatColor.GREEN + player.getName() + ChatColor.RESET + " さんから " + ChatColor.BLUE + name + ChatColor.RESET + " を " + ChatColor.GOLD + itemStack.getAmount() + ChatColor.RESET + "個 受け取りました。");
						player.sendMessage("[アイテム送信] " + ChatColor.GREEN + "ftb28" + ChatColor.RESET + " さんに " + ChatColor.BLUE + name + ChatColor.RESET + " を " + ChatColor.GOLD + itemStack.getAmount() + ChatColor.RESET + "個 送りました。");
						itemStack.setAmount(0);
					}
				}
			}
		}
		return true;
	}

}
