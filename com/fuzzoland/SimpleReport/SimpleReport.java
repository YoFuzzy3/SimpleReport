package com.fuzzoland.SimpleReport;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

public class SimpleReport extends JavaPlugin{

	public static File datafolder;
	public BookHandler bh;
	
	public void onEnable(){
		File booksFolder = new File(getDataFolder() + "/books");
		if(!booksFolder.exists()){
			booksFolder.mkdirs();
		}
		bh = new BookHandler(booksFolder);
		try{
			bh.loadBooks();
		}catch(IOException e){
			e.printStackTrace();
		}
		getCommand("report").setExecutor(new CommandReport(this));
		datafolder = getDataFolder();
	}
	
	public void onDisable(){
		try{
			bh.saveBooks();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
