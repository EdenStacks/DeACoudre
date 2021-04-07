package fr.nayeone.deacoudre.deacoudregame.utils;

import fr.nayeone.deacoudre.DeACoudre;
import fr.nayeone.deacoudre.deacoudregame.DeACoudreGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class DeACoudreScoreboard {

    private final DeACoudreGame deACoudreGame;

    private boolean isBuild = false;
    private final List<Player> playerList = new ArrayList<>();
    private Scoreboard board;
    private Objective objective;

    public DeACoudreScoreboard(DeACoudreGame deACoudreGame) {
        this.deACoudreGame = deACoudreGame;
    }

    public void build() {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        this.board = scoreboardManager.getNewScoreboard();
        this.objective = this.board.registerNewObjective(
                "DAC - " + this.deACoudreGame.getName(),
                "dummy",
                ChatColor.translateAlternateColorCodes('&', this.deACoudreGame.getDeACoudreGamePrefix()
                        + " &8&l| &d" + this.deACoudreGame.getTimer())
        );
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        setLines();
        this.isBuild = true;
    }

    public void update() {
        if (!isBuild) {
            DeACoudre.getPluginLogger().log(
                    Level.INFO,
                    "[" + this.deACoudreGame.getName() + "] " + " Le scoreboard ne peut pas être update avant d'être build."
            );
            return;
        }
        this.objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.deACoudreGame.getDeACoudreGamePrefix()
                + " &8&l| &d" + this.deACoudreGame.getTimer()));
        setLines();
        playerList.forEach(player -> player.setScoreboard(this.board));
    }

    public void setLines() {
        String jumperName = this.deACoudreGame.getJumperName();
        Score line1 = this.objective.getScore("§f");
        line1.setScore(2);
        Score line2 = objective.getScore(ChatColor.translateAlternateColorCodes(
                '&',
                "&eJumper &f: &d" + jumperName
        ));
        line2.setScore(1);
        String nextJumperName = this.deACoudreGame.getNextJumperName();
        Score line3 = this.objective.getScore(ChatColor.translateAlternateColorCodes(
                '&',
                "&eNext Jumper &f: &d" + nextJumperName
        ));
        line3.setScore(0);
    }

    public void addToPlayer(Player player) {
        this.playerList.add(player);
        player.setScoreboard(this.board);
    }

    public void removeToPlayer(Player player) {
        this.playerList.remove(player);
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public boolean isBuild() {
        return isBuild;
    }
}
