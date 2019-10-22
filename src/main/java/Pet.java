public class Pet {
    private int health; //убрать статик
    private int happiness;
    private int satiety; //сытость
    private int peppiness; //бодрость
    private String name;
    private Birthday birthday;
    private int age;

    private int maxHealth = 10;
    private int maxSatiety = 10;
    private int maxPeppiness = 10;
    private int maxHappiness = 10;
    private int maxAge = 100;

    public Pet(String name) {
        this.name = name;
        health = maxHealth;
        happiness = 10;
        satiety = maxSatiety;
        peppiness = maxPeppiness;
        birthday = new Birthday();
        age = 0;
    }

    public void feed() {
        if (satiety < maxSatiety)
            satiety += 1;
    }

    public void play() {
        if (happiness < maxHappiness)
            happiness += 1;
        if (peppiness != 0)
            peppiness -= 1;
    }

    public void sleep(int hours) {
        if (peppiness < maxPeppiness)
            peppiness += hours;
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
}