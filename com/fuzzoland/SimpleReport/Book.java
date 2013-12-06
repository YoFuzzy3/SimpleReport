package com.fuzzoland.SimpleReport;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class Book{

	private String title;
	private String author;
	private List<String> pages;
	
	public Book(String title, String author, List<String> pages){
		this.title = title;
		this.author = author;
		this.pages = pages;
	}
	
	public void save(String codename) throws IOException{
		YamlConfiguration book = new YamlConfiguration();
		book.set("Title", this.title);
		book.set("Author", this.author);
		book.set("Pages", this.pages);
		book.save(new File(SimpleReport.datafolder + "/books", codename + ".yml"));
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getAuthor(){
		return this.author;
	}
	
	public void setAuthor(String author){
		this.author = author;
	}
	
	public List<String> getPages(){
		return this.pages;
	}
	
	public void setPages(List<String> pages){
		this.pages = pages;
	}
}
