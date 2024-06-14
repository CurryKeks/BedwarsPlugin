package me.currycookie.libs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomScoreboard {

    private String boardTitle;
    private Scoreboard board;
    private Objective objective;
    private ArrayList<String> body;
    private HashMap<String, VariableWorth> variables;
    public CustomScoreboard CustomScoreboard(String boardTitle) {
        this.variables = new HashMap<>();
        this.boardTitle = boardTitle;
        this.board = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = this.board.registerNewObjective(boardTitle, boardTitle.hashCode() +"", boardTitle);

        return this;
    }

    public CustomScoreboard setBody(String... body) {
        for(int i = 0; i < body.length; i++) {
            this.objective.getScore(body[i]).setScore(i);
        }
        return this;
    }

    public CustomScoreboard setVariable(String variable, VariableWorth variableWorth) {
        return this;
    }

    public class VariableWorth {
        public String variableReturn(Player player) {return "NO_USAGE";};
        }
}
