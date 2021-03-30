package fr.nayeone.deacoudre.utils;

import fr.nayeone.deacoudre.DeACoudre;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ConfigurationUtils {

	public static List<String> getAllDACID() {
		FileConfiguration cfg = DeACoudre.getConfigurationManager().getConfigurationFile("DAC.yml");
		ConfigurationSection dacSection = cfg.getConfigurationSection("dac");
		return new ArrayList<String>(dacSection.getKeys(false));
	}

	public static List<String> getAllDACName() {
		FileConfiguration cfg = DeACoudre.getConfigurationManager().getConfigurationFile("DAC.yml");
		List<String> dacNames = new ArrayList<>();
		getAllDACID().forEach(dacID -> {
			ConfigurationSection dacIDSection = cfg.getConfigurationSection("dac." + dacID);
			if (dacIDSection.getString("name") != null) {
				dacNames.add(dacIDSection.getString("name"));
			}
		});
		return dacNames;
	}

	public static String getDACName(String dacID) {
		FileConfiguration cfg = DeACoudre.getConfigurationManager().getConfigurationFile("DAC.yml");
		AtomicReference<String> name = new AtomicReference<>("");
		getAllDACID().forEach(s -> {
			ConfigurationSection dacIDSection = cfg.getConfigurationSection("dac." + dacID);
			if (dacID.equalsIgnoreCase(s)) name.set(dacIDSection.getString("name"));
		});
		return name.get();
	}

	public static boolean isValid(String dacName) {
		for (String s: getAllDACName()) {
			if (s.equalsIgnoreCase(dacName)) {
				return true;
			}
		}
		return false;
	}

	@Nullable
	public static String getDACID(String dacName) {
		FileConfiguration cfg = DeACoudre.getConfigurationManager().getConfigurationFile("DAC.yml");
		ConfigurationSection dacSection = cfg.getConfigurationSection("dac");
		for (String dacID : dacSection.getKeys(false)) {
			ConfigurationSection dacIDSection = dacSection.getConfigurationSection(dacID);
			if (dacIDSection.getString("name").equalsIgnoreCase(dacName)) {
				return dacID;
			}
		}
		return null;
	}

	public static ConfigurationSection getDACConfigurationSection(String dacID) {
		FileConfiguration cfg = DeACoudre.getConfigurationManager().getConfigurationFile("DAC.yml");
		return cfg.getConfigurationSection("dac." + dacID);
	}

	public static boolean isDACSign(Sign sign) {
		FileConfiguration cfg = DeACoudre.getConfigurationManager().getConfigurationFile("DAC.yml");
		ConfigurationSection signsSection = cfg.getConfigurationSection("signs");
		assert signsSection != null;
		for (String signID : signsSection.getKeys(false)) {
			ConfigurationSection signSection = signsSection.getConfigurationSection(signID);
			Location blockLocation = getLocationFromCS(signSection);
			Block block = blockLocation.getBlock();
			if (block.getState() instanceof Sign && block.getLocation().equals(sign.getLocation())) {
				return true;
			}
		}
		return false;
	}

	private static Location getLocationFromCS(ConfigurationSection cs) {
		double x = cs.getDouble("x");
		double y = cs.getDouble("y");
		double z = cs.getDouble("z");
		World world = Bukkit.getWorld(Objects.requireNonNull(cs.getString("world")));
		if (cs.contains("yaw") && cs.contains("pitch")) {
			float yaw = (float) cs.getDouble("yaw");
			float pitch = (float) cs.getDouble("pitch");
			return new Location(world, x, y, z, yaw, pitch);
		} else {
			return new Location(world, x, y, z);
		}
	}

}
