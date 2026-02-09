package org.cws.paintOff.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.cws.paintOff.PaintOff;

public class ScoreManager {
    PaintOff instance = PaintOff.getInstance();
    int bars = 40;
    double percentageDivider = (double) 100 / bars;

    public void setupScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("gameScoreboard", "dummy","§6§lPaint Off");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.getScore("§7-------------------").setScore(2);
        String color = instance.paintManager.getColorCode(instance.paintManager.getColorByPlayer(player,false));
        String enemyColor = instance.paintManager.getColorCode(instance.paintManager.getColorByPlayer(player,true));

        int teamPainted = instance.paintManager.teamPainted.get(instance.gameManager.teamOfPlayer(player,false));
        int enemyPainted;
        if (instance.paintManager.teamPainted.get(instance.gameManager.teamOfPlayer(player,true)) != null) {
            enemyPainted = instance.paintManager.teamPainted.get(instance.gameManager.teamOfPlayer(player,true));
        } else {
            enemyPainted = 0;
        }

        double percentage = (double) teamPainted / (teamPainted + enemyPainted) * 100;
        int progressBars = (int) (percentage / percentageDivider);

        StringBuilder progressBar = new StringBuilder();
        for (int i = 0; i < bars; i++) {
            progressBar.append(i < progressBars ? color : enemyColor).append("|");
        }

        objective.getScore(" " + progressBar + "§7 " + (int)percentage + "%").setScore(1);

        player.setScoreboard(scoreboard);
    }

    public void updateScore(Player player) {
        if (player.getScoreboard() == Bukkit.getScoreboardManager().getMainScoreboard()) {
            setupScoreboard(player);
        } else {
            Scoreboard scoreboard = player.getScoreboard();
            Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
            if (objective != null) {
                objective.unregister();
            }
            setupScoreboard(player);
        }
    }

    public void removeScoreboard(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
}