package fr.nayeone.deacoudre.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import fr.nayeone.deacoudre.DeACoudre;
import fr.nayeone.deacoudre.lang.MessageFR;
import fr.nayeone.deacoudre.utils.ConfigurationUtils;
import fr.nayeone.deacoudre.deacoudregame.event.DACSignCreateEvent;
import fr.nayeone.deacoudre.deacoudregame.utils.DeACoudreGameSign;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("dac")
public class DAC extends BaseCommand {

	@Subcommand("create")
	@CommandPermission("deacoudre.create")
	public static void onCreate(Player player, String dacName) {
		for (String s : ConfigurationUtils.getAllDACName()) {
			if (s.equalsIgnoreCase(dacName)) {
				player.sendMessage(MessageFR.dacNameAlreadyExist);
				player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
				return;
			}
		}

		FileConfiguration cfg = DeACoudre.getConfigurationManager().getConfigurationFile("DAC.yml");
		ConfigurationSection dacSection = cfg.getConfigurationSection("dac");
		int id = dacSection.getKeys(false).size();
		while (dacSection.contains(String.valueOf(id))) {
			id++;
		}
		ConfigurationSection dacIDSection = dacSection.createSection(String.valueOf(id));
		dacIDSection.set("name", dacName);
		dacIDSection.set("displayname", "none");
		dacIDSection.set("win-points", 1);
		dacIDSection.createSection("lobby");
		dacIDSection.createSection("pool");
		dacIDSection.createSection("diving");
		dacIDSection.createSection("spectator");
		dacIDSection.createSection("end");
		dacIDSection.set("min-players", 1);
		dacIDSection.set("max-players", 10);
		dacIDSection.createSection("pool-region.pos1");
		dacIDSection.createSection("pool-region.pos2");

		DeACoudre.getConfigurationManager().saveFile("DAC.yml");
		player.sendMessage(MessageFR.dacCreated.replace("{DAC}", dacName));
	}

	@Subcommand("set displayname")
	@CommandPermission("deacoudre.set.displayname")
	@CommandCompletion("@dacNames displayname")
	public static void onSetDisplayname(Player player, String dacName, String displayname) {
		if (!ConfigurationUtils.isValid(dacName)) {
			player.sendMessage(MessageFR.dacNameDoesNotExist.replace("{DAC}", dacName));
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			return;
		}
		String dacID = ConfigurationUtils.getDACID(dacName);
		ConfigurationSection dacIDSection = ConfigurationUtils.getDACConfigurationSection(dacID);
		dacIDSection.set("displayname", displayname);
		DeACoudre.getConfigurationManager().saveFile("DAC.yml");
		player.sendMessage(MessageFR.dacSetDisplayname
				.replace("{displayname}", displayname.replaceAll("&", "§"))
				.replace("{DAC}", dacName)
		);
	}

	@Subcommand("set win-points")
	@CommandPermission("deacoudre.set.winpoints")
	@CommandCompletion("@dacNames @range:0-10")
	public static void onSetWinPoints(Player player, String dacName, int winPoints) {
		if (!ConfigurationUtils.isValid(dacName)) {
			player.sendMessage(MessageFR.dacNameDoesNotExist.replace("{DAC}", dacName));
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			return;
		}
		String dacID = ConfigurationUtils.getDACID(dacName);
		ConfigurationSection dacIDSection = ConfigurationUtils.getDACConfigurationSection(dacID);
		dacIDSection.set("win-points", winPoints);
		DeACoudre.getConfigurationManager().saveFile("DAC.yml");
		player.sendMessage(MessageFR.dacSetWinPoints
				.replace("{win-points}", String.valueOf(winPoints))
				.replace("{DAC}", dacName)
		);
	}

	@Subcommand("set lobby")
	@CommandPermission("deacoudre.set.lobby")
	@CommandCompletion("@dacNames")
	public static void onSetLobby(Player player, String dacName) {
		if (!ConfigurationUtils.isValid(dacName)) {
			player.sendMessage(MessageFR.dacNameDoesNotExist.replace("{DAC}", dacName));
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			return;
		}
		String dacID = ConfigurationUtils.getDACID(dacName);
		ConfigurationSection lobbySection = ConfigurationUtils.getDACConfigurationSection(dacID).getConfigurationSection("lobby");
		Location playerLocation = player.getLocation();
		lobbySection.set("x", playerLocation.getX());
		lobbySection.set("y", playerLocation.getY());
		lobbySection.set("z", playerLocation.getZ());
		lobbySection.set("yaw", playerLocation.getYaw());
		lobbySection.set("pitch", playerLocation.getPitch());
		lobbySection.set("world", playerLocation.getWorld().getName());
		DeACoudre.getConfigurationManager().saveFile("DAC.yml");
		player.sendMessage(MessageFR.dacSetLobby
				.replace("{DAC}", dacName)
		);
	}

	@Subcommand("set pool")
	@CommandPermission("deacoudre.set.pool")
	@CommandCompletion("@dacNames")
	public static void onSetPool(Player player, String dacName) {
		if (!ConfigurationUtils.isValid(dacName)) {
			player.sendMessage(MessageFR.dacNameDoesNotExist.replace("{DAC}", dacName));
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			return;
		}
		String dacID = ConfigurationUtils.getDACID(dacName);
		ConfigurationSection poolSection = ConfigurationUtils.getDACConfigurationSection(dacID).getConfigurationSection("pool");
		Location playerLocation = player.getLocation();
		poolSection.set("x", playerLocation.getX());
		poolSection.set("y", playerLocation.getY());
		poolSection.set("z", playerLocation.getZ());
		poolSection.set("yaw", playerLocation.getYaw());
		poolSection.set("pitch", playerLocation.getPitch());
		poolSection.set("world", playerLocation.getWorld().getName());
		DeACoudre.getConfigurationManager().saveFile("DAC.yml");
		player.sendMessage(MessageFR.dacSetPool
				.replace("{DAC}", dacName)
		);
	}

	@Subcommand("set diving")
	@CommandPermission("deacoudre.set.diving")
	@CommandCompletion("@dacNames")
	public static void onSetDiving(Player player, String dacName) {
		if (!ConfigurationUtils.isValid(dacName)) {
			player.sendMessage(MessageFR.dacNameDoesNotExist.replace("{DAC}", dacName));
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			return;
		}
		String dacID = ConfigurationUtils.getDACID(dacName);
		ConfigurationSection divingSection = ConfigurationUtils.getDACConfigurationSection(dacID).getConfigurationSection("diving");
		Location playerLocation = player.getLocation();
		divingSection.set("x", playerLocation.getX());
		divingSection.set("y", playerLocation.getY());
		divingSection.set("z", playerLocation.getZ());
		divingSection.set("yaw", playerLocation.getYaw());
		divingSection.set("pitch", playerLocation.getPitch());
		divingSection.set("world", playerLocation.getWorld().getName());
		DeACoudre.getConfigurationManager().saveFile("DAC.yml");
		player.sendMessage(MessageFR.dacSetDiving
				.replace("{DAC}", dacName)
		);
	}

	@Subcommand("set spectator")
	@CommandPermission("deacoudre.set.spectator")
	@CommandCompletion("@dacNames")
	public static void onSetSpectator(Player player, String dacName) {
		if (!ConfigurationUtils.isValid(dacName)) {
			player.sendMessage(MessageFR.dacNameDoesNotExist.replace("{DAC}", dacName));
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			return;
		}
		String dacID = ConfigurationUtils.getDACID(dacName);
		ConfigurationSection spectatorSection = ConfigurationUtils.getDACConfigurationSection(dacID).getConfigurationSection("spectator");
		Location playerLocation = player.getLocation();
		spectatorSection.set("x", playerLocation.getX());
		spectatorSection.set("y", playerLocation.getY());
		spectatorSection.set("z", playerLocation.getZ());
		spectatorSection.set("yaw", playerLocation.getYaw());
		spectatorSection.set("pitch", playerLocation.getPitch());
		spectatorSection.set("world", playerLocation.getWorld().getName());
		DeACoudre.getConfigurationManager().saveFile("DAC.yml");
		player.sendMessage(MessageFR.dacSetSpectator
				.replace("{DAC}", dacName)
		);
	}

	@Subcommand("set end")
	@CommandPermission("deacoudre.set.end")
	@CommandCompletion("@dacNames")
	public static void onSetEnd(Player player, String dacName) {
		if (!ConfigurationUtils.isValid(dacName)) {
			player.sendMessage(MessageFR.dacNameDoesNotExist.replace("{DAC}", dacName));
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			return;
		}
		String dacID = ConfigurationUtils.getDACID(dacName);
		ConfigurationSection endSection = ConfigurationUtils.getDACConfigurationSection(dacID).getConfigurationSection("end");
		Location playerLocation = player.getLocation();
		endSection.set("x", playerLocation.getX());
		endSection.set("y", playerLocation.getY());
		endSection.set("z", playerLocation.getZ());
		endSection.set("yaw", playerLocation.getYaw());
		endSection.set("pitch", playerLocation.getPitch());
		endSection.set("world", playerLocation.getWorld().getName());
		DeACoudre.getConfigurationManager().saveFile("DAC.yml");
		player.sendMessage(MessageFR.dacSetEnd
				.replace("{DAC}", dacName)
		);
	}

	@Subcommand("set pool-region")
	@CommandPermission("deacoudre.set.poolregion")
	@CommandCompletion("@dacNames pos1|pos2")
	public static void onSetPoolRegion(Player player, String dacName, String pos) {
		if (!ConfigurationUtils.isValid(dacName)) {
			player.sendMessage(MessageFR.dacNameDoesNotExist.replace("{DAC}", dacName));
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			return;
		}
		if (!pos.equalsIgnoreCase("pos1") && !pos.equalsIgnoreCase("pos2")) {
			player.sendMessage(MessageFR.invalidArgOnPos.replace("{arg}", pos));
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			return;
		}
		String dacID = ConfigurationUtils.getDACID(dacName);
		ConfigurationSection poolRegionSection = ConfigurationUtils.getDACConfigurationSection(dacID).getConfigurationSection("pool-region." + pos);
		Location playerLocation = player.getLocation();
		poolRegionSection.set("x", playerLocation.getX());
		poolRegionSection.set("y", playerLocation.getY());
		poolRegionSection.set("z", playerLocation.getZ());
		poolRegionSection.set("yaw", playerLocation.getYaw());
		poolRegionSection.set("pitch", playerLocation.getPitch());
		poolRegionSection.set("world", playerLocation.getWorld().getName());
		DeACoudre.getConfigurationManager().saveFile("DAC.yml");
		player.sendMessage(MessageFR.dacSetPoolRegionPos
				.replace("{pos}", pos)
				.replace("{DAC}", dacName)
		);
	}

	@Subcommand("set min-players")
	@CommandPermission("deacoudre.set.minplayers")
	@CommandCompletion("@dacNames @range:1-6")
	public static void onSetMinPlayers(Player player, String dacName, int minPlayers) {
		if (!ConfigurationUtils.isValid(dacName)) {
			player.sendMessage(MessageFR.dacNameDoesNotExist.replace("{DAC}", dacName));
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			return;
		}
		String dacID = ConfigurationUtils.getDACID(dacName);
		ConfigurationSection dacIDSection = ConfigurationUtils.getDACConfigurationSection(dacID);
		dacIDSection.set("min-players", minPlayers);
		DeACoudre.getConfigurationManager().saveFile("DAC.yml");
		player.sendMessage(MessageFR.dacSetMinPlayers
				.replace("{min-players}", String.valueOf(minPlayers))
				.replace("{DAC}", dacName)
		);
	}

	@Subcommand("set max-players")
	@CommandPermission("deacoudre.set.maxplayers")
	@CommandCompletion("@dacNames @range:1-30")
	public static void onSetMaxPlayers(Player player, String dacName, int maxPlayers) {
		if (!ConfigurationUtils.isValid(dacName)) {
			player.sendMessage(MessageFR.dacNameDoesNotExist.replace("{DAC}", dacName));
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			return;
		}
		String dacID = ConfigurationUtils.getDACID(dacName);
		ConfigurationSection dacIDSection = ConfigurationUtils.getDACConfigurationSection(dacID);
		dacIDSection.set("max-players", maxPlayers);
		DeACoudre.getConfigurationManager().saveFile("DAC.yml");
		player.sendMessage(MessageFR.dacSetMaxPlayers
				.replace("{max-players}", String.valueOf(maxPlayers))
				.replace("{DAC}", dacName)
		);
	}

	@Subcommand("remove")
	@CommandPermission("deacoudre.remove")
	@CommandCompletion("@dacNames")
	public static void onSetMaxPlayers(Player player, String dacName) {
		if (!ConfigurationUtils.isValid(dacName)) {
			player.sendMessage(MessageFR.dacNameDoesNotExist.replace("{DAC}", dacName));
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			return;
		}
		FileConfiguration cfg = DeACoudre.getConfigurationManager().getConfigurationFile("DAC.yml");
		ConfigurationSection dacSection = cfg.getConfigurationSection("dac");
		String dacID = ConfigurationUtils.getDACID(dacName);
		dacSection.set(String.valueOf(dacID), null);
		DeACoudre.getConfigurationManager().saveFile("DAC.yml");
		player.sendMessage(MessageFR.dacRemoved
				.replace("{DAC}", dacName)
		);
	}

	@Subcommand("set sign")
	@CommandPermission("deacoudre.set.sign")
	@CommandCompletion("@dacNames")
	public static void onSetSign(Player player, String dacName) {
		if (!ConfigurationUtils.isValid(dacName)) {
			player.sendMessage(MessageFR.dacNameDoesNotExist.replace("{DAC}", dacName));
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			return;
		}
		Block seeBlock = player.getTargetBlock(10);
		if (seeBlock == null) {
			player.sendMessage(MessageFR.tooFarAwayFromABlock);
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			return;
		}
		if (!(seeBlock.getState() instanceof Sign)) {
			player.sendMessage(MessageFR.notInstanceOfSign);
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			return;
		}
		Sign sign = (Sign) seeBlock.getState();
		for (DeACoudreGameSign s : DeACoudre.getAllSigns()) {
			if (s.getSign().getLocation().equals(sign.getLocation())) {
				player.sendMessage(MessageFR.signAlreadyUsed);
				player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
				return;
			}
		}
		FileConfiguration cfg = DeACoudre.getConfigurationManager().getConfigurationFile("DAC.yml");
		ConfigurationSection signsSection = cfg.getConfigurationSection("signs");
		int id = signsSection.getKeys(false).size();
		while (signsSection.contains(String.valueOf(id))) {
			id++;
		}
		DACSignCreateEvent event = new DACSignCreateEvent(player, String.valueOf(id), sign, dacName);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			ConfigurationSection signsIDSection = signsSection.createSection(String.valueOf(id));
			player.sendMessage(MessageFR.dacSetSign.replace("{DAC}", dacName));
			Location signLocation = sign.getLocation();
			signsIDSection.set("x", signLocation.getX());
			signsIDSection.set("y", signLocation.getY());
			signsIDSection.set("z", signLocation.getZ());
			signsIDSection.set("world", signLocation.getWorld().getName());
			signsIDSection.set("dac-name", dacName);
			DeACoudre.getConfigurationManager().saveFile("DAC.yml");
		}
	}

	@Subcommand("unset sign")
	@CommandPermission("deacoudre.unset.sign")
	public static void onRemoveSign(Player player) {
		Block seeBlock = player.getTargetBlock(10);
		if (seeBlock == null) {
			player.sendMessage(MessageFR.tooFarAwayFromABlock);
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			return;
		}
		if (!(seeBlock.getState() instanceof Sign)) {
			player.sendMessage(MessageFR.notInstanceOfSign);
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			return;
		}
		Sign sign = (Sign) seeBlock.getState();
		for (DeACoudreGameSign s : DeACoudre.getAllSigns()) {
			if (s.getSign().getLocation().equals(sign.getLocation())) {
				FileConfiguration cfg = DeACoudre.getConfigurationManager().getConfigurationFile("DAC.yml");
				ConfigurationSection signsSection = cfg.getConfigurationSection("signs");
				assert signsSection != null;
				signsSection.set(s.getSignID(), null);
				s.getDeACoudreGame().getDeACoudreGameSigns().remove(s);
				DeACoudre.getConfigurationManager().saveFile("DAC.yml");
				player.sendMessage(MessageFR.dacUnSetSign.replace("{DAC}", s.getDeACoudreGame().getName()));
				sign.line(0, Component.text(""));
				sign.line(1, Component.text(""));
				sign.line(2, Component.text(""));
				sign.line(3, Component.text(""));
				sign.update();
				return;
			}
		}
		player.sendMessage(MessageFR.dacNoSignToUnSet);
	}

}
