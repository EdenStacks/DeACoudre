package fr.nayeone.deacoudre.runnable;

import fr.nayeone.deacoudre.DeACoudre;
import fr.nayeone.deacoudre.deacoudregame.utils.DeACoudreGameSign;
import fr.nayeone.deacoudre.deacoudregame.utils.DeACoudreScoreboard;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdaterRunnable extends BukkitRunnable {

	@Override
	public void run() {
		DeACoudre.getAllSigns().forEach(DeACoudreGameSign::update);
		DeACoudre.getAllScoreBoard().forEach(DeACoudreScoreboard::update);
	}

}
