import java.util.HashMap;

public class Games {
    private static HashMap<String, String> gamesAssortment;

    public Games() {
        gamesAssortment = new HashMap<>();
        gamesAssortment.put("GuessNumber", "Угадай число.");
    }

    public static String getGamesAssortment() {
        StringBuilder help = new StringBuilder("\nИгры:");
        for (var game : gamesAssortment.keySet()) {
            help.append("\n");
            help.append(gamesAssortment.get(game));
        }
        return help.toString();
    }
}
