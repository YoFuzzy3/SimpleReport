package com.fuzzoland.SimpleReport;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class CommandReport implements CommandExecutor{

	private SimpleReport plugin;
	
	public CommandReport(SimpleReport plugin){
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(args.length == 0){
			sender.sendMessage(ChatColor.GOLD + "/report submit - submit the book in your hand");
			sender.sendMessage(ChatColor.DARK_GREEN + "/report list - view all reports");
			sender.sendMessage(ChatColor.GOLD + "/report view <name> - get a report");
			sender.sendMessage(ChatColor.DARK_GREEN + "/report del <name> - delete a report");
		}else if(args.length >= 1){
			if(args[0].equalsIgnoreCase("submit")){
				if(sender.hasPermission("simplereport.submit")){
					if(sender instanceof Player){
						if(args.length == 1){
							Player player = (Player) sender;
							ItemStack item = player.getItemInHand().clone();
							if(item.getType() == Material.WRITTEN_BOOK){
								BookMeta meta = (BookMeta) item.getItemMeta();
								String title = meta.getTitle();
								if(!plugin.bh.isBook(title)){
									List<String> pages = new ArrayList<String>();
									for(String page : meta.getPages()){
										pages.add(page.replaceAll("§", "&"));
									}
									plugin.bh.updateBook(title, new Book(title, meta.getAuthor(), pages));
									sender.sendMessage(ChatColor.GREEN + "Report successfully submitted!");
								}else{
									sender.sendMessage(ChatColor.RED + "A report by this name already exists.");
								}
							}else{
					        	sender.sendMessage(ChatColor.RED + "The item must be a written book.");
					        }
						}else{
							sender.sendMessage(ChatColor.RED + "/report submit");
						}
					}else{
						sender.sendMessage(ChatColor.RED + "Only players can use this command.");
					}
				}else{
					sender.sendMessage(ChatColor.RED + "You do not have permission to use that command.");
				}
			}else if(args[0].equalsIgnoreCase("list")){
				if(sender.hasPermission("simplereport.list")){
					if(args.length == 1){
						sender.sendMessage(ChatColor.BLUE + "Reports:");
						for(String codename : plugin.bh.getBookNames()){
							sender.sendMessage(ChatColor.GREEN + "- " + codename);
						}
					}else{
						sender.sendMessage(ChatColor.RED + "/report list");
					}
				}else{
					sender.sendMessage(ChatColor.RED + "You do not have permission to use that command.");
				}
			}else if(args[0].equalsIgnoreCase("view")){
				if(sender.hasPermission("simplereport.view")){
					if(sender instanceof Player){
						if(args.length == 2){
							if(plugin.bh.isBook(args[1])){
								Player player = (Player) sender;
								Book book = plugin.bh.getBook(args[1]);
								ItemStack item = new ItemStack(Material.WRITTEN_BOOK, 1);
								BookMeta meta = (BookMeta) item.getItemMeta();
								meta.setTitle(book.getTitle());
								meta.setAuthor(book.getAuthor());
								for(String page : book.getPages()){
									meta.addPage(page.replaceAll("&", "§").replaceAll("Â", ""));
								}
								item.setItemMeta(meta);
								player.getInventory().addItem(item);
								player.updateInventory();
								sender.sendMessage(ChatColor.GREEN + "Report added to inventory!");
							}else{
								sender.sendMessage(ChatColor.RED + "A report by this name does not exist.");
							}
						}else{
							sender.sendMessage(ChatColor.RED + "/report view <name>");
						}
					}else{
						sender.sendMessage(ChatColor.RED + "Only players can use this command.");
					}
				}else{
					sender.sendMessage(ChatColor.RED + "You do not have permission to use that command.");
				}
			}else if(args[0].equalsIgnoreCase("del")){
				if(sender.hasPermission("simplereport.del")){
					if(args.length == 2){
						if(plugin.bh.isBook(args[1])){
							plugin.bh.updateBook(args[1], null);
							sender.sendMessage(ChatColor.GREEN + "Report successfully deleted!");
						}else{
							sender.sendMessage(ChatColor.RED + "A report by this name does not exist.");
						}
					}else{
						sender.sendMessage(ChatColor.RED + "/report del <name>");
					}
				}else{
					sender.sendMessage(ChatColor.RED + "You do not have permission to use that command.");
				}
			}else{
				sender.sendMessage(ChatColor.RED + "Type /report for help.");
			}
		}
		return true;
	}
}
