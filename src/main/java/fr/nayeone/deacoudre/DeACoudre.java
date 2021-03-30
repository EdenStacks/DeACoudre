package fr.nayeone.deacoudre;

import fr.nayeone.deacoudre.deacoudregame.utils.DeACoudreGamePlayer;
import fr.nayeone.deacoudre.manager.CommandManager;
import fr.nayeone.deacoudre.manager.ConfigurationManager;
import fr.nayeone.deacoudre.manager.ListenerManager;
import fr.nayeone.deacoudre.manager.PlayerdataManager;
import fr.nayeone.deacoudre.runnable.SignUpdateRunnable;
import fr.nayeone.deacoudre.utils.ConfigurationUtils;
import fr.nayeone.deacoudre.deacoudregame.DeACoudreGame;
import fr.nayeone.deacoudre.deacoudregame.utils.DeACoudreGameSign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DeACoudre extends JavaPlugin{

	// MANAGER
	private static ConfigurationManager configurationManager;
	private static ListenerManager listenerManager;
	private static PlayerdataManager playerdataManager;

	public static List<DeACoudreGame> allDACGames = new ArrayList<>();

	@Override
	public void onEnable() {
		long delay = System.currentTimeMillis();
		configurationManager = new ConfigurationManager(this);
		configurationManager.setupFiles();
		playerdataManager = new PlayerdataManager(configurationManager);
		listenerManager = new ListenerManager(this);
		new CommandManager(this);
		getLogger().log(Level.INFO, "Building all DAC ...");
		initAllDAC();
		getLogger().log(Level.INFO, "End of DAC build.");
		BukkitRunnable signUpdateRunnable = new SignUpdateRunnable();
		signUpdateRunnable.runTaskTimer(this, 1, 20);
		getLogger().log(Level.INFO, "Plugin enabled took " + (System.currentTimeMillis() - delay) + " ms");
	}

	@Override
	public void onDisable() {
		configurationManager.saveFiles();
	}

	private void initAllDAC() {
		FileConfiguration cfg = configurationManager.getConfigurationFile("DAC.yml");
		ConfigurationSection dacSection = cfg.getConfigurationSection("dac");
		ConfigurationUtils.getAllDACID().forEach(dacID -> {
			DeACoudreGame deACoudreGame = new DeACoudreGame(dacID);
			deACoudreGame.build();
			String dacName = ConfigurationUtils.getDACName(dacID);
			if (deACoudreGame.isBuild()) {
				getLogger().log(Level.INFO, "Construction de §a" + dacName + " §2OK");
			} else {
				getLogger().log(Level.WARNING, "Construction de §c" + dacName + " §4KO");
			}
			allDACGames.add(deACoudreGame);
		});
	}

	public static boolean isInDeACoudre(Player player) {
		for (DeACoudreGame deACoudreGame : allDACGames) {
			for (DeACoudreGamePlayer deACoudreGamePlayer : deACoudreGame.getDeACoudreGamePlayers()) {
				if (deACoudreGamePlayer.getPlayer().equals(player)) {
					return true;
				}
			}
		}
		return false;
	}

	public static List<DeACoudreGameSign> getAllSigns() {
		List<DeACoudreGameSign> signs = new ArrayList<>();
		for (DeACoudreGame deACoudreGame : allDACGames) {
			signs.addAll(deACoudreGame.getDeACoudreGameSigns());
		}
		return signs;
	}

	public static ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	public static PlayerdataManager getPlayerdataManager() {
		return playerdataManager;
	}

	public static ListenerManager getListenerManager() {
		return listenerManager;
	}

	public static Logger getPluginLogger() {
		return getPlugin(DeACoudre.class).getLogger();
	}
}
