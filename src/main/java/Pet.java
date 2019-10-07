public class Pet {
    private static int health;
    private static int happiness;
    private static int satiety; //сытость
    private static int peppiness; //бодрость
    private static String name;
    private static Birthday birthday;
    private static int age;

    private static int maxHealth = 10;
    private static int maxSatiety = 10;
    private static int maxPeppiness = 10;
    private static int maxHappiness = 10;
    private static int maxAge = 10;
    //TODO: придумать максимальные характеристики
    //TODO: зависимость между характеристиками

    public Pet(String name){
        Pet.name = name;
        health = maxHealth;
        happiness = 10;
        satiety = maxSatiety;
        peppiness = maxPeppiness;
        birthday = new Birthday();
        age = 0;
    }

    public void feed(){
        satiety += 1;
        satiety %= maxSatiety;
    }

    public void play(){
        happiness += 1;
        happiness %= maxHappiness;
        if (peppiness != 0)
            peppiness -= 1;
    }

    //TODO: сделать зависимость от времени
    public void sleep(int hours){
        peppiness += hours;
        peppiness %= maxPeppiness;
    }

    public String getCharacteristics(){
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
