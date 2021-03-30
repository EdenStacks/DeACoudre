package fr.nayeone.deacoudre.deacoudregame.event;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DACSignCreateEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private final Player player;
	private Sign sign;
	private String signID;
	private final String dacName;
	private boolean cancel = false;

	public DACSignCreateEvent(Player player, String signID, Sign sign, String dacName) {
		this.player = player;
		this.sign = sign;
		this.dacName = dacName;
		this.signID = signID;
	}

	@Override
	public @NotNull HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public Player getPlayer() {
		return player;
	}

	public Sign getSign() {
		return sign;
	}

	public String getDacName() {
		return dacName;
	}

	public String getSignID() {
		return signID;
	}

	public void setSign(Sign sign) {
		this.sign = sign;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
}
