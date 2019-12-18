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
        for (var command : new String[]{"", "aqqqq", "creeeeete", "fead"}) {
            var result = botLogic.commandInput("", command);
            Assertions.assertEquals(botLogic.error("No such command"), result);
        }

        var result = botLogic.commandInput("", "create");
        Assertions.assertEquals(botLogic.error("Не указано имя питомца"), result);

        result = botLogic.commandInput("", "sleep");
        Assertions.assertEquals(botLogic.error("Не указано количестов часов."), result);
    }

    @Test
    void deleteCommand() throws IOException {
        for (var id : new String[] {"1", "2", "3", "4", "5"}) {
            botLogic.commandInput(id, "create pet");
        }
        botLogic.deleteCommand(new String[] {"5"});
        Assertions.assertFalse(botLogic.getPlayers().containsKey("5"));

    }

    @Test
    void feedCommand() throws IOException {
        var foodShop = new FoodShop();
        var assortment = foodShop.getAssortment();
        botLogic.commandInput("1", "create pet");
        var player = botLogic.getPlayers().get("1");
        var pet = player.getPet();
        for (var satiety : new int[]{0, 1, 2, 3, 9, pet.getMaxSatiety()}) {
            for (var food : assortment.values()) {
                pet.setSatiety(satiety);
                player.setFridgeItem(food);
                botLogic.feedCommand(new String[] {"1", food.getName()});
                Assertions.assertEquals(Math.min(satiety + food.getSaturation(), pet.getMaxSatiety()), pet.getSatiety());
            }
        }
        Assertions.assertEquals("Error: Не указана еда.", botLogic.feedCommand(new String[] {"1"}));
    }

    @Test
    void playCommand() throws IOException {
        botLogic.commandInput("1", "create pet");
        var player = botLogic.getPlayers().get("1");
        var pet = player.getPet();
        for (var happiness : new int[]{0, 1, 2, 3, 9, pet.getMaxHappiness()})
            for (var peppiness : new int[]{0, 1, 2, 3, 9, pet.getMaxPeppiness()}) {
                pet.setPeppiness(peppiness);
                pet.setHappiness(happiness);
                var originalMoney = player.getMoney();
                botLogic.playCommand(new String[] {"1"});
                if (happiness < pet.getMaxHappiness())
                    Assertions.assertEquals(happiness + 1, pet.getHappiness());
                else
                    Assertions.assertEquals(pet.getMaxHappiness(), pet.getHappiness());
                if (peppiness > 0)
                    Assertions.assertEquals(peppiness - 1, pet.getPeppiness());
                else
                    Assertions.assertEquals(0, pet.getPeppiness());
                Assertions.assertEquals(originalMoney + 1, player.getMoney());
            }
    }

    @Test
    void sleepCommand() throws IOException {
        botLogic.commandInput("1", "create pet");
        var player = botLogic.getPlayers().get("1");
        var pet = player.getPet();
        for (var peppiness : new int[]{0, 1, 2, 3, 9, pet.getMaxPeppiness()})
            for (var hours : new int[]{-10, -1, 0, 1, 2, 8, 9,
                    pet.getMaxPeppiness(), pet.getMaxPeppiness() + 1}) {
                pet.setPeppiness(peppiness);
                pet.sleep(hours);
                if (hours <= 0)
                    Assertions.assertEquals(peppiness, pet.getPeppiness());
                else {
                    Assertions.assertEquals(Math.min(peppiness + hours, pet.getMaxPeppiness()), pet.getPeppiness());
                }
            }
    }

    @Test
    void createCommand() throws IOException {
        var playerID = "1";
        botLogic.commandInput(playerID, "help");
        var players = botLogic.getPlayers();
        botLogic.createCommand(new String[]{playerID, "pet"});
        Assertions.assertTrue(players.containsKey(playerID));
        var player = players.get(playerID);
        var pet = player.getPet();
        Assertions.assertEquals("pet", pet.getName());
        var result = botLogic.createCommand(new String[]{playerID, "pet"});
        Assertions.assertEquals(botLogic.error("Pet exist"), result);
    }

    @Test
    void buyCommand() throws IOException {
        var playerID = "1";
        var foodShop = new FoodShop();
        Assertions.assertEquals("Error: Не указана еда.", botLogic.buyCommand(new String[] {playerID}));
        var assortment = foodShop.getAssortment();
        botLogic.commandInput(playerID, "create pet");
        var player = botLogic.getPlayers().get(playerID);
        player.setMoney(0);
        for (var food : assortment.keySet()) {
            Assertions.assertEquals(
                    "Error: Недостаточно денег",
                    botLogic.buyCommand(new String[] {playerID, food}));
        }
        for (var food : assortment.values()) {
            player.setMoney(2 * food.getPrice());
            botLogic.buyCommand(new String[] {playerID, food.getName()});
            Assertions.assertEquals(food.getPrice(), player.getMoney());
            Assertions.assertTrue(player.getFridge().containsFood(food.getName()));
            Assertions.assertEquals(1, player.getFridge().getAmount(food.getName()));

            botLogic.buyCommand(new String[] {playerID, food.getName()});
            Assertions.assertEquals(0, player.getMoney());
            Assertions.assertTrue(player.getFridge().containsFood(food.getName()));
            Assertions.assertEquals(2, player.getFridge().getAmount(food.getName()));
        }
    }
}
