import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.function.Function;

public class BotLogic {
    private static HashMap<String, Command> commandsList = new HashMap<>();
    private static HashMap<String, ArrayDeque<String>> notifyQueue = new HashMap<>();
    private static HashMap<String, Pet> pets = new HashMap<>();
    private static String currentPlayerID = "";
    private static PetLife petLife;
    private static String helpOutput = "";
    private static FoodShop foodShop;

    public BotLogic() {
        fillCommands();
        foodShop = new FoodShop();
        var notifyMoveThread = new MoveNotificationThread(this);
        notifyMoveThread.start();
    }

    private static void makeCommand(String name, String help, Function<String[], String> action) {
        var newCommand = new Command(name, help, action);
        commandsList.put(newCommand.getName(), newCommand);
    }

    private static void fillCommands() {
        makeCommand("help", "help: Выводит список доступных комманд.", BotLogic::helpCommand);
        makeCommand("create", "create [name]: Создаёт нового питомца с именем name.",
                BotLogic::createCommand);
        makeCommand("feed", "feed [food]: Покормить питомца едой food. Увеличивает сытость. Кормить питомца можно только едой из холодильника.",
                BotLogic::feedCommand);
        makeCommand("play", "play: Поиграть с питомцем. Прибавляет 1 к счастью и отнимает 1 от бодрости. Добавляет деньги.",
                BotLogic::playCommand);
        makeCommand("sleep",
                "sleep [hours]: Отправить питомца спать на hours часов. Прибавляет hours к бодрости.",
                BotLogic::sleepCommand);
        makeCommand("chars", "chars: Получить характеристики питомца",
                BotLogic::getCharacteristicsCommand);
        makeCommand("delete", "delete: Удалить питомца",
                BotLogic::deleteCommand);
        makeCommand("buy", "buy [food]: Купить еду food. Купленная еда добавляется в холодильник.",
                BotLogic::buyCommand);
        makeCommand("shop", "shop: Получить ассортимент товаров в магазине.",
                BotLogic::getAssortmentCommand);
        makeCommand("fridge", "fridge: Получить содержимое холодильника.",
                BotLogic::getFridgeAssortmentCommand);
        makeCommand("/start", "start",
                BotLogic::greetings);
    }

    public static String commandInput(String playerID, String args) throws IOException {
        currentPlayerID = playerID;
        var input = args.split(" ");
        var output = "";
        var userCommand = input[0];
        if (!commandsList.containsKey(userCommand)) {
            output = error("No such command");
            queueAdd(currentPlayerID, output);
            return output;
        }
        var commandArgs = new String[0];
        if (input.length > 1) {
            commandArgs = Arrays.copyOfRange(input, 1, input.length);
        }
        try {
            output = commandsList.get(userCommand).execute(commandArgs);
            queueAdd(currentPlayerID, output);
            return output;
        } catch (Exception e) {
            output = error("Something broke everything here. Maybe it was a ghost?");
            queueAdd(currentPlayerID, output);
            return output;
        }
    }

    private synchronized static void queueAdd(String playerID, String value){
        if (!notifyQueue.containsKey(playerID))
            notifyQueue.put(playerID, new ArrayDeque<String>());
        var success = notifyQueue.get(playerID).offer(value);
    }

    public static String error(String e) {
        return "Error: " + e;
    }

    private static String greetings(String[] args) {
        String hello = "Привет!\nВ этом чат-боте ты можешь создать и ухаживать за своим питомцем.";
        return hello + commandsList.get("help").execute(new String[0]);
    }

    private static String helpCommand(String[] args) {
        if (helpOutput.length() == 0) {
            StringBuilder help = new StringBuilder("\nКомманды, которые ты можешь использовать:");
            for (var command : commandsList.keySet()) {
                help.append("\n");
                help.append(commandsList.get(command).help());
            }
            helpOutput = help.toString();
        }
        return helpOutput;
    }

    public static String createCommand(String[] args) {
        var name = args[0];
        if (pets.containsKey(currentPlayerID))
            return error("Pet exist");
        pets.put(currentPlayerID, new Pet(name));

        petLife = new PetLife(pets.get(currentPlayerID));

        Thread myThready = new Thread(petLife);
        myThready.start();

        return pets.get(currentPlayerID).getCharacteristics();

    }

    public static String deleteCommand(String[] args) {
        pets.remove(currentPlayerID);
        return "Питомец удален";
    }

    public synchronized static String buyCommand(String[] args) {
        if (args.length == 0)
            return error("Не указана еда.");
        var food = foodShop.buy(args[0]);
        return pets.get(currentPlayerID).buyFood(food);
    }

    public synchronized static String feedCommand(String[] args) {
        if (args.length == 0)
            return error("Не указана еда.");
        return pets.get(currentPlayerID).feed(args[0]);
    }

    public synchronized static String playCommand(String[] args) {
        pets.get(currentPlayerID).play();
        return "Как весело! +1 к счастью";
    }

    private synchronized static String sleepCommand(String[] args) {
        int hours = Integer.parseInt(args[0]);
        pets.get(currentPlayerID).sleep(hours);
        return "+" + Integer.toString(hours) + " к бодрости";
    }

    private synchronized static String getCharacteristicsCommand(String[] args) {
        return pets.get(currentPlayerID).getCharacteristics();
    }

    private synchronized static String getAssortmentCommand(String[] args) {
        return foodShop.showAssortment();
    }

    private synchronized static String getFridgeAssortmentCommand(String[] args) {
        return pets.get(currentPlayerID).getFridgeAssortment();
    }

    public HashMap<String, Pet> getPets() {
        return pets;
    }

    public void movePetNotify(){
        for (var playerID:pets.keySet()){
            var nextNotify = pets.get(playerID).getNotifys().poll();
            while (nextNotify != null){
                queueAdd(playerID, nextNotify);
                nextNotify = pets.get(playerID).getNotifys().poll();
            }
        }
    }

    public HashMap<String, ArrayDeque<String>> getNotifys() {
        return notifyQueue;
    }
}