package fr.nayeone.deacoudre.lang;

import org.bukkit.ChatColor;

public class MessageFR {

	public static String prefix = ChatColor.YELLOW + "Dé à Coudre" + ChatColor.WHITE + ChatColor.BOLD + " » " + ChatColor.RESET;

	public static String dacNameAlreadyExist = prefix + ChatColor.RED + "Ce nom de dé à coudre est déjà utilisé.";
	public static String dacCreated = prefix + ChatColor.YELLOW + "{DAC}" + ChatColor.WHITE + " a été crée avec succès.";
	public static String dacNameDoesNotExist = prefix + ChatColor.YELLOW + "{DAC}" + ChatColor.RED + " n'est pas un nom valide.";
	public static String dacSetDisplayname = prefix + "Le displayname : {displayname}" + ChatColor.WHITE + " a été appliqué pour {DAC}.";
	public static String dacSetWinPoints = prefix + "{DAC} rapporte maintenant {win-points} points par victoire.";
	public static String dacSetLobby = prefix + "Le lobby est en place pour {DAC}.";
	public static String dacSetPool = prefix + "La pool est en place pour {DAC}.";
	public static String dacSetDiving = prefix + "Le diving est en place pour {DAC}.";
	public static String dacSetSpectator = prefix + "Le spectator est en place pour {DAC}.";
	public static String dacSetEnd = prefix + "Le end est en place pour {DAC}.";
	public static String dacSetPoolRegionPos = prefix + "La pool-region ({pos}}) est en place pour {DAC}.";
	public static String invalidArgOnPos = prefix + ChatColor.RED + "{arg} n'est pas valide. Utilisez pos1 ou pos2.";
	public static String dacSetMinPlayers = prefix + "{DAC} à maintenant une limite basse de {min-players} joueur(s).";
	public static String dacSetMaxPlayers = prefix + "{DAC} à maintenant une limite haute de {max-players} joueur(s).";
	public static String dacRemoved = prefix + ChatColor.YELLOW + "{DAC}" + ChatColor.WHITE + " a été supprimé avec succès.";
	public static String tooFarAwayFromABlock = prefix + ChatColor.RED + "Tu es trop loin d'un panneau.";
	public static String notInstanceOfSign = prefix + ChatColor.RED + "Tu dois regarder un panneau.";
	public static String dacSetSign = prefix + "Panneau en place pour {DAC}.";
	public static String signAlreadyExist = prefix + ChatColor.RED + "Ce panneau est déjà en place pour " + ChatColor.YELLOW + "{DAC}";
	public static String signAlreadyUsed = prefix + ChatColor.RED + "Ce panneau est déjà utilisé par un DAC.";
	public static String dacUnSetSign = prefix + "Le panneau n'est plus lié à {DAC}.";
	public static String dacNoSignToUnSet = prefix + ChatColor.RED + "Ce panneau n'est lié à aucun dé à coudre.";

}
