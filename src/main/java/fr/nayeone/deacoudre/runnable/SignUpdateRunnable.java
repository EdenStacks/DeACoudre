package fr.nayeone.deacoudre.runnable;

import fr.nayeone.deacoudre.DeACoudre;
import fr.nayeone.deacoudre.deacoudregame.utils.DeACoudreGameSign;
import org.bukkit.scheduler.BukkitRunnable;

public class SignUpdateRunnable extends BukkitRunnable {

	@Override
	public void run() {
		DeACoudre.getAllSigns().forEach(DeACoudreGameSign::update);
	}

}
