package fr.nayeone.deacoudre.utils;


import fr.nayeone.deacoudre.DeACoudre;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DeACoudreExpansion extends PlaceholderExpansion {

	private final String VERSION = getClass().getPackage().getImplementationVersion();
	private DeACoudre plugin;

	public DeACoudreExpansion(DeACoudre plugin) {
		this.plugin = plugin;
	}

	@Override
	public @NotNull String getIdentifier() {
		return "dac";
	}

	@Override
	public @NotNull String getAuthor() {
		return "NayeOne";
	}

	@Override
	public @NotNull String getVersion() {
		return VERSION;
	}

	@Override
	public boolean persist(){
		return true;
	}

	@Override
	public boolean canRegister() {
		return true;
	}

	@Override
	public String onRequest(OfflinePlayer offlinePlayer, String identifier) {
		if (identifier.equals("test")) { return "success"; }
		if (offlinePlayer == null || !offlinePlayer.isOnline()) { return "player is not online"; }

		Player player = offlinePlayer.getPlayer();
		FileConfiguration cfg = DeACoudre.getPlayerdataManager().getDataFileCFG(player);
		switch (identifier) {
			case "total_game":
				return String.valueOf(cfg.getInt("statistics.total-game"));
			case "game_win":
				return String.valueOf(cfg.getInt("statistics.game-win"));
			case "game_lose":
				return String.valueOf(cfg.getInt("statistics.game-lose"));
			case "win-point":
				return String.valueOf(cfg.getInt("statistics.win-point"));
			case "perfect_jump":
				return String.valueOf(cfg.getInt("statistics.perfect-jump"));
			case "missed_jump":
				return String.valueOf(cfg.getInt("statistics.missed-jump"));
		}
		return null;
	}

}
