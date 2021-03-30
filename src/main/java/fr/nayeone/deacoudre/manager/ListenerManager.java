package fr.nayeone.deacoudre.manager;

import fr.nayeone.deacoudre.deacoudregame.utils.DeACoudreGamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {

	private final Plugin plugin;
	private final PluginManager pluginManager;

	public ListenerManager(Plugin plugin) {
		this.plugin = plugin;
		pluginManager = Bukkit.getPluginManager();
	}

	public void registerEvent(Listener listener) {
		this.pluginManager.registerEvents(listener, this.plugin);
	}

}
