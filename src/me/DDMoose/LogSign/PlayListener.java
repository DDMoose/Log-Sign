package me.DDMoose.LogSign;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayListener extends PlayerListener {
	public static LogSign plugin;
	
	public static List<String> engravings = new LinkedList<String>();

	public PlayListener(LogSign instance) {
		plugin = instance;
	}
	public void onPlayerLogin(PlayerLoginEvent event){
        if(event.getPlayer() instanceof Player){
		    Player player = event.getPlayer();
		
		    boolean hasSign = Persistence.containsKey(player, LogSign.Signs);
		    if(!hasSign) {
			    System.out.println("Player has no sign");
			    Persistence.write(player,"%0%0%0%", LogSign.Signs);
		    }else{
			    Location loc = Persistence.getLocation(player, LogSign.Signs);
			    Block block = loc.getBlock();
			
			    if(block.getType()  == Material.SIGN_POST){
				    block.setType(Material.AIR);
			}
		}
       }
	}
	
	public void load() throws IOException{
		FileInputStream fstream = new FileInputStream("plugins/LogSign/messages.txt");
		DataInputStream in = new DataInputStream(fstream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String strLine;
	    
	    while ((strLine = br.readLine()) != null)   {
	        engravings.add(strLine);        
	      }
	}
		
	public void onPlayerQuit(PlayerQuitEvent event) {
		if(event.getPlayer() instanceof Player){	
			Player player = (Player) event.getPlayer();
		    	Location loc = Persistence.getLocation(player, LogSign.Signs);
				Block block = loc.getBlock();
			    loc = player.getLocation();
				Persistence.write(player,"%"+loc.getBlockX()+"%"+loc.getBlockY()+"%"+loc.getBlockZ()+"%", LogSign.Signs);
				loc = player.getLocation();		
				block = player.getWorld().getBlockAt(loc);
				
				if(block.getType() == Material.LAVA ||
				  (block.getType()== Material.WATER)||
				  (block.getType()== Material.AIR)){
					
					block.setType(Material.SIGN_POST);	
					Sign s = (Sign) block.getState();
					
					try {
						load();
					} catch (IOException e) {
						e.printStackTrace();
					}

					
					Random rand = new Random();
		            s.setLine(0, engravings.get(rand.nextInt(engravings.size())));
					//s.setLine(0,anArray[rand.nextInt(11)]);
		            s.setLine(1, player.getName());
		            
					Date now = new Date();
		            s.setLine(2, DateFormat.getDateTimeInstance(
		                    DateFormat.SHORT, DateFormat.SHORT).format(now));
		            s.update();	
				}

			}
			
		}
	}


