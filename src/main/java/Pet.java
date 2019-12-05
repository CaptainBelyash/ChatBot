import java.util.ArrayDeque;
import java.util.HashMap;

public class Pet {
    private int health;
    private int happiness;
    private int satiety; //сытость
    private int peppiness; //бодрость
    private String name;
    private Birthday birthday;
    private int age;

    private ArrayDeque<String> notifys = new ArrayDeque<String>();

    private int money;
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
        health = maxHealth;
        happiness = maxHappiness;
        satiety = maxSatiety;
        peppiness = maxPeppiness;
        birthday = new Birthday();
        age = 0;
        money = initialMoney;
        fridge = new HashMap<String, Food>();
    }

    public String feed(String foodName) {
        if (!this.fridge.containsKey(foodName))
            return "Такого продукта нет в холодильнике";
        var food = this.fridge.get(foodName);
        food.reduceAmount();
        if (food.getAmount() <= 0)
            fridge.remove(foodName);
        satiety += food.getSaturation();
        if (satiety >= maxSatiety){
            satiety = maxSatiety;
            return "Я наелся!";
        }
        return "Очень вкусно! +" + food.getSaturation() + " к сытости";
    }

    public void play() { //В будущем мини-игры, которые приносят разное количество денег, счастья и отнимают разное количество бодрости
        if (happiness < maxHappiness)
            happiness += 1;
        if (peppiness != 0)
            peppiness -= 1;
        money += 1;
    }

    public void sleep(int hours) {
        if (hours >= 0 && peppiness <= maxPeppiness) {
            peppiness = Math.min(peppiness + hours, maxPeppiness);
        }
    }

    public String buyFood(Food food) {
        if (food.getPrice() > money)
            return "Недостаточно денег";
        if (fridge.containsKey(food.getName()))
            fridge.get(food.getName()).increaseAmount();
        else
            fridge.put(food.getName(), food);
        money -= food.getPrice();
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

    public void reduceHappiness() {
        if (happiness == 1)
            notifys.offer("Я очень сльно грущу");
        if (happiness > 0)
            happiness--;
    }

    public void reduceSatiety() {
        if (satiety == 1)
            notifys.offer("Я голоден");
        if (satiety > 0)
            satiety--;
        else
            reduceHappiness();
    }

    public void reducePeppiness() {
        if (peppiness > 0)
            peppiness--;
        else
            reduceHappiness();
    }

    public void increaseAge() {
        if (age < maxAge)
            age++;
    }

    public int getSatiety() {
        return satiety;
    }

    public int getHappiness() {
        return happiness;
    }

    public int getPeppiness() {
        return peppiness;
    }

    public int getAge() {
        return age;
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

    public int getMoney() {
        return money;
    }

    public HashMap<String, Food> getFridge() {
        return fridge;
    }

    public void setSatiety(int satiety) {
        if (satiety >= 0 && satiety <= maxSatiety)
            this.satiety = satiety;
    }

    public void setHappiness(int happiness) {
        if (happiness >= 0 && happiness <= maxHappiness)
            this.happiness = happiness;
    }

    public void setPeppiness(int peppiness) {
        if (peppiness >= 0 && peppiness <= maxPeppiness)
            this.peppiness = peppiness;
    }

    public void setAge(int age) {
        if (age >= 0 && age <= maxAge)
            this.age = age;
    }

    public void setFridgeItem(Food food) {
        if (fridge.containsKey(food.getName()))
            fridge.get(food.getName()).increaseAmount();
        else
            fridge.put(food.getName(), food);
    }

    public void setMoney(int money) {
        this.money = money;
    }
}