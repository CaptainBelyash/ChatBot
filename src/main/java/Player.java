import java.util.concurrent.atomic.AtomicInteger;

public class Player {
    private String ID;
    private Pet pet;
    private Fridge fridge;
    private AtomicInteger money;
    private boolean inGame = false;
    private String currentGame;
    private Games games;
    private GuessTheNumberGame game;


    private static final int initialMoney = 100;

    public Player(String ID, String petName) {
        this.ID = ID;
        this.fridge = new Fridge();
        this.money = new AtomicInteger(initialMoney);
        this.pet = new Pet(petName);
        games = new Games();
    }

    public void buyFood(Food food) {
        fridge.putFood(food);
        money.addAndGet(-food.getPrice());
    }

    public String feedPet(String foodName) {
        var food = fridge.getFood(foodName);
        return pet.feed(food);
    }

    public String letsPlay(String game) {
        inGame = true;
        currentGame = game;
        if (currentGame.equals("GuessNumber")) {
            this.game = new GuessTheNumberGame();
        }
        return "Давай поиграем!";
    }

    public void sleepPet(int hours) {
        pet.sleep(hours);
    }

    public String play(String[] args) {
        var result = game.makeGuess(Integer.parseInt(args[0]));
        if (game.getWin()) {
            inGame = false;
            money.addAndGet(50 - game.getTries());
            pet.play();
        }
        return result;
    }

    public Pet getPet() {
        return pet;
    }

    public int getMoney() {
        return money.get();
    }

    public Fridge getFridge() {
        return fridge;
    }

    public void setFridgeItem(Food food) {
        fridge.putFood(food);
    }

    public void setMoney(int money) {
        this.money.set(money);
    }

    public boolean playerInGame() {
        return inGame;
    }
}
