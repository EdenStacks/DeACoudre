package fr.nayeone.deacoudre.manager;

import fr.nayeone.deacoudre.DeACoudre;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class PlayerdataManager {

	private final String folderPath;

	public PlayerdataManager(ConfigurationManager configurationManager){
		folderPath = configurationManager.getPlayerdataFolder().getPath();
	}

	public void createData(Player player){
		File dataFile = getDataFile(player);
		FileConfiguration dataFileCFG;
		if (!dataFile.exists()){
			try {
				if(dataFile.createNewFile()){
					dataFileCFG = YamlConfiguration.loadConfiguration(dataFile);
					fillData(player, dataFileCFG);
					dataFileCFG.save(dataFile);
				}else {
					DeACoudre.getPluginLogger().log(Level.SEVERE, "dataFile for " + player.getName() + " can't be created.");
				}
			} catch (IOException ignored) {
			}
		}
	}

	private void fillData(Player player, FileConfiguration fileConfiguration){
		fileConfiguration.set("last-name", player.getName());

		ConfigurationSection statisticsSection = fileConfiguration.createSection("statistics");
		statisticsSection.set("total-game", 0);
		statisticsSection.set("game-win", 0);
		statisticsSection.set("game-lose", 0);
		statisticsSection.set("win-point", 0);
		statisticsSection.set("perfect-jump", 0);
		statisticsSection.set("missed-jump", 0);
		statisticsSection.set("max-perfect-jump-in-row", 0);
		statisticsSection.set("max-lives-in-game", 0);
	}

	public File getDataFile(Player player){
		return new File(folderPath, player.getUniqueId().toString() + ".yml");
	}

	public FileConfiguration getDataFileCFG(Player player){
		File dataFile = getDataFile(player);
		if (!dataFile.exists()){
			createData(player);
		}
		return YamlConfiguration.loadConfiguration(dataFile);
	}

	public void savePlayerdataFile(Player player, FileConfiguration fileConfiguration){
		try {
			File dataFile = this.getDataFile(player);
			fileConfiguration.save(dataFile);
		} catch (IOException ignored) { }
	}

	public String getFolderPath() {
		return folderPath;
	}
}
