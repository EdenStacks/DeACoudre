package fr.nayeone.deacoudre.deacoudregame.utils;

import fr.nayeone.deacoudre.deacoudregame.DeACoudreGame;
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
			this.sign.setLine(0, this.deACoudreGame.getDisplayName());
		} else {
			this.sign.setLine(0, this.deACoudreGame.getName());
		}
		if (!this.deACoudreGame.isBuild()) {
			this.sign.setLine(1, "");
			this.sign.setLine(2, ChatColor.RED + "Fermée");
			this.sign.setLine(3, "");
			this.sign.update();
			return;
		}
		switch (this.deACoudreGame.getGameState()) {
			case READY:
			case WAITING:
				this.sign.setLine(1, this.deACoudreGame.getDeACoudreGamePlayers().size()
						+ " / " + this.deACoudreGame.getMaxPlayers());
				int missingPlayerAmount = this.deACoudreGame.getMinPlayers() - this.getDeACoudreGame().getDeACoudreGamePlayers().size();
				if (missingPlayerAmount <= 0) {
					this.sign.setLine(2, ChatColor.GREEN + "Prêt" + ChatColor.WHITE
							+ " (" + ChatColor.YELLOW + this.deACoudreGame.getTimer() + ChatColor.WHITE + ")");
				} else {
					this.sign.setLine(2, missingPlayerAmount + " manquants");
				}
				this.sign.setLine(3, ChatColor.GREEN + "En attente ...");
				break;
			case IN_PROGRESS:
				this.sign.setLine(1, this.deACoudreGame.getAliveDeACoudreGamePlayer().size() + " en jeu");
				this.sign.setLine(2, "DàC : " + this.deACoudreGame.getTotalPerfectJump());
				this.sign.setLine(3, ChatColor.GOLD + "En cours ...");
				break;
			case FINISH:
				this.sign.setLine(1, "Bravo à");
				if (this.deACoudreGame.getWinner() != null) {
					this.sign.setLine(2, this.deACoudreGame.getWinner().getPlayer().getName());
				} else {
					this.sign.setLine(2, "personne :(");
				}
				this.sign.setLine(3, ChatColor.LIGHT_PURPLE + "Terminé !");
				break;
		}
		this.sign.update();
	}
}
