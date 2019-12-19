import java.util.HashMap;

public class Games {
    private static HashMap<String, String> gamesAssortment;
    private String currentGame;
    private GuessTheNumberGame guessNumber;
    private BullsAndCows bullsAndCows;

    public Games() {
        gamesAssortment = new HashMap<>();
        gamesAssortment.put("GuessNumber", "Питомец загадывает число от 0 до 100. Вы должны угадать число.");
    }

    public void play(String gameName) {
        currentGame = gameName;
        if (currentGame.equals("GuessNumber")) {
            this.guessNumber = new GuessTheNumberGame();
        }
    }

    public String doAction(String[] args) {
        if (currentGame.equals("GuessNumber")) {
            return this.guessNumber.makeGuess(Integer.parseInt(args[0]));
        }
        return "Произошла ошибка";
    }

    public static String getGamesAssortment() {
        StringBuilder help = new StringBuilder("\nИгры:");
        for (var game : gamesAssortment.keySet()) {
            help.append("\n" + game + ": ");
            help.append(gamesAssortment.get(game));
        }
        return help.toString();
    }

    public boolean getWin() {
        if (currentGame.equals("GuessNumber")) {
            return guessNumber.getWin();
        }
        return false;
    }

    public int getMoney() {
        if (currentGame.equals("GuessNumber")) {
            return 50 - guessNumber.getTries();
        }
        return 0;
    }

    public HashMap<String, String> getGames() {
        return gamesAssortment;
    }
}
