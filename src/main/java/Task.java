import java.util.TimerTask;

public class Task extends TimerTask {
    private String characteristic;
    private Pet pet;

    public Task(Pet pet, String characteristic){
        this.characteristic = characteristic;
        this.pet = pet;
    }

    @Override
    public void run() {
        switch (characteristic){
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

    public void reduceHappiness(){
        pet.reduceHappiness();
    }

    public void reduceSatiety(){
        pet.reduceSatiety();
    }

    public void reducePeppiness(){
        pet.reducePeppiness();
    }

    public void increaseAge(){
        pet.increaseAge();
    }
}
