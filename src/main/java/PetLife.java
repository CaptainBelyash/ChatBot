import java.util.Timer;
import java.util.TimerTask;

public class PetLife implements Runnable
{
    private int hungrySpeed = 3600; //секунды
    private int hopelessSpeed = 7200;
    private int growthSpeed = 86400;
    private int sleepinessSpeed = 3600;
    private Pet pet;

    public PetLife(Pet pet){
        this.pet = pet;
    }

    @Override
    public void run() {
        Timer timerHappiness = new Timer();
        TimerTask taskHappiness = new Task(pet, "happiness");
        timerHappiness.schedule(taskHappiness, hopelessSpeed * 100, hopelessSpeed * 100);

        Timer timerSatiety = new Timer();
        TimerTask taskSatiety = new Task(pet, "satiety");
        timerSatiety.schedule(taskSatiety, hungrySpeed * 100, hungrySpeed * 100);

        Timer timerPeppiness = new Timer();
        TimerTask taskPeppiness = new Task(pet, "peppiness");
        timerPeppiness.schedule(taskPeppiness, sleepinessSpeed * 100, sleepinessSpeed * 100);

        Timer timerAge = new Timer();
        TimerTask taskAge = new Task(pet, "age");
        timerAge.schedule(taskAge, growthSpeed * 100, growthSpeed * 100);

    }
}
