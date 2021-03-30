package fr.nayeone.deacoudre.deacoudregame.event;

import fr.nayeone.deacoudre.deacoudregame.DeACoudreGame;
import fr.nayeone.deacoudre.deacoudregame.state.GameState;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GameStateChangeEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private final DeACoudreGame deACoudreGame;
	private final GameState gameStateTo;
	private final GameState gameStateFrom;

	public GameStateChangeEvent(DeACoudreGame deACoudreGame, GameState gameStateTo, GameState gameStateFrom) {
		this.deACoudreGame = deACoudreGame;
		this.gameStateTo = gameStateTo;
		this.gameStateFrom = gameStateFrom;
	}

	@Override
	public @NotNull HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public DeACoudreGame getDeACoudreGame() {
		return deACoudreGame;
	}

	public GameState getGameStateFrom() {
		return gameStateFrom;
	}

	public GameState getGameStateTo() {
		return gameStateTo;
	}
}
