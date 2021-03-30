package fr.nayeone.deacoudre.deacoudregame.event;

import fr.nayeone.deacoudre.deacoudregame.DeACoudreGame;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerWinEvent extends Event {

    private final static HandlerList handlerList = new HandlerList();

    private final DeACoudreGame deACoudreGame;
    private final Player player;

    public PlayerWinEvent(DeACoudreGame deACoudreGame, Player player) {
        this.deACoudreGame = deACoudreGame;
        this.player = player;
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
}
