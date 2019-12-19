import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Pet {
    private AtomicInteger health;
    private AtomicInteger happiness;
    private AtomicInteger satiety; //сытость
    private AtomicInteger peppiness; //бодрость
    private final String name;
    private final Birthday birthday;
    private AtomicInteger age;
    private String Avatar;

    private ArrayDeque<String> notifys = new ArrayDeque<String>();

    private int maxHealth = 10; //static
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
    }

    public String feed(Food food) {
        satiety.addAndGet(food.getSaturation());
        if (happiness.get() < maxHappiness)
            happiness.incrementAndGet();
        if (satiety.get() >= maxSatiety){
            satiety.set(maxSatiety);
            return "Я наелся!";
        }
        return "Очень вкусно! +" + food.getSaturation() + " к сытости";
    }

    public void play() { //В будущем мини-игры, которые приносят разное количество денег, счастья и отнимают разное количество бодрости
        if (happiness.get() < maxHappiness)
            happiness.incrementAndGet();
        if (peppiness.get() != 0)
            peppiness.decrementAndGet();
        else {
            reduceSatiety();
            notifys.offer("Я устал");
        }
    }

    public void sleep(int hours) {
        if (hours >= 0 && peppiness.get() <= maxPeppiness) {
            peppiness.set(Math.min(peppiness.get() + hours, maxPeppiness));
        }
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

        return characteristics;
    }

    public void reduceHappiness() {
        if (happiness.get() == 1)
            notifys.offer("Я очень сльно грущу");
        if (happiness.get() > 0)
            happiness.decrementAndGet();
    }

    public void reduceSatiety() {
        if (satiety.get() == 1)
            notifys.offer("Я голоден");
        if (satiety.get() > 0)
            satiety.decrementAndGet();
        else
            reduceHealth();
    }

    public void reducePeppiness() {
        if (peppiness.get() == 1)
            notifys.offer("Я устал");
        if (peppiness.get() > 0)
            peppiness.decrementAndGet();
        else
            reduceHealth();
    }

    public void reduceHealth() {
        if (health.get() > 0)
            health.decrementAndGet();
        reduceHappiness();
    }

    public void increaseAge() {
        if (age.get() < maxAge)
            age.incrementAndGet();
    }

    public int getSatiety() {
        return satiety.get();
    }

    public int getHappiness() {
        return happiness.get();
    }

    public int getPeppiness() {
        return peppiness.get();
    }

    public int getAge() {
        return age.get();
    }

    public String getName() {
        return name;
    }

    public int getMaxSatiety() {
        return maxSatiety;
    }

    public int getMaxHappiness() {
        return maxHappiness;
    }

    public int getMaxPeppiness() {
        return maxPeppiness;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setSatiety(int satiety) {
        if (satiety >= 0 && satiety <= maxSatiety)
            this.satiety.set(satiety);
    }

    public void setHappiness(int happiness) {
        if (happiness >= 0 && happiness <= maxHappiness)
            this.happiness.set(happiness);
    }

    public void setPeppiness(int peppiness) {
        if (peppiness >= 0 && peppiness <= maxPeppiness)
            this.peppiness.set(peppiness);
    }

    public void setAge(int age) {
        if (age >= 0 && age <= maxAge)
            this.age.set(age);
    }

}