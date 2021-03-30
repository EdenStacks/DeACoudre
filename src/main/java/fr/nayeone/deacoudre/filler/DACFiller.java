package fr.nayeone.deacoudre.filler;

import fr.nayeone.deacoudre.utils.CFGFiller;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class DACFiller implements CFGFiller {

	@Override
	public void fill(FileConfiguration fileConfiguration) {
		ConfigurationSection dacSection = fileConfiguration.createSection("dac");
		ConfigurationSection signsSection = fileConfiguration.createSection("signs");
	}

}
