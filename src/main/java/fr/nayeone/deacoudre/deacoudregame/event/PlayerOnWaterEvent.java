package fr.nayeone.deacoudre.deacoudregame.event;

import fr.nayeone.deacoudre.deacoudregame.DeACoudreGame;
import fr.nayeone.deacoudre.deacoudregame.utils.DeACoudreGamePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerOnWaterEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final DeACoudreGame deACoudreGame;
    private final DeACoudreGamePlayer deACoudreGamePlayer;

    public PlayerOnWaterEvent(DeACoudreGame deACoudreGame, DeACoudreGamePlayer deACoudreGamePlayer) {
        this.deACoudreGame = deACoudreGame;
        this.deACoudreGamePlayer = deACoudreGamePlayer;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public DeACoudreGamePlayer getDeACoudreGamePlayer() {
        return deACoudreGamePlayer;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public DeACoudreGame getDeACoudreGame() {
        return deACoudreGame;
    }
}
