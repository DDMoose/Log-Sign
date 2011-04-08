package me.DDMoose.LogSign;

import java.io.File;
import java.io.IOException;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class LogSign extends JavaPlugin {
	private final PlayListener playListener = new PlayListener(this);
	
	static String mainDir = "plugins/LogSign/";
	static File Signs = new File(mainDir + "LogSign.Signs");

	public void initFile(File temp){
		new File(mainDir).mkdir();	
		if(!temp.exists())
			try{
				temp.createNewFile();
			}catch(IOException e){
				System.out.println("Unable to create directory. Is write access disabled?");
				e.printStackTrace();
			}
	}
	
	@Override
	public void onEnable() {
		
		initFile(Signs);
			
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " by DDMoose is enabled!" );
        PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_QUIT, this.playListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_LOGIN, this.playListener, Event.Priority.Normal,this);
		
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		System.out.println((pdfFile.getName() + " version ")
				+pdfFile.getVersion() + " by DDMoose is disabled!");	
	}
	
}
