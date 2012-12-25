package broseidon4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public final class Settlements extends JavaPlugin {
	private static File[] settlementList;
	
	@Override
	public void onEnable() {
		getLogger().info("Settlements has successfully loaded!");
		File directory = new File("plugins/Settlements/");  //gets directory of all of the settlement info
		settlementList = directory.listFiles();
		
		
		
	}
	
	public void onDisable() {
		getLogger().info("Settlements has been disabled!");
		
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		switch (cmd.getName().toLowerCase()) {
		case "addchunk":
			if (!(sender instanceof Player)) {
				sender.sendMessage("Silly rabbit, this command is for players!");
			} else {
				Player player = (Player) sender;
				File chunkData = new File("plugins/Settlements/" + player.getName()+ ".txt");
				try {
					chunkData.createNewFile();
					File directory = new File("plugins/Settlements/");  //re-updates settlement list info in-case a new settlement was created.
					settlementList = directory.listFiles();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				try {
					PrintWriter fileOutput = new PrintWriter(new BufferedWriter(new FileWriter(chunkData.getPath())));
					Chunk newChunk = player.getLocation().getChunk(); //gets chunk of the player
					fileOutput.write((newChunk.getX()) + "," + newChunk.getZ());
					fileOutput.println();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
				return true;
			}

		
		}
		
		return false;
	}
	

}
