public class GuessTheNumberGame {
    private int number;
    private boolean win = false;
    private int tries;

    public GuessTheNumberGame() {
        number = (int) (Math.random() * 100);
    }

    public String makeGuess(int guess) {
        tries += 1;
        if (guess > number)
            return "Меньше";
        if (guess < number)
            return "Больше";
        win = true;
        return "Вы угадали!";
    }

    public boolean getWin() {
        return win;
    }

    public int getTries() {
        return tries;
    }
}
