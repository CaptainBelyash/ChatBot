import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


class PetTest {
    public Pet pet;

    @BeforeEach
    void setUp() {
        pet = new Pet("");
    }

    @Test
    void setCharacteristic(){
        var originalSatiety = pet.getSatiety();
        var originalHappiness = pet.getHappiness();
        var originalPeppiness = pet.getPeppiness();
        var maxSatiety = pet.getMaxSatiety();
        var maxHappiness = pet.getMaxHappiness();
        var maxPeppiness = pet.getMaxPeppiness();

        for (var characteristic: new int[] {-100, -5, 0, 1, 3, 10, 100,
                maxSatiety, maxHappiness, maxPeppiness}){
            pet.setSatiety(characteristic);
            pet.setHappiness(characteristic);
            pet.setPeppiness(characteristic);

            if (characteristic < 0 || characteristic > maxSatiety)
                Assertions.assertEquals(originalSatiety, pet.getSatiety());
            else
                Assertions.assertEquals(characteristic, pet.getSatiety());

            if (characteristic < 0 || characteristic > maxHappiness)
                Assertions.assertEquals(originalHappiness, pet.getHappiness());
            else
                Assertions.assertEquals(characteristic, pet.getHappiness());

            if (characteristic < 0 || characteristic > maxPeppiness)
                Assertions.assertEquals(originalPeppiness, pet.getPeppiness());
            else
                Assertions.assertEquals(characteristic, pet.getPeppiness());

            originalSatiety = pet.getSatiety();
            originalHappiness = pet.getHappiness();
            originalPeppiness = pet.getPeppiness();
        }
    }

    @Test
    void feed() {
        for (var satiety: new int[] {0, 1, 2, 3, 9, pet.getMaxSatiety()}){
            pet.setSatiety(satiety);
            pet.feed();
            if (satiety < pet.getMaxSatiety())
                Assertions.assertEquals(satiety + 1, pet.getSatiety());
            else
                Assertions.assertEquals(pet.getMaxSatiety(), pet.getSatiety());
        }
    }

    @Test
    void play() {
        for (var happiness: new int[] {0, 1, 2, 3, 9, pet.getMaxHappiness()})
            for (var peppiness: new int[] {0, 1, 2, 3, 9, pet.getMaxPeppiness()}){
                pet.setPeppiness(peppiness);
                pet.setHappiness(happiness);
                pet.play();
                if (happiness < pet.getMaxHappiness())
                    Assertions.assertEquals(happiness + 1, pet.getHappiness());
                else
                    Assertions.assertEquals(pet.getMaxHappiness(), pet.getHappiness());
                if (peppiness > 0)
                    Assertions.assertEquals(peppiness - 1, pet.getPeppiness());
                else
                    Assertions.assertEquals(0, pet.getPeppiness());
            }
    }

    @Test
    void sleep() {
        for (var peppiness: new int[] {0, 1, 2, 3, 9, pet.getMaxPeppiness()})
            for (var hours: new int[] {-10, -1, 0, 1, 2, 8, 9,
                    pet.getMaxPeppiness(), pet.getMaxPeppiness() + 1}){
            pet.setPeppiness(peppiness);
            pet.sleep(hours);
            if (hours <= 0)
                Assertions.assertEquals(peppiness, pet.getPeppiness());
            else{
                Assertions.assertEquals(Math.min(peppiness + hours, pet.getMaxPeppiness()), pet.getPeppiness());
            }
        }
    }
}