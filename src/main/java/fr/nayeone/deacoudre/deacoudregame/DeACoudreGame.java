package fr.nayeone.deacoudre.deacoudregame;

import fr.nayeone.deacoudre.DeACoudre;
import fr.nayeone.deacoudre.deacoudregame.event.*;
import fr.nayeone.deacoudre.deacoudregame.state.GamePlayerState;
import fr.nayeone.deacoudre.deacoudregame.state.GameState;
import fr.nayeone.deacoudre.deacoudregame.utils.*;
import fr.nayeone.deacoudre.lang.MessageFR;
import fr.nayeone.deacoudre.utils.ConfigurationUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nullable;
import java.util.*;
import java.util.logging.Level;

public class DeACoudreGame implements Listener {

	private final String dacID;

	// From configuration
	private String name;
	private String displayName;
	private int winPoints;
	private Location lobbyLocation;
	private Location poolLocation;
	private Location divingLocation;
	private Location spectatorLocation;
	private Location endLocation;
	private int minPlayers;
	private int maxPlayers;
	private PoolRegion poolRegion;
	private final List<DeACoudreGameSign> deACoudreGameSigns = new ArrayList<>();
	private final List<Location> poolBlocksLoc = new ArrayList<>();

	private boolean isBuild;
	private final DeACoudreScoreboard deACoudreScoreboard = new DeACoudreScoreboard(this);
	private final List<DeACoudreGamePlayer> deACoudreGamePlayers = new ArrayList<>();
	private final List<DeACoudreGamePlayer> playerList = new ArrayList<>();
	private final List<DeACoudreGamePlayer> winners = new ArrayList<>();
	private DeACoudreGamePlayer jumper;
	private int nextPlayerNumber;
	private GameState gameState;
	private int timer = 30;
	private BukkitTask jumpTimer = null;
	private BukkitTask task = null;

	public DeACoudreGame(String dacID) {
		this.dacID = dacID;
		this.isBuild = false;
		this.gameState = GameState.WAITING;
	}

	public void build() {
		FileConfiguration cfg = DeACoudre.getConfigurationManager().getConfigurationFile("DAC.yml");
		ConfigurationSection dacSection = cfg.getConfigurationSection("dac." + this.dacID);
		assert dacSection != null;
		this.name = dacSection.getString("name");
		List<DeACoudreGameSign> deACoudreGameSigns = getSignsFromConfig();
		deACoudreGameSigns.forEach(deACoudreGameSign -> {
			DeACoudreGameSign deACoudreGameSign1 = new DeACoudreGameSign(deACoudreGameSign.getSignID(), deACoudreGameSign.getSign(), this);
			this.deACoudreGameSigns.add(deACoudreGameSign);
		});
		DeACoudre.getPluginLogger().log(Level.INFO, "Le dé à coudre " + this.name + " est lié à " + deACoudreGameSigns.size() + " panneaux.");
		DeACoudre.getListenerManager().registerEvent(this);
		List<String> emptySections = getEmptySectionsName(this.dacID);
		if (emptySections.size() > 0) {
			String dacName = ConfigurationUtils.getDACName(this.dacID);
			for (String s : emptySections) {
				DeACoudre.getPluginLogger().log(
						Level.WARNING,
						"Le dé à coudre (§c{dacID}§e) n'a pas de §c{emptysection} §edéfinie."
								.replace("{dacID}", dacName)
								.replace("{emptysection}", s)
				);
			}
			return;
		}
		this.displayName = dacSection.getString("displayname").replace("&", "§");
		this.winPoints = dacSection.getInt("win-points");
		this.lobbyLocation = getLocationFromCS(dacSection.getConfigurationSection("lobby"));
		this.poolLocation = getLocationFromCS(dacSection.getConfigurationSection("pool"));
		this.divingLocation = getLocationFromCS(dacSection.getConfigurationSection("diving"));
		this.spectatorLocation = getLocationFromCS(dacSection.getConfigurationSection("spectator"));
		this.endLocation = getLocationFromCS(dacSection.getConfigurationSection("end"));
		this.minPlayers = dacSection.getInt("min-players");
		this.maxPlayers = dacSection.getInt("max-players");
		this.poolRegion = new PoolRegion();
		this.poolRegion.setPos1(getLocationFromCS(dacSection.getConfigurationSection("pool-region.pos1")));
		this.poolRegion.setPos2(getLocationFromCS(dacSection.getConfigurationSection("pool-region.pos2")));
		this.deACoudreScoreboard.build();
		this.isBuild = true;
	}


	private Location getLocationFromCS(ConfigurationSection cs) {
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

	private List<String> getEmptySectionsName(String dacID) {
		List<String> emptySections = new ArrayList<>();
		FileConfiguration cfg = DeACoudre.getConfigurationManager().getConfigurationFile("DAC.yml");
		if (!cfg.contains("dac." + dacID + ".lobby.x")) emptySections.add("lobby");
		if (!cfg.contains("dac." + dacID + ".pool.x")) emptySections.add("pool");
		if (!cfg.contains("dac." + dacID + ".diving.x")) emptySections.add("diving");
		if (!cfg.contains("dac." + dacID + ".spectator.x")) emptySections.add("spectator");
		if (!cfg.contains("dac." + dacID + ".end.x")) emptySections.add("end");
		if (!cfg.contains("dac." + dacID + ".pool-region.pos1.x")) emptySections.add("pool-region.pos1");
		if (!cfg.contains("dac." + dacID + ".pool-region.pos2.x")) emptySections.add("pool-region.pos2");
		return emptySections;
	}

	private List<DeACoudreGameSign> getSignsFromConfig() {
		List<DeACoudreGameSign> signs = new ArrayList<>();
		FileConfiguration cfg = DeACoudre.getConfigurationManager().getConfigurationFile("DAC.yml");
		ConfigurationSection signsSection = cfg.getConfigurationSection("signs");
		assert signsSection != null;
		for (String signID : signsSection.getKeys(false)) {
			ConfigurationSection signSection = signsSection.getConfigurationSection(signID);
			String dacName = signSection.getString("dac-name");
			if (dacName.equalsIgnoreCase(this.name)) {
				Location blockLocation = getLocationFromCS(signSection);
				Block block = blockLocation.getBlock();
				if (block.getState() instanceof Sign) {
					signs.add(new DeACoudreGameSign(signID, (Sign) block.getState(), this));
				} else {
					signSection.set(String.valueOf(signID), null);
				}
			}
		}
		return signs;
	}

	@EventHandler
	public void onSignInteract(PlayerInteractEvent event) {
		if (!isBuild) return;
		if (event.getInteractionPoint() == null ||!(event.getInteractionPoint().getBlock().getState() instanceof Sign)) return;
		Sign sign = (Sign) event.getInteractionPoint().getBlock().getState();
		for (DeACoudreGameSign deACoudreGameSign : this.deACoudreGameSigns) {
			if (deACoudreGameSign.getSign().equals(sign)) {
				Player player = event.getPlayer();
				if (DeACoudre.isInDeACoudre(player)) {
					player.sendMessage(MessageFR.prefix + ChatColor.RED + "Tu es déjà dans une partie.");
					return;
				}
				if (this.deACoudreGamePlayers.size() >= this.maxPlayers) {
					player.sendMessage(MessageFR.prefix + ChatColor.RED + "La partie est pleine de joueurs.");
					return;
				}
				switch (this.gameState) {
					case READY:
					case WAITING:
						PlayerJoinDACEvent playerJoinDACEvent = new PlayerJoinDACEvent(player, this);
						Bukkit.getPluginManager().callEvent(playerJoinDACEvent);
						break;
					case IN_PROGRESS:
						player.sendMessage(MessageFR.prefix + "La partie est encore en cours.");
						break;
					case FINISH:
						player.sendMessage(MessageFR.prefix + "La partie va bientôt se relancer.");
						break;
				}

			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		DeACoudreGamePlayer deACoudreGamePlayer = getDeACoudreGamePlayer(player);
		if (deACoudreGamePlayer == null) return;

		Bukkit.getPluginManager().callEvent(new PlayerQuitDACEvent(player, this, QuitCause.LEAVE));
	}

	@EventHandler
	public void onSignCreate(DACSignCreateEvent event) {
		if (!ConfigurationUtils.getDACID(event.getDacName()).equalsIgnoreCase(this.dacID)) return;
		Sign sign = event.getSign();
		for (DeACoudreGameSign s : getSignsFromConfig()) {
			if (ConfigurationUtils.isDACSign(s.getSign())) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(MessageFR.signAlreadyExist.replace("{DAC}", event.getDacName()));
				return;
			}
		}
		DeACoudreGameSign deACoudreGameSign = new DeACoudreGameSign(event.getSignID(), sign, this);
		deACoudreGameSign.update();
		this.deACoudreGameSigns.add(deACoudreGameSign);
		event.setSign(deACoudreGameSign.getSign());
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (isInThisDeACoudreGame(player)) {
			if (player.getInventory().getItemInMainHand().equals(LobbyInventory.leaveItem)) {
				if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))) {
					return;
				}
				Bukkit.getPluginManager().callEvent(new PlayerQuitDACEvent(player, this, QuitCause.LEAVE));
			}
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onGameStateChange(GameStateChangeEvent event) {
		DeACoudreGame deACoudreGame = event.getDeACoudreGame();
		if (!deACoudreGame.getName().equalsIgnoreCase(this.name)) return;
		GameState gameStateFrom = event.getGameStateFrom();
		GameState gameStateTo = event.getGameStateTo();
		if ((gameStateFrom.equals(GameState.WAITING) || gameStateFrom.equals(GameState.FINISH)) && gameStateTo.equals(GameState.READY)) {
			if (this.task != null) {
				this.task.cancel();
			}
			if (!this.winners.isEmpty()) this.winners.clear();
			this.task = new BukkitRunnable() {
				@Override
				public void run() {
					timer--;
					updateState();
				}
			}.runTaskTimer(DeACoudre.getPlugin(DeACoudre.class), 0, 20L);
		} else if (gameStateFrom.equals(GameState.READY) && gameStateTo.equals(GameState.WAITING)) {
			this.task.cancel();
			this.timer = 30;
		} else if (gameStateFrom.equals(GameState.READY) && gameStateTo.equals(GameState.IN_PROGRESS)) {
			this.task.cancel();
			this.timer = 15;
			Bukkit.getPluginManager().callEvent(new DACStartEvent(deACoudreGame));
		} else if (gameStateFrom.equals(GameState.IN_PROGRESS) && gameStateTo.equals(GameState.FINISH)) {
			this.deACoudreGamePlayers.forEach(deACoudreGamePlayer -> {
				Player player = deACoudreGamePlayer.getPlayer();
				player.getInventory().clear();
				player.setLevel(0);
				this.deACoudreScoreboard.removeToPlayer(player);
				player.teleport(this.endLocation);
			});
			if (getAliveDeACoudreGamePlayer().size() != 0 && deACoudreGame.getPoolRegion().hasWaterInside()) {
				DeACoudreGamePlayer winner = getAliveDeACoudreGamePlayer().get(0);
				this.winners.add(winner);
				Bukkit.getPluginManager().callEvent(new PlayerWinEvent(this, winner.getPlayer()));
			} else if (getAliveDeACoudreGamePlayer().size() != 0 && !deACoudreGame.getPoolRegion().hasWaterInside()) {
				for (DeACoudreGamePlayer winner : getAliveDeACoudreGamePlayer()) {
					this.winners.add(winner);
					Bukkit.getPluginManager().callEvent(new PlayerWinEvent(this, winner.getPlayer()));
				}
			}
			this.deACoudreGamePlayers.clear();
			this.playerList.clear();
			this.jumper = null;
			this.task.cancel();
			this.jumpTimer.cancel();
			this.timer = 10;
			this.task = new BukkitRunnable() {
				@Override
				public void run() {
					timer--;
					updateState();
				}
			}.runTaskTimer(DeACoudre.getPlugin(DeACoudre.class), 0, 20L);
			this.poolBlocksLoc.forEach(location -> location.getBlock().setType(Material.WATER));
		}
		DeACoudre.getPluginLogger().log(
				Level.INFO,
				"[" + deACoudreGame.getName() + "] " + "Changement du statue " + gameStateFrom.name() + " -> " + gameStateTo.name()
		);
	}

	private boolean isPerfectJump(Location jumperPos) {
		List<Block> blockAround = new ArrayList<>();
		blockAround.add(jumperPos.add(1, 0, 0).getBlock());
		blockAround.add(jumperPos.add(-2, 0, 0).getBlock());
		blockAround.add(jumperPos.add(1, 0, 1).getBlock());
		blockAround.add(jumperPos.add(0, 0, -2).getBlock());
		for (Block block : blockAround) {
			if (block.getType().equals(Material.WATER)) {
				return false;
			}
		}
		return true;
	}

	private void nextJumper() {
		if (jumpTimer != null) {
			this.jumpTimer.cancel();
		}
		this.jumper = playerList.get(nextPlayerNumber - 1);
		this.jumper.getPlayer().teleport(divingLocation);
		this.timer = 15;
		jumpTimer = startChecker();

		nextPlayerNumber++;
		if (nextPlayerNumber > playerList.size()) {
			nextPlayerNumber = 1;
		}
		while (!playerList.get(nextPlayerNumber - 1).isAlive()) {
			nextPlayerNumber++;
			if (nextPlayerNumber > playerList.size()) {
				nextPlayerNumber = 1;
			}
		}
		playerList.get(nextPlayerNumber - 1).getPlayer().sendMessage(getDeACoudreGamePrefix() + "Tu es le prochain à sauter.");
	}

	@EventHandler
	public void onPlayerWInEvent(PlayerWinEvent event) {
		DeACoudreGame deACoudreGame = event.getDeACoudreGame();
		if (!deACoudreGame.equals(this)) return;
		Player winner = event.getPlayer();
		winner.sendMessage(getDeACoudreGamePrefix() + "Bravo ! Tu as gagné cette partie. (§e+" + this.winPoints + " points§f)");
	}

	@EventHandler
	public void onPlayerJumpSuccess(PlayerOnWaterEvent event) {
		DeACoudreGame deACoudreGame = event.getDeACoudreGame();
		Sound sound = null;
		if (!deACoudreGame.equals(this)) return;
		nextJumper();
		DeACoudreGamePlayer oldJumper = event.getDeACoudreGamePlayer();
		Material blockToFill = Material.ORANGE_WOOL;
		if (isPerfectJump(oldJumper.getPlayer().getLocation())) {
			oldJumper.getPlayer().sendMessage(getDeACoudreGamePrefix() + "Tu viens de faire un dé à coudre !");
			oldJumper.setPerfectJumpCount(oldJumper.getPerfectJumpCount() + 1);
			oldJumper.setLifePoint(oldJumper.getLifePoint() + 1);
			oldJumper.getPlayer().setLevel(oldJumper.getLifePoint());
			sound = Sound.BLOCK_NOTE_BLOCK_GUITAR;
			blockToFill = Material.EMERALD_BLOCK;
		}
		Location waterLoc = oldJumper.getPlayer().getLocation();
		Location waterLoc2 = waterLoc.clone();
		while (waterLoc.getBlock().getType().equals(Material.WATER)) {
			waterLoc.getBlock().setType(blockToFill);
			this.poolBlocksLoc.add(waterLoc.clone());
			waterLoc.add(0, 1, 0);
		}
		waterLoc2.add(0, -1, 0);
		while (waterLoc2.getBlock().getType().equals(Material.WATER)) {
			waterLoc2.getBlock().setType(blockToFill);
			this.poolBlocksLoc.add(waterLoc2.clone());
			waterLoc2.add(0, -1, 0);
		}
		oldJumper.getPlayer().teleport(this.poolLocation);
		if (sound != null) {
			oldJumper.getPlayer().playSound(oldJumper.getPlayer().getLocation(), sound, 0.5F, 1);
		}
		updateState();
	}

	@EventHandler
	public void onStart(DACStartEvent event) {
		DeACoudreGame deACoudreGame = event.getDeACoudreGame();
		if (!deACoudreGame.equals(this)) return;
		this.playerList.clear();
		this.playerList.addAll(this.deACoudreGamePlayers);
		this.nextPlayerNumber = 2;
		this.jumper = this.deACoudreGamePlayers.get(0);
		this.jumper.getPlayer().teleport(this.divingLocation);
		this.playerList.get(nextPlayerNumber - 1).getPlayer().sendMessage(getDeACoudreGamePrefix() + "Tu es le prochain à sauter.");
		jumpTimer = startChecker();
		this.task = new BukkitRunnable() {
			@Override
			public void run() {
				if (timer == 0) {
					timer = 15;
					Bukkit.getPluginManager().callEvent(new PlayerQuitDACEvent(jumper.getPlayer(), deACoudreGame, QuitCause.AFK));
					if (getAliveDeACoudreGamePlayer().size() > 1) {
						nextJumper();
					}
				} else if (timer <= 8) {
					jumper.getPlayer().playSound(jumper.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
				}
				timer--;
			}
		}.runTaskTimer(DeACoudre.getPlugin(DeACoudre.class), 0, 20L);
		DeACoudre.getPluginLogger().log(
				Level.INFO,
				"[" + deACoudreGame.getName() + "] " + "La partie vient de commencer"
		);
	}

	public BukkitTask startChecker() {
		DeACoudreGame deACoudreGame = this;
		return new BukkitRunnable() {
			@Override
			public void run() {
				if(jumper.getPlayer().getLocation().getBlock().getType() == Material.WATER) {
					Bukkit.getPluginManager().callEvent(new PlayerOnWaterEvent(deACoudreGame, jumper));
				}
			}
		}.runTaskTimer(DeACoudre.getPlugin(DeACoudre.class), 0, 1L);
	}

	@EventHandler
	public void onPlayerJoinDAC(PlayerJoinDACEvent event) {
		DeACoudreGame deACoudreGame = event.getDeACoudreGame();
		Player player = event.getPlayer();
		if (deACoudreGame.equals(this)) {
			DeACoudreGamePlayer deACoudreGamePlayer = new DeACoudreGamePlayer(player);
			deACoudreGamePlayer.setDeACoudreGame(this);
			deACoudreGamePlayer.setGamePlayerState(GamePlayerState.WAITING_START);
			this.deACoudreGamePlayers.add(deACoudreGamePlayer);
			player.teleport(this.lobbyLocation);
			LobbyInventory.setToPlayer(player);
			player.setGameMode(GameMode.ADVENTURE);
			joinMessage(player);
			this.deACoudreScoreboard.addToPlayer(player);
			player.getInventory().setHeldItemSlot(4);
			player.setLevel(deACoudreGamePlayer.getLifePoint());
			updateState();
			DeACoudre.getPluginLogger().log(
					Level.INFO,
					"[" + deACoudreGame.getName() + "] " + "Le joueur " + player.getName() + " a rejoint la partie."
			);
		}
	}

	@EventHandler
	public void onPlayerQuitDAC(PlayerQuitDACEvent event) {
		DeACoudreGame deACoudreGame = event.getDeACoudreGame();
		Player player = event.getPlayer();
		if (deACoudreGame.equals(this)) {
			DeACoudreGamePlayer deACoudreGamePlayer = getDeACoudreGamePlayer(player);
			QuitCause quitCause = event.getQuitCause();
			if (this.gameState.equals(GameState.IN_PROGRESS)) {
				this.playerList.get(this.playerList.indexOf(deACoudreGamePlayer)).setAlive(false);
			}
			if (this.jumper != null && this.jumper.equals(deACoudreGamePlayer)) {
				nextJumper();
			}
			this.deACoudreGamePlayers.remove(deACoudreGamePlayer);
			player.getInventory().clear();
			player.teleport(this.endLocation);
			deACoudreGamePlayer.getDeACoudreGamePlayerStats().save();
			this.deACoudreScoreboard.removeToPlayer(player);
			player.getInventory().setHeldItemSlot(4);
			player.setLevel(0);
			updateState();
			switch (quitCause) {
				case LEAVE:
					leaveMessage(player);
					break;
				case AFK:
					afkMessage(player);
					break;
			}
			DeACoudre.getPluginLogger().log(
					Level.INFO,
					"[" + deACoudreGame.getName() + "] " + "Le joueur " + player.getName() + " a quitté la partie."
			);
		}
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (isInThisDeACoudreGame(player)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onClickInventory(InventoryClickEvent event) {
		if (!(event.getView().getPlayer() instanceof Player)) return;
		Player player = (Player) event.getView().getPlayer();
		if (isInThisDeACoudreGame(player)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (playerIsInDeACoudreGame(player)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBreakBlock(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (playerIsInDeACoudreGame(player)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerTakeDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		if (playerIsInDeACoudreGame(player) && event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
			if (this.jumper.getPlayer().equals(player)) {
				if (this.jumper.getLifePoint() <= 1) {
					this.jumper.getPlayer().teleport(this.spectatorLocation);
					this.jumper.getPlayer().sendMessage(getDeACoudreGamePrefix() + "Tu as été éliminé.");
					this.jumper.getPlayer().setLevel(0);
					this.jumper.getPlayer().playSound(this.jumper.getPlayer().getLocation(), Sound.BLOCK_BELL_USE, 0.5F, 1);
					this.jumper.setAlive(false);
					this.jumper.getPlayer().setLevel(this.jumper.getLifePoint());
					if (!(getAliveDeACoudreGamePlayer().size() <= 1)) {
						nextJumper();
					}
					updateState();
				} else {
					this.jumper.setLifePoint(this.jumper.getLifePoint() - 1);
					player.sendMessage(getDeACoudreGamePrefix() + "Tu as perdu une vie.");
					player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 0.5F, 1);
					this.jumper.getPlayer().teleport(this.poolLocation);
					this.jumper.getPlayer().setLevel(this.jumper.getLifePoint());
					nextJumper();
				}
				event.setCancelled(true);
			}
			event.setCancelled(true);
		}
 	}

 	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (isInThisDeACoudreGame(player)) {
			event.setCancelled(true);
		}
	}

	public void updateState() {
		switch (this.gameState) {
			case WAITING:
				if (this.deACoudreGamePlayers.size() >= this.minPlayers) {
					this.gameState = GameState.READY;
					Bukkit.getPluginManager().callEvent(new GameStateChangeEvent(this, GameState.READY, GameState.WAITING));
				}
				break;
			case READY:
				if (this.deACoudreGamePlayers.size() < this.minPlayers) {
					this.gameState = GameState.WAITING;
					Bukkit.getPluginManager().callEvent(new GameStateChangeEvent(this, GameState.WAITING, GameState.READY));
				} else if (this.timer == 0) {
					this.task.cancel();
					this.timer = 15;
					this.gameState = GameState.IN_PROGRESS;
					Bukkit.getPluginManager().callEvent(new GameStateChangeEvent(this, GameState.IN_PROGRESS, GameState.READY));
				} else if (this.deACoudreGamePlayers.size() == this.maxPlayers) {
					this.task.cancel();
					this.timer = 15;
					this.gameState = GameState.IN_PROGRESS;
					Bukkit.getPluginManager().callEvent(new GameStateChangeEvent(this, GameState.IN_PROGRESS, GameState.READY));
				}
				break;
			case IN_PROGRESS:
				if (getAliveDeACoudreGamePlayer().size() <= 1) {
					this.gameState = GameState.FINISH;
					Bukkit.getPluginManager().callEvent(new GameStateChangeEvent(this, GameState.FINISH, GameState.IN_PROGRESS));
				} else if (!this.poolRegion.hasWaterInside()) {
					this.gameState = GameState.FINISH;
					Bukkit.getPluginManager().callEvent(new GameStateChangeEvent(this, GameState.FINISH, GameState.IN_PROGRESS));
				}
				break;
			case FINISH:
				if (this.timer == 0) {
					this.task.cancel();
					this.timer = 30;
					this.gameState = GameState.WAITING;
					Bukkit.getPluginManager().callEvent(new GameStateChangeEvent(this, GameState.WAITING, GameState.FINISH));
				}
				break;
		}
	}

	private boolean isInThisDeACoudreGame(Player player) {
		for (DeACoudreGamePlayer deACoudreGamePlayer : this.deACoudreGamePlayers) {
			if (deACoudreGamePlayer.getPlayer().equals(player)) {
				return true;
			}
		}
		return false;
	}

	@Nullable
	public DeACoudreGamePlayer getDeACoudreGamePlayer(Player player) {
		if (isInThisDeACoudreGame(player)) {
			for (DeACoudreGamePlayer deACoudreGamePlayer : this.deACoudreGamePlayers) {
				if (deACoudreGamePlayer.getPlayer().equals(player)) {
					return deACoudreGamePlayer;
				}
			}
		}
		return null;
	}

	public boolean playerIsInDeACoudreGame(Player player) {
		for (DeACoudreGamePlayer deACoudreGamePlayer : this.deACoudreGamePlayers) {
			Player p  = deACoudreGamePlayer.getPlayer();
			if (p.equals(player)) {
				return true;
			}
		}
		return false;
	}

	private void joinMessage(Player joiner) {
		this.deACoudreGamePlayers.forEach(deACoudreGamePlayer1 -> {
			Player player1 = deACoudreGamePlayer1.getPlayer();
			if (player1.equals(joiner)) return;
			player1.sendMessage(getDeACoudreGamePrefix() + ChatColor.YELLOW +
					joiner.getName() + ChatColor.GREEN + " vient de rejoindre la partie.");
		});
	}

	public String getDeACoudreGamePrefix() {
		String displayname;
		if (this.displayName != null && !this.displayName.equalsIgnoreCase("none")) {
			displayname = this.displayName;
		} else {
			displayname = this.name;
		}
		return displayname + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + " » " + ChatColor.RESET;
	}

	private void afkMessage(Player player) {
		this.deACoudreGamePlayers.forEach(deACoudreGamePlayer1 -> {
			Player player1 = deACoudreGamePlayer1.getPlayer();
			if (player1.equals(player)) return;
			player1.sendMessage(getDeACoudreGamePrefix() + ChatColor.YELLOW +
					player.getName() + ChatColor.RED + " a perdu car il n'a pas sauté.");
		});
	}

	private void leaveMessage(Player leaver) {
		this.deACoudreGamePlayers.forEach(deACoudreGamePlayer1 -> {
			Player player1 = deACoudreGamePlayer1.getPlayer();
			if (player1.equals(leaver)) return;
			player1.sendMessage(getDeACoudreGamePrefix() + ChatColor.YELLOW +
					leaver.getName() + ChatColor.RED + " vient de quitter la partie.");
		});
	}

	public String getName() {
		return name;
	}

	@Nullable
	public String getDisplayName() {
		if (!isBuild) return null;
		return displayName;
	}

	public int getWinPoints() {
		if (!isBuild) return 0;
		return winPoints;
	}

	@Nullable
	public Location getLobbyLocation() {
		if (!isBuild) return null;
		return lobbyLocation;
	}

	@Nullable
	public Location getPoolLocation() {
		if (!isBuild) return null;
		return poolLocation;
	}

	@Nullable
	public Location getDivingLocation() {
		if (!isBuild) return null;
		return divingLocation;
	}

	@Nullable
	public Location getSpectatorLocation() {
		if (!isBuild) return null;
		return spectatorLocation;
	}

	@Nullable
	public Location getEndLocation() {
		if (!isBuild) return null;
		return endLocation;
	}

	public int getMaxPlayers() {
		if (!isBuild) return 0;
		return maxPlayers;
	}

	public int getMinPlayers() {
		if (!isBuild) return 0;
		return minPlayers;
	}

	public List<DeACoudreGameSign> getDeACoudreGameSigns() {
		return deACoudreGameSigns;
	}

	public PoolRegion getPoolRegion() {
		return poolRegion;
	}

	public List<DeACoudreGamePlayer> getAliveDeACoudreGamePlayer() {
		List<DeACoudreGamePlayer> alive = new ArrayList<>();
		this.deACoudreGamePlayers.forEach(deACoudreGamePlayer -> {
			if (deACoudreGamePlayer.isAlive()) {
				alive.add(deACoudreGamePlayer);
			}
		});
		return alive;
	}

	public int getTotalPerfectJump() {
		int count = 0;
		for (DeACoudreGamePlayer deACoudreGamePlayer : this.deACoudreGamePlayers) {
			count += deACoudreGamePlayer.getPerfectJumpCount();
		}
		return count;
	}

	public boolean isBuild() {
		return isBuild;
	}

	public List<DeACoudreGamePlayer> getWinners() {
		return winners;
	}

	public GameState getGameState() {
		return gameState;
	}

	public List<DeACoudreGamePlayer> getDeACoudreGamePlayers() {
		return deACoudreGamePlayers;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	private static class LobbyInventory {

		private static ItemStack leaveItem;

		private static final Map<ItemStack, Integer> itemAndPos = new HashMap<>();
		static {
			ItemStack leaveItem = new ItemStack(Material.DARK_OAK_DOOR);
			ItemMeta leaveItemMeta = leaveItem.getItemMeta();
			leaveItemMeta.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "« " + ChatColor.RED + "Retour au spawn");
			leaveItem.setItemMeta(leaveItemMeta);
			itemAndPos.put(leaveItem, 8);
			LobbyInventory.leaveItem = leaveItem;
		}

		public static void setToPlayer(Player player) {
			Inventory playerInventory = player.getInventory();
			playerInventory.clear();
			itemAndPos.forEach((itemStack, integer) -> playerInventory.setItem(integer, itemStack));
		}

		public static ItemStack getLeaveItem() {
			return leaveItem;
		}
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public int getTimer() {
		return timer;
	}

	@Nullable
	public DeACoudreGamePlayer getNextJumper() {
		if (playerList.size() < 1) {
			return null;
		}
		return playerList.get(this.nextPlayerNumber - 1);
	}

	@Nullable
	public DeACoudreGamePlayer getJumper() {
		return this.jumper;
	}

	public String getJumperName() {
		String jumperName;
		if (this.getJumper() == null) {
			jumperName = "Personne";
		} else {
			jumperName = this.getJumper().getPlayer().getName();
		}
		return jumperName;
	}

	public String getNextJumperName() {
		String nextJumperName;
		if (this.getNextJumper() == null) {
			nextJumperName = "Personne";
		} else {
			nextJumperName = this.getNextJumper().getPlayer().getName();
		}
		return nextJumperName;
	}

	public DeACoudreScoreboard getDeACoudreScoreboard() {
		return deACoudreScoreboard;
	}
}
