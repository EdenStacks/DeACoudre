package fr.nayeone.deacoudre.deacoudregame.utils;

import fr.nayeone.deacoudre.DeACoudre;
import fr.nayeone.deacoudre.manager.PlayerdataManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class DeACoudreGamePlayerStats {

	private final Player player;

	private int totalGame;
	private int gameWin;
	private int gameLose;
	private int winPoint;
	private int perfectJump;
	private int missedJump;
	private int maxPerfectJumpInRow;
	private int maxLivesInGame;

	private boolean isLoaded;

	public DeACoudreGamePlayerStats(Player player) {
		this.player = player;
		this.isLoaded = false;
	}

	public void load() {
		long delay = System.currentTimeMillis();
		PlayerdataManager playerdataManager = DeACoudre.getPlayerdataManager();
		FileConfiguration cfg = playerdataManager.getDataFileCFG(player);
		ConfigurationSection statisticsSection = cfg.getConfigurationSection("statistics");
		assert statisticsSection != null;
		this.totalGame = statisticsSection.getInt("total-game");
		this.gameWin = statisticsSection.getInt("game-win");
		this.gameLose = statisticsSection.getInt("game-lose");
		this.winPoint = statisticsSection.getInt("win-point");
		this.perfectJump = statisticsSection.getInt("perfect-jump");
		this.missedJump = statisticsSection.getInt("missed-jump");
		this.maxPerfectJumpInRow = statisticsSection.getInt("max-perfect-jump-in-row");
		this.maxLivesInGame = statisticsSection.getInt("max-lives-in-game");
		this.isLoaded = true;
		delay = System.currentTimeMillis() - delay;
		DeACoudre.getPluginLogger().log(Level.INFO, "Loaded statistics for " + player.getName() + " in " + delay + " ms");
	}

	public void save() {
		if (!this.isLoaded) {
			DeACoudre.getPluginLogger().log(Level.WARNING, "Can't save statistics before load for " + player.getName());
		}
		long delay = System.currentTimeMillis();
		PlayerdataManager playerdataManager = DeACoudre.getPlayerdataManager();
		FileConfiguration cfg = playerdataManager.getDataFileCFG(player);
		ConfigurationSection statisticsSection = cfg.getConfigurationSection("statistics");
		assert statisticsSection != null;
		statisticsSection.set("total-game", this.totalGame);
		statisticsSection.set("game-win", this.gameWin);
		statisticsSection.set("game-lose", this.gameLose);
		statisticsSection.set("win-point", this.winPoint);
		statisticsSection.set("perfect-jump", this.perfectJump);
		statisticsSection.set("missed-jump", this.missedJump);
		statisticsSection.set("max-perfect-jump-in-row", this.maxPerfectJumpInRow);
		statisticsSection.set("max-lives-in-game", this.maxLivesInGame);
		playerdataManager.savePlayerdataFile(player, cfg);
		delay = System.currentTimeMillis() - delay;
		DeACoudre.getPluginLogger().log(Level.INFO, "Saved statistics for " + player.getName() + " in " + delay + " ms");
	}

	public Player getPlayer() {
		return player;
	}

	public int getGameLose() {
		return gameLose;
	}

	public int getGameWin() {
		return gameWin;
	}

	public int getMaxLivesInGame() {
		return maxLivesInGame;
	}

	public int getMaxPerfectJumpInRow() {
		return maxPerfectJumpInRow;
	}

	public int getMissedJump() {
		return missedJump;
	}

	public int getTotalGame() {
		return totalGame;
	}

	public int getPerfectJump() {
		return perfectJump;
	}

	public int getWinPoint() {
		return winPoint;
	}

	public void setGameLose(int gameLose) {
		this.gameLose = gameLose;
	}

	public void setGameWin(int gameWin) {
		this.gameWin = gameWin;
	}

	public void setLoaded(boolean loaded) {
		isLoaded = loaded;
	}

	public void setMaxLivesInGame(int maxLivesInGame) {
		this.maxLivesInGame = maxLivesInGame;
	}

	public void setMaxPerfectJumpInRow(int maxPerfectJumpInRow) {
		this.maxPerfectJumpInRow = maxPerfectJumpInRow;
	}

	public void setMissedJump(int missedJump) {
		this.missedJump = missedJump;
	}

	public void setPerfectJump(int perfectJump) {
		this.perfectJump = perfectJump;
	}

	public void setTotalGame(int totalGame) {
		this.totalGame = totalGame;
	}

	public void setWinPoint(int winPoint) {
		this.winPoint = winPoint;
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	public void addGameLose(int n) {
		this.gameLose += n;
	}

	public void addGameWin(int n) {
		this.gameWin += n;
	}

	public void addMissedJump(int n) {
		this.missedJump += n;
	}

	public void addTotalGame(int n) {
		this.totalGame += n;
	}

	public void addPerfectJump(int n) {
		this.perfectJump += n;
	}

	public void addWinPoint(int n) {
		this.winPoint += n;
	}

}
