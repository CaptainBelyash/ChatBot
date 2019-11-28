import java.util.TimerTask;

public class Task extends TimerTask {
    private String characteristic;
    private Pet pet;

    public Task(Pet pet, String characteristic) {
        this.characteristic = characteristic;
        this.pet = pet;
    }

    @Override
    public void run() {
        switch (characteristic) {
            case "happiness": {
                reduceHappiness();
                break;
            }
            case "satiety": {
                reduceSatiety();
                break;
            }
            case "peppiness": {
                reducePeppiness();
                break;
            }
            case "age": {
                increaseAge();
                break;
            }
        }
    }

    private synchronized void reduceHappiness() {
        pet.reduceHappiness();
    }

    private synchronized void reduceSatiety() {
        pet.reduceSatiety();
    }

    private synchronized void reducePeppiness() {
        pet.reducePeppiness();
    }

    private synchronized void increaseAge() {
        pet.increaseAge();
    }
}
