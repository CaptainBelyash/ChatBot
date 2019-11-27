import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

class BotLogicTest {
    private BotLogic botLogic;

    @BeforeEach
    void setUp() {
        botLogic = new BotLogic();
    }

    @Test
    void commandInput() throws IOException {
        for (var command: new String[] {"", "aqqqq", "creeeeete", "fead"}){
            var result = botLogic.commandInput("", command);
            Assertions.assertEquals(botLogic.error(), result);
        }

        for (var command: new String[] {"create", "sleep"}){
            var result = botLogic.commandInput("", command);
            Assertions.assertEquals(botLogic.error(), result);
        }
    }

    @Test
    void deleteCommand() throws IOException {
        for (var id: new String[] {"1", "2", "3", "4", "5"}){
            botLogic.commandInput(id, "create pet");
        }
        botLogic.deleteCommand(new String[0]);
        Assertions.assertFalse(botLogic.getPets().containsKey("5"));

    }

    @Test
    void feedCommand() throws IOException {
        botLogic.commandInput("1", "create pet");
        var pet = botLogic.getPets().get("1");
        for (var satiety: new int[] {0, 1, 2, 3, 9, pet.getMaxSatiety()}) {
            pet.setSatiety(satiety);
            botLogic.feedCommand(new String[0]);
            Assertions.assertEquals(Math.min(satiety + 1, pet.getMaxSatiety()), pet.getSatiety());
        }
    }

    @Test
    void playCommand() throws IOException {
        botLogic.commandInput("1", "create pet");
        var pet = botLogic.getPets().get("1");
        for (var happiness: new int[] {0, 1, 2, 3, 9, pet.getMaxHappiness()})
            for (var peppiness: new int[] {0, 1, 2, 3, 9, pet.getMaxPeppiness()}){
                pet.setPeppiness(peppiness);
                pet.setHappiness(happiness);
                botLogic.playCommand(new String[0]);
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
    void sleepCommand() throws IOException {
        botLogic.commandInput("1", "create pet");
        var pet = botLogic.getPets().get("1");
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

    @Test
    void createCommand() throws IOException {
        botLogic.commandInput("1", "help");
        var pets = botLogic.getPets();
        botLogic.createCommand(new String[] {"pet"});
        Assertions.assertTrue(pets.containsKey("1"));
        var pet = pets.get("1");
        Assertions.assertEquals("pet", pet.getName());
        var result = botLogic.createCommand(new String[] {"pet"});
        Assertions.assertEquals(botLogic.error(), result);
    }
}
