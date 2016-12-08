package shop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public Main instance;

	public void onEnable() {
		this.instance = this;
		saveDefaultConfig();
	}

	public void onDisable() {
		this.instance = null;
		reloadConfig();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			getServer().getConsoleSender().sendMessage(color("&4Only players may use this command!"));
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("shop")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("set")) {
					if (sender.isOp() || sender.hasPermission("shop.set")) {
						Player player = (Player) sender;

						this.getConfig().set("shop.world", player.getWorld().getName());
						this.getConfig().set("shop.x", Double.valueOf(player.getLocation().getX()));
						this.getConfig().set("shop.y", Double.valueOf(player.getLocation().getY()));
						this.getConfig().set("shop.z", Double.valueOf(player.getLocation().getZ()));
						this.saveConfig();

						player.sendMessage(color(this.getConfig().getString("messages.successfully-set-shop")));

						return true;
					} else {
						sender.sendMessage(color(this.getConfig().getString("messages.no-permission")));
					}
				} else if (args[0].equalsIgnoreCase("open")) {
					if (sender.isOp() || sender.hasPermission("shop.open")) {
						if (this.getConfig().getBoolean("messages.Is-The-Shop-Closed") == false) {
							this.getConfig().set("messages.Is-The-Shop-Closed", true);
							this.saveConfig();
							sender.sendMessage(color("&aShop is now open!"));
							return true;
						} else if (this.getConfig().getBoolean("messages.Is-The-Shop-Closed") == true) {
							sender.sendMessage(color("&6The shop is already open!"));
							return true;
						}
					} else {
						sender.sendMessage(color(this.getConfig().getString("messages.no-permission")));
					}
				} else if (args[0].equalsIgnoreCase("close")) {
					if (sender.isOp() || sender.hasPermission("shop.close")) {
						if (this.getConfig().getBoolean("messages.Is-The-Shop-Closed") == true) {
							this.getConfig().set("messages.Is-The-Shop-Closed", false);
							this.saveConfig();
							sender.sendMessage(color("&cShop is now closed!"));
							return true;
						} else if (this.getConfig().getBoolean("messages.Is-The-Shop-Closed") == false) {
							sender.sendMessage(color("&6The shop is already closed!"));
							return true;
						}
					} else {
						sender.sendMessage(color(this.getConfig().getString("messages.no-permission")));
					}
				} else if (args[0].equalsIgnoreCase("help")) {
					if (sender.isOp() || sender.hasPermission("shop.help")) {
						sender.sendMessage(new String[] { color("&3/shop set &7| &asets the shop location."),
								color("&3/shop reload &7| &areloads the shop config."),
								color("&3/shop open &7| &aopens the shop."),
								color("&3/shop close &7| &acloses the shop.") });
						return true;
					} else {
						sender.sendMessage(color(this.getConfig().getString("messages.no-permission")));
					}
				} else if (args[0].equalsIgnoreCase("reload")) {
					if (sender.isOp() || sender.hasPermission("shop.admin")) {
						this.reloadConfig();
						sender.sendMessage(color("&aConfig has been reloaded!"));
						return true;
					} else {
						sender.sendMessage(color(this.getConfig().getString("messages.no-permission")));
					}
				}
			}
			if (this.getConfig().getBoolean("messages.Is-The-Shop-Closed", true)) {
				if (args.length < 1) {
					if (sender.hasPermission("shop.teleport")) {
						Player player = (Player) sender;

						World w = Bukkit.getWorld(this.getConfig().getString("shop.world"));
						double x = this.getConfig().getDouble("shop.x");
						double y = this.getConfig().getDouble("shop.y");
						double z = this.getConfig().getDouble("shop.z");

						player.teleport(new Location(w, x, y, z));
						player.sendMessage(color(this.getConfig().getString("messages.arrived-to-shop")));
						return true;
					} else {
						sender.sendMessage(color(this.getConfig().getString("messages.no-permission")));
					}
				}
			} else {
				sender.sendMessage(color("&cThe shop is currently closed!"));
			}
		} else if (cmd.getName().equalsIgnoreCase("playershops")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("create")) {
					Player player = (Player) sender;
					String playername = player.getName();
					if (sender.isOp() || sender.hasPermission("playershops.create")) {
						
			Double playerx = Double.valueOf(player.getLocation().getX());
			Double playery = Double.valueOf(player.getLocation().getY());
			Double playerz = Double.valueOf(player.getLocation().getZ());
						
			this.getConfig().set("Shops." + playername, new String());
			this.getConfig().set("Shops." + playery, new String());
			this.getConfig().set("Shops." + playerz, new String());
			this.getConfig().set("Shops." + playerx, new String());
			this.saveConfig();
			
			player.sendMessage(color(this.getConfig().getString("messages.Created-player-shop")));
			return true;
		} else if(this.getConfig().contains("Shops." + playername)) {
			player.sendMessage(ChatColor.RED + "Your shop already exists!");
				}
			} else {
				sender.sendMessage(color(this.getConfig().getString("messages.no-permission")));
			}
			}
		}
		return false;
	}
public String color(String message) {
	return ChatColor.translateAlternateColorCodes('&', message);
}
}
