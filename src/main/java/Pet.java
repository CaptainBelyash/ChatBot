
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pet {
    @JsonProperty("health")
    private int health;
    @JsonProperty("happiness")
    private int happiness;
    @JsonProperty("satiety")
    private int satiety; //сытость
    @JsonProperty("peppiness")
    private int peppiness; //бодрость
    @JsonProperty("name")
    private String name;
    @JsonIgnore
    private Birthday birthday;
    @JsonProperty("age")
    private int age;

    @JsonIgnore
    private int maxHealth = 10;
    @JsonIgnore
    private int maxSatiety = 10;
    @JsonIgnore
    private int maxPeppiness = 10;
    @JsonIgnore
    private int maxHappiness = 10;
    @JsonIgnore
    private int maxAge = 100;

    public Pet() {
    }

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

    public void reduceHappiness(){
        if (happiness > 0)
            happiness--;
    }

    public void reduceSatiety(){
        if (satiety > 0)
            satiety--;
        else
            reduceHappiness();
    }

    public void reducePeppiness(){
        if (peppiness > 0)
            peppiness--;
        else
            reduceHappiness();
    }

    public void increaseAge(){
        if (age < maxAge)
            age++;
    }
}