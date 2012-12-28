package broseidon4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public final class Settlements extends JavaPlugin {
	private static ArrayList<File> settlementList;
	
	@Override
	public void onEnable() {
		File folder = new File("plugins/Settlements/");
		File dir = getDataFolder();
		if (!dir.exists()) {
			System.out.println("Settlements is making its directory!");
			dir.mkdirs();
		}
		

				

		System.out.println("Settlements has been enabled!");
	}
	
	public static void updateList()
	{
		File folder = new File("plugins/Settlements/");
		settlementList = new ArrayList<File>(Arrays.asList(folder.listFiles()));
	}
	
	public static int searchFileList(String search) {
		for (int i = 0; i < settlementList.size(); i++ )
		{
			if (settlementList.get(i).getName().equalsIgnoreCase(search)) {
				return i;
			}
		}
		return -1; //if the file is not listed
	}	
		
		
	public void onDisable() {
		System.out.println("Settlements has been disabled!");
		
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("settlements")) {
			if (args[0].equalsIgnoreCase("create")) {
				File dir = new File("plugins/Settlements/"+args[1]);
				dir.mkdirs();
				File chunks = new File("plugins/Settlements/"+args[1]+"/chunks.yml");
				File players = new File("plugins/Settlements/"+args[1]+"/players.yml");
				if (!chunks.exists()) {    //if the settlement doesnt exist (name available)
					try {
						chunks.createNewFile();
						updateList();
						System.out.println("A chunks file for the settlement, "+args[1]+" has been created!");
						sender.sendMessage(ChatColor.YELLOW + "The settlement " + args[1] + " has been created!");
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if (!players.exists()) {
					try {
						players.createNewFile();
						updateList();
						System.out.println("A players file for the settlement, "+args[1]+" has been created!");
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				}
				else {
					sender.sendMessage(ChatColor.YELLOW + "A settlement with that name already exists!");
				}
				
			} else if(args[0].equalsIgnoreCase("addchunk")) {
				int i = 0;
				if(args.length > 2) {
					sender.sendMessage(ChatColor.RED  + "Too many arguments! \nUsage: /settlements addchunk [Settlement Name]");
				} else if (args.length == 1) {
					sender.sendMessage(ChatColor.RED + "Specify a settlement to add the chunk to!\nUsage: /settlements addchunk [Settlement Name]");
				} else if (!(new File("plugins/Settlements/"+args[1]+"/chunks.yml").exists())) { //if the file for the settlement specified doesn't exist
					sender.sendMessage(ChatColor.RED + "That settlement doesn't exist!");
				} else { 
					Chunk newChunk = ((Player) sender).getLocation().getChunk();
					
					try {
						 //THIS GETS THE SETTLEMENT FOLDER, NOT CHUNKS.YML
						System.out.println("plugins/Settlements/"+args[1] + "/chunks.yml"); //debug shit 
						FileWriter w = new FileWriter("plugins/Settlements/"+args[1] + "/chunks.yml", true);
						BufferedWriter writer = new BufferedWriter(w);
						writer.append(newChunk.getX() + "," + newChunk.getZ() +"\n");
						writer.close();
						
						System.out.println("The chunk " + newChunk.getX() + "," + newChunk.getZ() + " (x,z) has been added to the settlement, " + args[1]);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
				}
			} else if(args[0].equalsIgnoreCase("addplayer")) {
				
				if (args.length>3) {
					sender.sendMessage(ChatColor.RED + "Too many arguments!\nUsage: /settlements addplayer [player] [Settlement Name]");
				} else if (args.length<3) {
					sender.sendMessage(ChatColor.RED + "Not enough arguments!\nUsage: /settlements addplayer [player] [Settlement Name]");
				} else if (searchFileList(args[2])==-1) {
					sender.sendMessage(ChatColor.RED + "That settlement doesn't exist!\nUsage: /settlements addplayer [player] [Settlement Name]");
				}
				
				else {
					 //THIS GETS THE SETTLEMENT FOLDER, NOT CHUNKS.YML
					try {
						
						System.out.println("plugins/Settlements/"+args[2] + "/players.yml"); //debug
						FileWriter w = new FileWriter("plugins/Settlements/"+args[2] + "/players.yml", true);
						BufferedWriter writer = new BufferedWriter(w);
						writer.append(args[1]+"\n");
						writer.close();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}
		
		
		
		
		return true;
	}
	

}
