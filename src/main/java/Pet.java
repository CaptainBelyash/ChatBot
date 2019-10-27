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
        happiness = maxHappiness;
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
        if (peppiness < maxPeppiness && hours > 0){
            peppiness = Math.min(peppiness + hours, maxPeppiness);
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

    public int getHealth(){
        return health;
    }

    public String getName(){
        return name;
    }

    public Birthday getBirthday(){
        return birthday;
    }

    public int getAge(){
        return age;
    }

    public int getHappiness(){
        return happiness;
    }

    public int getSatiety(){
        return satiety;
    }

    public int getPeppiness(){
        return peppiness;
    }

    public int getMaxHappiness(){
        return maxHappiness;
    }

    public int getMaxSatiety(){
        return maxSatiety;
    }

    public int getMaxPeppiness(){
        return maxPeppiness;
    }

    public int getMaxHealth(){
        return maxHealth;
    }

    public void setSatiety(int satiety){
        if (satiety >= 0 && satiety <= maxSatiety)
            this.satiety = satiety;
    }

    public void setHappiness(int happiness){
        if (happiness >= 0 && happiness <= maxHappiness)
            this.happiness = happiness;
    }

    public void setPeppiness(int peppiness){
        if (peppiness >= 0 && peppiness <= maxPeppiness)
            this.peppiness = peppiness;
    }
}