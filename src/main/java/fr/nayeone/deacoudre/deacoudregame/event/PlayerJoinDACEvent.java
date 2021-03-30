package fr.nayeone.deacoudre.deacoudregame.event;

import fr.nayeone.deacoudre.deacoudregame.DeACoudreGame;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinDACEvent extends Event implements Cancellable {

	private final static HandlerList handlerList = new HandlerList();
	private boolean isCancelled;

	private final Player player;
	private final DeACoudreGame deACoudreGame;

	public PlayerJoinDACEvent(Player player, DeACoudreGame deACoudreGame) {
		this.player = player;
		this.deACoudreGame = deACoudreGame;
	}

	@Override
	public @NotNull HandlerList getHandlers() {
		return handlerList;
	}

	public static HandlerList getHandlerList() {
		return handlerList;
	}

	public Player getPlayer() {
		return player;
	}

	public DeACoudreGame getDeACoudreGame() {
		return deACoudreGame;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.isCancelled = cancel;
	}
}
