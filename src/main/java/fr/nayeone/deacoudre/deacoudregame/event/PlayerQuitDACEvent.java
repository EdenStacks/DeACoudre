package fr.nayeone.deacoudre.deacoudregame.event;

import fr.nayeone.deacoudre.deacoudregame.DeACoudreGame;
import fr.nayeone.deacoudre.deacoudregame.utils.QuitCause;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerQuitDACEvent extends Event {

	private final static HandlerList handlerList = new HandlerList();

	private final Player player;
	private final DeACoudreGame deACoudreGame;
	private final QuitCause quitCause;

	public PlayerQuitDACEvent(Player player, DeACoudreGame deACoudreGame, QuitCause quitCause) {
		this.player = player;
		this.deACoudreGame = deACoudreGame;
		this.quitCause = quitCause;
	}

	@Override
	public @NotNull HandlerList getHandlers() {
		return handlerList;
	}

	public static HandlerList getHandlerList() {
		return handlerList;
	}

	public DeACoudreGame getDeACoudreGame() {
		return deACoudreGame;
	}

	public Player getPlayer() {
		return player;
	}

	public QuitCause getQuitCause() {
		return quitCause;
	}
}
