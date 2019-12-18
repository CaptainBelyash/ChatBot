import java.util.concurrent.atomic.AtomicInteger;

public class Player {
    private String ID;
    private Pet pet;
    private Fridge fridge;
    private AtomicInteger money;



    private static final int initialMoney = 100;

    public Player(String ID, String petName) {
        this.ID = ID;
        this.fridge = new Fridge();
        this.money = new AtomicInteger(initialMoney);
        this.pet = new Pet(petName);
    }

    public void buyFood(Food food) {
        fridge.putFood(food);
        money.addAndGet(-food.getPrice());
    }

    public String feedPet(String foodName) {
        var food = fridge.getFood(foodName);
        return pet.feed(food);
    }

    public void playWithPet() {
        pet.play();
        money.incrementAndGet();
    }

    public void sleepPet(int hours) {
        pet.sleep(hours);
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
}
