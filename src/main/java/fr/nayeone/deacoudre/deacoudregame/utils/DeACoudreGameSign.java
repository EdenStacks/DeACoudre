package fr.nayeone.deacoudre.deacoudregame.utils;

import fr.nayeone.deacoudre.deacoudregame.DeACoudreGame;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;

public class DeACoudreGameSign {

	private final String signID;

	private Sign sign;
	private DeACoudreGame deACoudreGame;

	public DeACoudreGameSign(String signID, Sign sign, DeACoudreGame deACoudreGame) {
		this.signID = signID;
		this.sign = sign;
		this.deACoudreGame = deACoudreGame;
	}

	public DeACoudreGame getDeACoudreGame() {
		return deACoudreGame;
	}

	public Sign getSign() {
		return sign;
	}

	public void setSign(Sign sign) {
		this.sign = sign;
	}

	public void setDeACoudreGame(DeACoudreGame deACoudreGame) {
		this.deACoudreGame = deACoudreGame;
	}

	public String getSignID() {
		return signID;
	}

	public void update() {
		if (this.deACoudreGame.getDisplayName() != null && !this.deACoudreGame.getDisplayName().equalsIgnoreCase("none")) {
			this.sign.line(0, Component.text(this.deACoudreGame.getDisplayName()));
		} else {
			this.sign.line(0, Component.text(this.deACoudreGame.getName()));
		}
		if (!this.deACoudreGame.isBuild()) {
			this.sign.line(1, Component.text(""));
			this.sign.line(2, Component.text(ChatColor.RED + "Fermée"));
			this.sign.line(3, Component.text(""));
			this.sign.update();
			return;
		}
		switch (this.deACoudreGame.getGameState()) {
			case READY:
			case WAITING:
				this.sign.line(1, Component.text(this.deACoudreGame.getDeACoudreGamePlayers().size()
						+ " / " + this.deACoudreGame.getMaxPlayers()));
				int missingPlayerAmount = this.deACoudreGame.getMinPlayers() - this.getDeACoudreGame().getDeACoudreGamePlayers().size();
				if (missingPlayerAmount <= 0) {
					this.sign.line(2, Component.text(ChatColor.GREEN + "Prêt" + ChatColor.WHITE
							+ " (" + ChatColor.YELLOW + this.deACoudreGame.getTimer() + ChatColor.WHITE + ")"));
				} else {
					this.sign.line(2, Component.text(missingPlayerAmount + " manquants"));
				}
				this.sign.line(3, Component.text(ChatColor.GREEN + "En attente ..."));
				break;
			case IN_PROGRESS:
				this.sign.line(1, Component.text(this.deACoudreGame.getAliveDeACoudreGamePlayer().size() + " en jeu"));
				this.sign.line(2, Component.text("DàC : " + this.deACoudreGame.getTotalPerfectJump()));
				this.sign.line(3, Component.text(ChatColor.GOLD + "En cours ..."));
				break;
			case FINISH:
				this.sign.line(1, Component.text("Bravo à"));
				if (this.deACoudreGame.getWinner() != null) {
					this.sign.line(2, Component.text(this.deACoudreGame.getWinner().getPlayer().getName()));
				} else {
					this.sign.line(2, Component.text("personne :("));
				}
				this.sign.line(3, Component.text(ChatColor.LIGHT_PURPLE + "Terminé !"));
				break;
		}
		this.sign.update();
	}
}
