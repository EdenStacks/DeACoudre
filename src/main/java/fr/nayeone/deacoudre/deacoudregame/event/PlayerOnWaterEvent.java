package fr.nayeone.deacoudre.deacoudregame.event;

import fr.nayeone.deacoudre.deacoudregame.DeACoudreGame;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerOnWaterEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final DeACoudreGame deACoudreGame;

    public PlayerOnWaterEvent(DeACoudreGame deACoudreGame) {
        this.deACoudreGame = deACoudreGame;
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
}
