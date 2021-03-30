package fr.nayeone.deacoudre.filler;

import fr.nayeone.deacoudre.utils.CFGFiller;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;

public class ConfigFiller implements CFGFiller {

	@Override
	public void fill(FileConfiguration fileConfiguration) {
		ConfigurationSection delaySection = fileConfiguration.createSection("delay");

		delaySection.set("start-game", 30);
		delaySection.set("jump", 15);

		fileConfiguration.set("command-blacklist", Arrays.asList("espawn", "spawn", "hub"));
	}

}
