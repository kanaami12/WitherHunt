package com.plugin.ftb.witherhunt;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class MainListener implements Listener {

	private Main plugin = Main.plugin;
	
	//火打石を使ったプレイヤー
	private ArrayList<Player> firedPlayer = new ArrayList<>();
	//巣カルブロックを置いたプレイヤー
	private ArrayList<Player> skullPlayer = new ArrayList<>();	
	
	/*
	 * アイテムを拾ったとき、プレイヤーネームとアイテムを表示
	 */
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		ItemStack itemStack = event.getItem().getItemStack();
		Material material = itemStack.getType();
		String itemName = "";
		
		if(material.equals(Material.SKULL_ITEM) && event.getItem().getItemStack().getDurability() == 1) {
			itemName = ChatColor.DARK_PURPLE + "ウィザースケルトンの頭";
			sendPickupMessage("[" + ChatColor.DARK_PURPLE + "ウィザスケ" + ChatColor.RESET + "]", itemName, player);
		}
		if(material.equals(Material.DIAMOND_SWORD) && itemStack.getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_MOBS) == 3) {
			itemName = ChatColor.GREEN + "ドロップ増加Ⅲのダイヤの剣";
			sendPickupMessage("[" + ChatColor.GREEN + "ダイヤ剣" + ChatColor.RESET + "]", itemName, player);
		}
	}
	
	/*
	 * アイテムをエンチャントしたとき、プレイヤーネームとアイテムを表示
	 */
	@EventHandler
	public void onEnchant(EnchantItemEvent event) {
		Player player = event.getEnchanter();
		ItemStack itemStack = event.getItem();
		Material material = itemStack.getType();
		String itemName = "";
		if(material.equals(Material.DIAMOND_SWORD) && event.getEnchantsToAdd().get(Enchantment.LOOT_BONUS_MOBS) == 3) {
			itemName = ChatColor.GREEN + "ドロップ増加Ⅲのダイヤの剣";
			sendPickupMessage("[" + ChatColor.GREEN + "ダイヤ剣" + ChatColor.RESET + "]", itemName, player);
		}
	}
	
	/*
	 * アイテムをエンチャントしたとき、プレイヤーネームとアイテムを表示
	 */
	@EventHandler
	public void onClickInv(InventoryClickEvent event) {
		Inventory inv = event.getClickedInventory();
		if(inv != null &&inv.getType().equals(InventoryType.ANVIL) && event.getSlotType().equals(SlotType.RESULT)) {
			Player player = (Player) event.getWhoClicked();
			ItemStack itemStack = event.getCurrentItem();
			if(itemStack == null) {
				return;
			}
			Material material = itemStack.getType();
			String itemName = "";
			if(material.equals(Material.DIAMOND_SWORD) &&  itemStack.getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_MOBS) == 3) {
				itemName = ChatColor.GREEN + "ドロップ増加Ⅲのダイヤの剣";
				sendPickupMessage("[" + ChatColor.GREEN + "ダイヤ剣" + ChatColor.RESET + "]", itemName, player);
			}
		}
	}
	
	/*
	 * ネザーポータルが作られたとき通知する
	 */
	@EventHandler
    public void onClick(PlayerInteractEvent event) {
		//火打石をもって黒曜石を右クリックしたプレイヤーを記録(2tick後に削除)
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			ItemStack item = event.getItem();
			if(item != null && item.getType().equals(Material.FLINT_AND_STEEL)) {
				Material material = event.getClickedBlock().getType();
				if(material != null && material.equals(Material.OBSIDIAN)) {
					//火打石をもって黒曜石を右クリックしたとき
					firedPlayer.add(event.getPlayer());
					new BukkitRunnable() {
			            @Override
			            public void run() {
			            	//2tick後に削除
			                firedPlayer.remove(event.getPlayer());
			            }
			        }.runTaskLater(plugin, 2);
					return;
				}
			}
		}
	}
	
	/*
	 * 火打石をもって黒曜石を右クリックしたプレイヤーがポータルを作ったと推定
	 */
	@EventHandler
    public void onPortalCreate(PortalCreateEvent event){
		if(!firedPlayer.isEmpty()) {
			for(Block block : event.getBlocks()) {
				if(block.getType().equals(Material.OBSIDIAN)) {
					broadcast("[" + ChatColor.DARK_RED + "ネザー" + ChatColor.RESET + "]" + " " + ChatColor.BOLD + firedPlayer.get(firedPlayer.size()-1).getName() + ChatColor.RESET + "さんが"
								+ ChatColor.DARK_RED + "ネザーポータル" + ChatColor.RESET + "を開いた(座標: " + block.getLocation().getBlockX() + ", " + block.getLocation().getBlockY() + ", " + block.getLocation().getBlockZ() + ")");
					return;
				}
			}
		}
	}
	
	/*
	 * ブロックを置いたとき通知する
	 */
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Block block = event.getBlock();
		
		if(block.getType().equals(Material.ENCHANTMENT_TABLE)) {
			Location loc = event.getBlock().getLocation();
			broadcast("[" + ChatColor.GREEN + "エンチャ" + ChatColor.RESET + "]" + " " + ChatColor.BOLD + event.getPlayer().getName() + ChatColor.RESET + "さんが"
					+ ChatColor.GREEN + "エンチャントテーブル" + ChatColor.RESET + "を置いた(座標: " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")");
		}
		if(block.getType().equals(Material.SKULL)) {
			if(block.getLocation().add(0, -0.5, 0).getBlock().getType().equals(Material.SOUL_SAND)) {
				//ソウルサンドの上に巣カルブロックを置いたプレイヤーを記録
				skullPlayer.add(event.getPlayer());
				new BukkitRunnable() {
		            @Override
		            public void run() {
		            	//2tick後に削除
		                skullPlayer.remove(event.getPlayer());
		            }
		        }.runTaskLater(plugin, 2);
			}
		}
		
	}
	
	/*
	 * ウィザーがスポーンしたとき通知
	 */
	@EventHandler
	public void onSpawn(CreatureSpawnEvent event) {
		if(event.getEntityType().equals(EntityType.WITHER)) {
			if(!skullPlayer.isEmpty()) {
				broadcast("[" + ChatColor.DARK_PURPLE + "ウィザー" + ChatColor.RESET + "]" + " " + ChatColor.BOLD + skullPlayer.get(skullPlayer.size()-1).getName() + ChatColor.RESET + "さんが"
						+ ChatColor.DARK_PURPLE + "ウィザー" + ChatColor.RESET + "を召喚した(座標: " + event.getLocation().getBlockX() + ", " + event.getLocation().getBlockY() + ", " + event.getLocation().getBlockZ() + ")");
			}else {
				broadcast("[" + ChatColor.DARK_PURPLE + "ウィザー" + ChatColor.RESET + "]" + ChatColor.DARK_PURPLE + "ウィザー" + ChatColor.RESET + "を召喚した"
						+ "(座標: " + event.getLocation().getBlockX() + ", " + event.getLocation().getBlockY() + ", " + event.getLocation().getBlockZ() + ")");
			}
		}
	}
	
	/*
	 * エンダードラゴンが倒されたとき、通知する
	 */
	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		String playerName = "";
		if(event.getEntity().getKiller() != null) {
			playerName =  ChatColor.BOLD + event.getEntity().getKiller().getName() + ChatColor.RESET + "さんが";
		}
		if(event.getEntity() instanceof Wither) {
			broadcast("[" + ChatColor.DARK_PURPLE + "おめでとう！" + ChatColor.RESET + "]"  +  " " + playerName + ChatColor.DARK_PURPLE + "ウィザー" + ChatColor.RESET + "を倒した");
		}
	}
	
	
	/*
	 * 拾ったアイテムを通知
	 * @param itemName 拾ったアイテムの名前
	 * @param player 拾ったプレイヤー
	 */
	private void sendPickupMessage(String prefix, String itemName, Player player) {
		broadcast(prefix + " " + ChatColor.BOLD + player.getName() + ChatColor.RESET + "さんが"
				+ itemName + ChatColor.RESET + "を手に入れた");
	}
	
	/*
	 * ブロードキャストの省略メソッドです。
	 * @param message ブロードキャストするメッセージ 
	 */
	public static void broadcast(String message) {
		Bukkit.broadcastMessage(message);
	}
}
