package fr.nayeone.deacoudre.deacoudregame.utils;

import fr.nayeone.deacoudre.DeACoudre;
import fr.nayeone.deacoudre.deacoudregame.DeACoudreGame;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.scheduler.BukkitRunnable;

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
					this.sign.setLine(2, "§aEn attente");
				}
				new BukkitRunnable() {
					int timer = 0;

					final String[] display = { "§2■§a■■■■■■■■■","§a■§2■§a■■■■■■■■","§a■■§2■§a■■■■■■■","§a■■■§2■§a■■■■■■","§a■■■■§2■§a■■■■■",
							"§a■■■■■§2■§a■■■■","§a■■■■■■§2■§a■■■","§a■■■■■■■§2■§a■■","§a■■■■■■■■§2■§a■","§a■■■■■■■■■§2■"};

					@Override
					public void run() {
						if (this.timer == 10) {
							cancel();
							return;
						}
						sign.setLine(3, this.display[this.timer]);
						sign.update();
						this.timer++;
					}
				}.runTaskTimer(DeACoudre.getPlugin(DeACoudre.class), 0L, 2L);

				break;
			case IN_PROGRESS:
				this.sign.setLine(1, this.deACoudreGame.getAliveDeACoudreGamePlayer().size() + " en jeu");
				this.sign.setLine(2, "§eDé à Coudre : " + this.deACoudreGame.getTotalPerfectJump());
				new BukkitRunnable() {
					int timer = 0;

					final String[] display = { "§6■§e■■■■■■■■■","§e■§6■§e■■■■■■■■","§e■■§6■§e■■■■■■■","§e■■■§6■§e■■■■■■","§e■■■■§6■§e■■■■■",
							"§e■■■■■§6■§e■■■■","§e■■■■■■§6■§e■■■","§e■■■■■■■§6■§e■■","§e■■■■■■■■§6■§e■","§e■■■■■■■■■§6■"};

					@Override
					public void run() {
						if (this.timer == 10) {
							cancel();
							return;
						}
						sign.setLine(3, this.display[this.timer]);
						sign.update();
						this.timer++;
					}
				}.runTaskTimer(DeACoudre.getPlugin(DeACoudre.class), 0L, 2L);
				break;
			case FINISH:
				this.sign.setLine(1, this.deACoudreGame.getDeACoudreGamePlayers().size()
						+ " / " + this.deACoudreGame.getMaxPlayers());
				this.sign.setLine(2, "§cNettoyage");
				new BukkitRunnable() {
					int timer = 0;

					final String[] display = { "§4■§c■■■■■■■■■","§c■§4■§c■■■■■■■■","§c■■§4■§c■■■■■■■","§c■■■§4■§c■■■■■■","§c■■■■§4■§c■■■■■",
							"§c■■■■■§4■§c■■■■","§c■■■■■■§4■§c■■■","§c■■■■■■■§4■§c■■","§c■■■■■■■■§4■§c■","§c■■■■■■■■■§4■"};

					@Override
					public void run() {
						if (this.timer == 10) {
							cancel();
							return;
						}
						sign.setLine(3, this.display[this.timer]);
						sign.update();
						this.timer++;
					}
				}.runTaskTimer(DeACoudre.getPlugin(DeACoudre.class), 0L, 2L);
				break;
		}
		this.sign.update();
	}
}
