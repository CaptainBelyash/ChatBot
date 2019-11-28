import java.util.Timer;
import java.util.TimerTask;

public class PetLife implements Runnable {
    private int hungrySpeed = 3; //секунды
    private int hopelessSpeed = 720;
    private int growthSpeed = 86400;
    private int sleepinessSpeed = 360;
    private Pet pet;

    public PetLife(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void run() {
        Timer timerHappiness = new Timer();
        TimerTask taskHappiness = new Task(pet, "happiness");
        timerHappiness.schedule(taskHappiness, hopelessSpeed * 1000, hopelessSpeed * 1000);

        Timer timerSatiety = new Timer();
        TimerTask taskSatiety = new Task(pet, "satiety");
        timerSatiety.schedule(taskSatiety, hungrySpeed * 1000, hungrySpeed * 1000);

        Timer timerPeppiness = new Timer();
        TimerTask taskPeppiness = new Task(pet, "peppiness");
        timerPeppiness.schedule(taskPeppiness, sleepinessSpeed * 1000, sleepinessSpeed * 1000);

        Timer timerAge = new Timer();
        TimerTask taskAge = new Task(pet, "age");
        timerAge.schedule(taskAge, growthSpeed * 1000, growthSpeed * 1000);

    }
}
