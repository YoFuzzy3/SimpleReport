package com.fuzzoland.SimpleReport;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

public class BookHandler{

	private Map<String, Book> books = new HashMap<String, Book>();
	private File dir;
	
	public BookHandler(File dir){
		this.dir = dir;
	}
	
	public void loadBooks() throws IOException{
		Integer count = 0;
		for(File file: dir.listFiles()){
			if(file.isFile()){
				if(file.getCanonicalPath().endsWith(".yml")){
					YamlConfiguration book = YamlConfiguration.loadConfiguration(file);
					String bookn = file.getName();
					books.put(bookn.substring(0, bookn.lastIndexOf('.')), new Book(book.getString("Title"), book.getString("Author"), book.getStringList("Pages")));
					count++;
				}
			}
		}
	}
	
	public void saveBooks() throws IOException{
		for(File file: dir.listFiles()){
			if(file.isFile()){
				if(file.getCanonicalPath().endsWith(".yml")){
					file.delete();
				}
			}
		}
		Integer count = 0;
		for(Entry<String, Book> entrySet : books.entrySet()){
			try{
				entrySet.getValue().save(entrySet.getKey());
			}catch(IOException e){
				e.printStackTrace();
			}
			count++;
		}
	}
	
	public Boolean isBook(String codename){
		if(books.containsKey(codename)){
			return true;
		}else{
			return false;
		}
	}
	
	public Set<String> getBookNames(){
		return books.keySet();
	}
	
	public Collection<Book> getBooks(){
		return books.values();
	}
	
	public Book getBook(String codename){
		if(books.containsKey(codename)){
			return books.get(codename);
		}else{
			return null;
		}
	}
	
	public void updateBook(String codename, Book book){
		if(book == null){
			books.remove(codename);
		}else{
			books.put(codename, book);
		}
	}
}
