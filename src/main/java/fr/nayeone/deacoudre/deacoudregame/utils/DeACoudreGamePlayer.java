package fr.nayeone.deacoudre.deacoudregame.utils;

import fr.nayeone.deacoudre.deacoudregame.DeACoudreGame;
import fr.nayeone.deacoudre.deacoudregame.event.PlayerJoinDACEvent;
import fr.nayeone.deacoudre.deacoudregame.event.PlayerQuitDACEvent;
import fr.nayeone.deacoudre.deacoudregame.state.GamePlayerState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DeACoudreGamePlayer {

	private final Player player;
	private int lifePoint = 1;
	private int perfectJumpCount = 0;
	private int missedJumpCount = 0;
	private boolean isAlive = true;
	private final DeACoudreGamePlayerStats deACoudreGamePlayerStats;
	private GamePlayerState gamePlayerState = GamePlayerState.WAITING_START;
	private DeACoudreGame deACoudreGame = null;

	public DeACoudreGamePlayer(Player player) {
		this.player = player;
		this.deACoudreGamePlayerStats = new DeACoudreGamePlayerStats(player);
		this.deACoudreGamePlayerStats.load();
	}

	public Player getPlayer() {
		return player;
	}

	public GamePlayerState getGamePlayerState() {
		return gamePlayerState;
	}

	public int getLifePoint() {
		return lifePoint;
	}

	public int getPerfectJumpCount() {
		return perfectJumpCount;
	}

	public int getMissedJumpCount() {
		return missedJumpCount;
	}

	public DeACoudreGame getDeACoudreGame() {
		return deACoudreGame;
	}

	public DeACoudreGamePlayerStats getDeACoudreGamePlayerStats() {
		return deACoudreGamePlayerStats;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setGamePlayerState(GamePlayerState gamePlayerState) {
		this.gamePlayerState = gamePlayerState;
	}

	public void setLifePoint(int lifePoint) {
		this.lifePoint = lifePoint;
	}

	public void setPerfectJumpCount(int perfectJumpCount) {
		this.perfectJumpCount = perfectJumpCount;
	}

	public void setMissedJumpCount(int missedJumpCount) {
		this.missedJumpCount = missedJumpCount;
	}

	public void setDeACoudreGame(DeACoudreGame deACoudreGame) {
		this.deACoudreGame = deACoudreGame;
	}

	public void setAlive(boolean alive) {
		isAlive = alive;
	}
}
