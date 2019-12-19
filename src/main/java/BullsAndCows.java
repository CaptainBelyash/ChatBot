import java.util.HashSet;

public class BullsAndCows {
    private int tries;
    private boolean win = false;
    private String word;
    private HashSet<String> lettersSet;
    private static String[] words = new String[] {"точка", "школа", "ствол", "поезд", "качан"};

    public BullsAndCows() {
        var number = (int) (Math.random() * (words.length - 1));
        word = words[number];
        for (var i = 0; i < word.length(); i++) {
            lettersSet.add(String.valueOf(word.charAt(i)));
        }
    }

    public String makeGuess(String guess) {
        var cows = 0;
        var bulls = 0;
        for (var i = 0; i < word.length(); i++) {
            if (lettersSet.contains(String.valueOf(guess.charAt(i)))) {
                if (word.charAt(i) == guess.charAt(i))
                    bulls++;
                else
                    cows++;
            }
        }
        if (bulls == 5) {
            win = true;
            return "Вы угадали!";
        }
        return "Коровы: " + cows + "\nБыки: " + bulls;
    }

    public boolean getWin() {
        return win;
    }

    public int getTries() {
        return tries;
    }
}
