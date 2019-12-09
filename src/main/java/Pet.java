import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Pet {
    private AtomicInteger health;
    private AtomicInteger happiness;
    private AtomicInteger satiety; //сытость
    private AtomicInteger peppiness; //бодрость
    private String name;
    private Birthday birthday;
    private AtomicInteger age;

    private ArrayDeque<String> notifys = new ArrayDeque<String>();

    private AtomicInteger money;
    private int initialMoney = 100;

    private HashMap<String, Food> fridge;

    private int maxHealth = 10;
    private int maxSatiety = 10;
    private int maxPeppiness = 10;
    private int maxHappiness = 10;
    private int maxAge = 100;

    public ArrayDeque<String> getNotifys() {
        return notifys;
    }

    public Pet(String name) {
        this.name = name;
        health = new AtomicInteger(maxHealth);
        happiness = new AtomicInteger(maxHappiness);
        satiety = new AtomicInteger(maxSatiety);
        peppiness = new AtomicInteger(maxPeppiness);
        birthday = new Birthday();
        age = new AtomicInteger(0);
        money = new AtomicInteger(initialMoney);
        fridge = new HashMap<String, Food>();
    }

    public String feed(String foodName) {
        if (!this.fridge.containsKey(foodName))
            return "Такого продукта нет в холодильнике";
        var food = this.fridge.get(foodName);
        food.reduceAmount();
        if (food.getAmount() <= 0)
            fridge.remove(foodName);
        satiety.addAndGet(food.getSaturation());
        if (satiety.get() >= maxSatiety){
            satiety.set(maxSatiety);
            return "Я наелся!";
        }
        return "Очень вкусно! +" + food.getSaturation() + " к сытости";
    }

    public synchronized void play() { //В будущем мини-игры, которые приносят разное количество денег, счастья и отнимают разное количество бодрости
        if (happiness.get() < maxHappiness)
            happiness.incrementAndGet();
        if (peppiness.get() != 0)
            peppiness.decrementAndGet();
        money.incrementAndGet();
    }

    public synchronized void sleep(int hours) {
        if (hours >= 0 && peppiness.get() <= maxPeppiness) {
            peppiness.set(Math.min(peppiness.addAndGet(hours), maxPeppiness));
        }
    }

    public synchronized String buyFood(Food food) {
        if (food.getPrice() > money.get())
            return "Недостаточно денег";
        if (fridge.containsKey(food.getName()))
            fridge.get(food.getName()).increaseAmount();
        else
            fridge.put(food.getName(), food);
        money.addAndGet(-food.getPrice());
        return "Холодильник пополнен";
    }

    public String getCharacteristics() {
        String characteristics = "";
        characteristics += "Питомец: " + name + "\n";
        characteristics += "День рождения: " + birthday.getBirthday() + "\n";
        characteristics += "Возраст: " + age + "\n";
        characteristics += "Здоровье: " + health + "/" + maxHealth + "\n";
        characteristics += "Счастье: " + happiness + "/" + maxHappiness + "\n";
        characteristics += "Сытость: " + satiety + "/" + maxSatiety + "\n";
        characteristics += "Бодрость: " + peppiness + "/" + maxPeppiness + "\n";
        characteristics += "Деньги: " + money + "\n";

        return characteristics;
    }

    public String getFridgeAssortment() {
        StringBuilder assortment = new StringBuilder("Ассортимент продуктов:");
        for (var foodName: fridge.keySet()){
            var food = fridge.get(foodName);
            assortment.append("\n" + foodName + " Насыщение: " + food.getSaturation() + "Количество: " + food.getAmount());
        }
        return assortment.toString();
    }

    public synchronized void reduceHappiness() {
        if (happiness == 1)
            notifys.offer("Я очень сльно грущу");
        if (happiness > 0)
            happiness--;
    }

    public synchronized void reduceSatiety() {
        if (satiety == 1)
            notifys.offer("Я голоден");
        if (satiety > 0)
            satiety--;
        else
            reduceHappiness();
    }

    public synchronized void reducePeppiness() {
        if (peppiness > 0)
            peppiness--;
        else
            reduceHappiness();
    }

    public synchronized void increaseAge() {
        if (age < maxAge)
            age++;
    }

    public synchronized int getSatiety() {
        return satiety.get();
    }

    public synchronized int getHappiness() {
        return happiness.get();
    }

    public synchronized int getPeppiness() {
        return peppiness.get();
    }

    public synchronized int getAge() {
        return age.get();
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized int getMaxSatiety() {
        return maxSatiety;
    }

    public synchronized int getMaxHappiness() {
        return maxHappiness;
    }

    public synchronized int getMaxPeppiness() {
        return maxPeppiness;
    }

    public synchronized int getMaxAge() {
        return maxAge;
    }

    public synchronized int getMoney() {
        return money.get();
    }

    public synchronized HashMap<String, Food> getFridge() {
        return fridge;
    }

    public synchronized void setSatiety(int satiety) {
        if (satiety >= 0 && satiety <= maxSatiety)
            this.satiety.set(satiety);
    }

    public synchronized void setHappiness(int happiness) {
        if (happiness >= 0 && happiness <= maxHappiness)
            this.happiness.set(happiness);
    }

    public synchronized void setPeppiness(int peppiness) {
        if (peppiness >= 0 && peppiness <= maxPeppiness)
            this.peppiness.set(peppiness);
    }

    public synchronized void setAge(int age) {
        if (age >= 0 && age <= maxAge)
            this.age.set(age);
    }

    public synchronized void setFridgeItem(Food food) {
        if (fridge.containsKey(food.getName()))
            fridge.get(food.getName()).increaseAmount();
        else
            fridge.put(food.getName(), food);
    }

    public synchronized void setMoney(int money) {
        this.money.set(money);
    }
}