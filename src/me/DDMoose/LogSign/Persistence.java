package me.DDMoose.LogSign;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Persistence {

	public static boolean containsKey(Player p, File file) {
		Properties pro = new Properties();
		String player = p.getName();
		
		try{
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			if(pro.containsKey(player)){
				return true;
			}
		}catch(IOException e){
			e.printStackTrace();
			return false;		
		}
		return false;
	}

	public static Location getLocation(Player p, File file) {	
		Properties pro = new Properties();
		String player = p.getName();
		try{
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			String string = pro.getProperty(player);
			String[] split = string.split("%");
			Double x = Double.parseDouble(split[1]);
			Double y = Double.parseDouble(split[2]);
			Double z = Double.parseDouble(split[3]);
			Location loc = new Location(p.getWorld(), x, y, z);
			return loc;
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static void write(Player p, String location,File file) {
		Properties pro = new Properties();
		String player = p.getName();
		try{
		
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			pro.setProperty(player, location);
			pro.store(new FileOutputStream(file),null);
		}catch (IOException e) {
				e.printStackTrace();
		}
	}

}
