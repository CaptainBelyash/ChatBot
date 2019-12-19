import java.util.concurrent.atomic.AtomicInteger;

public class Player {
    private String ID;
    private Pet pet;
    private Fridge fridge;
    private AtomicInteger money;
    private boolean inGame = false;
    private Games games;


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
        if (!games.getGames().containsKey(game))
            return "Такой игры нет";
        inGame = true;
        games.play(game);
        return "Давай поиграем!";
    }

    public void sleepPet(int hours) {
        pet.sleep(hours);
    }

    public String play(String[] args) {
        var result = games.doAction(args);
        if (games.getWin()) {
            inGame = false;
            money.addAndGet(games.getMoney());
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
