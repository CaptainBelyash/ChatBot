import java.util.HashMap;

public class Games {
    private static HashMap<String, String> gamesAssortment;
    private String currentGame;
    private GuessTheNumberGame guessNumber;
    private BullsAndCows bullsAndCows;

    public Games() {
        gamesAssortment = new HashMap<>();
        gamesAssortment.put("GuessNumber", "Питомец загадывает число от 0 до 100. Вы должны угадать число.");
        gamesAssortment.put("BullsAndCows", "Питомец загадывает пятибуквенное слово без повторяющихся букв. " +
                "Вы должны называть ему пятибуквенные слова без повторяющихся букв. " +
                "После чего питомец называет количество коров и быков." +
                "Коровы - количество букв в вашем слове, которые содержатся в загаданном. " +
                "Быки - количество букв в вашем слове, которые стоят на тех же позициях, что и в загаданном.");
    }

    public void play(String gameName) {
        currentGame = gameName;
        if (currentGame.equals("GuessNumber")) {
            this.guessNumber = new GuessTheNumberGame();
        }
        if (currentGame.equals("BullsAndCows")) {
            this.bullsAndCows = new BullsAndCows();
        }
    }

    public String doAction(String[] args) {
        if (currentGame.equals("GuessNumber")) {
            return this.guessNumber.makeGuess(Integer.parseInt(args[0]));
        }
        if (currentGame.equals("BullsAndCows")) {
            return this.bullsAndCows.makeGuess(args[0]);
        }
        return "Произошла ошибка";
    }

    public static String getGamesAssortment() {
        StringBuilder help = new StringBuilder("\nИгры:");
        for (var game : gamesAssortment.keySet()) {
            help.append("\n");
            help.append(gamesAssortment.get(game));
        }
        return help.toString();
    }

    public boolean getWin() {
        if (currentGame.equals("GuessNumber")) {
            return guessNumber.getWin();
        }
        if (currentGame.equals("BullsAndCows")) {
            return bullsAndCows.getWin();
        }
        return false;
    }

    public int getMoney() {
        if (currentGame.equals("GuessNumber")) {
            return 50 - guessNumber.getTries();
        }
        if (currentGame.equals("BullsAndCows")) {
            return 100 - bullsAndCows.getTries();
        }
        return 0;
    }
}
