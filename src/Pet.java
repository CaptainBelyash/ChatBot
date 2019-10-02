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

    public static void feed(){
        satiety += 1;
    }

    public static void play(){
        happiness += 1;
        peppiness -= 1;
    }

    //TODO: сделать зависимость от времени
    public static void sleep(){
        peppiness += 1;
    }

    public static String getCharacteristics(){
        String characteristics = "";
        characteristics += "Pet: " + name + "\n";
        characteristics += "Birthday: " + birthday.getBirthday() + "\n";
        characteristics += "Age: " + age + "\n";
        characteristics += "Health: " + health + "/" + maxHealth + "\n";
        characteristics += "Happiness: " + happiness + "/" + maxHappiness + "\n";
        characteristics += "Satiety: " + satiety + "/" + maxSatiety + "\n";
        characteristics += "Peppiness: " + peppiness + "/" + maxPeppiness + "\n";

        return characteristics;
    }
}
