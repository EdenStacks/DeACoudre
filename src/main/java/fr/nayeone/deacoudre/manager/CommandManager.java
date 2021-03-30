package fr.nayeone.deacoudre.manager;

import co.aikar.commands.PaperCommandManager;
import fr.nayeone.deacoudre.command.DAC;
import fr.nayeone.deacoudre.utils.ConfigurationUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandManager {

	public CommandManager(Plugin plugin) {
		PaperCommandManager manager = new PaperCommandManager(plugin);
		manager.registerCommand(new DAC());
		manager.getCommandCompletions().registerAsyncCompletion(
				"dacNames",
				context -> {
					Player player = context.getPlayer();
					player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1, 1);
					return ConfigurationUtils.getAllDACName();
				}
		);
	}

}
