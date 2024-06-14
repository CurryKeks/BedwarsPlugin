package me.currycookie.libs;

import me.currycookie.BedwarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <h1>Sollte etwas hier nicht stimmen, schreib mich einfach an</h1>
 */
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
        this.objective = this.board.registerNewObjective(boardTitle, boardTitle.hashCode() + "", boardTitle);
        this.objective.setDisplayName(boardTitle);

        return this;
    }

    /**
     * Setzt den "Innenraum" des Scoreboards.
     * @param body Der Inhalt des Scoreboards (nutze hier Variablen und ersetze sie in ::setVariable()!)
     * @return das CustomScoreboard für Chaining
     */
    public CustomScoreboard setBody(String... body) {
        for (int i = 0; i < body.length; i++) {
            this.objective.getScore(body[i]).setScore(i);
        }
        return this;
    }

    /**
     * Setzt die Variable, welche vorher in ::body() angegeben wurden
     * @param variable Die Variable, die ersetzt werden soll
     * @param variableWorth die zu ersetzende Variable (nutze: new VariableWorth() {
     *       public String variableReturn(<Optional> Player player) {
     *                    return "neuerString"
     *       }
     * }
     * @returnc
     */
    public CustomScoreboard setVariable(String variable, VariableWorth variableWorth) {
        this.variables.put(variable, variableWorth);
        this.objective = this.board.registerNewObjective(this.boardTitle, this.boardTitle.hashCode() + "", this.boardTitle);
        for(int i = 0; i < this.body.size(); i++) {
            if (this.body.get(i).contains(variable)) {
                this.objective.getScore(this.body.get(i).replace(variable, variableWorth.variableReturn())).setScore(i);
            } else
                this.objective.getScore(this.body.get(i)).setScore(i);
        }
        return this;
    }

    /**
     *  Animiert die Hauptanzeige des Scoreboard (hoffe ich zumindest)
     *  Der Normale Boardtitel wird überschrieben und erst in ::send() neu gesetzt (falls sie aufgerufen werden sollte)
     *
     * @param backAndForth ob die Animation auch rückwärts abgespielt werden sollte
     * @param durationBetweenAnimations wie schnell die Animation abgespielt werden sollte
     * @param animation Die Animation in einzelnen Frames als String wiedergeben
     */
    public void setAnimatedTitle(boolean backAndForth, int durationBetweenAnimations, String... animation) {
        new BukkitRunnable() {

            int counter;
            boolean backwards;
            public void run() {
                if(counter >= animation.length)
                    if(backAndForth) {
                        backwards = true;
                    } else
                    counter = 0;
                if(counter == 0)
                    if(backwards){
                        backwards = false;
                    }
                if(backwards)
                    counter--;
                else
                    counter++;
                objective.setDisplayName(animation[counter]);
            }
        }.runTaskTimer(BedwarsPlugin.getInstance(), 0, durationBetweenAnimations);
    }

    /**
     * Sende dem Spieler das Scoreboard (NICHT VERWECHSELN MIT DER ::update(Player) METHODE)
     * @param player
     */
    public void send(Player player) {
        player.setScoreboard(this.board);
    }

    /**
     * Sendet das Scoreboard an den Spieler mit geupdateten Variablen
     * @param player
     */
    public void update(Player player) {
        this.variables.keySet().forEach(var -> {
            setVariable(var, this.variables.get(var));
        });
        player.setScoreboard(this.board);
    }

    public class VariableWorth {
        public String variableReturn(Player player) {
            return "NO_PLAYER";
        }
        public String variableReturn() {
            return "NO_USAGE";
        }
    }
}
